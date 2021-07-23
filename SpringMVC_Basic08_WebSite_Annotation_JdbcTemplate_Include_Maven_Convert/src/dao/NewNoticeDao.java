package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import vo.Notice;

@Repository			//Repository를 붙이려면 전제조건이 있음, xml 에서 bean으로 만드는게 아니라 자동으로 만들겠다 라는 뜻
public class NewNoticeDao implements NoticeDao {
	
	/*jdbc Template 객체 의존*/
	private JdbcTemplate jdbctemplate;

	@Autowired   //같은 타입이 있다면 주입을 받을거임
	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}
	
	@Override
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException {
		//쿼리의 결과가 단일값 (sum() , max() , min(), count())
		String sql = "SELECT COUNT(*) CNT FROM NOTICES WHERE "+field+" LIKE ?";

		return this.jdbctemplate.queryForObject(sql, Integer.class, "%"+query+"%");
											//실행될 쿼리문, ref class타입, 쿼리이름
	}

	@Override
	public List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException {
		
		int srow = 1 + (page-1)*5;
		int erow = 5 + (page-1)*5; 

		String sql = "SELECT * FROM";  // 쿼리문에 * 쓰지말자, 안좋은 습관

		sql += "(SELECT ROWNUM NUM, N.* FROM (SELECT * FROM NOTICES WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N)";
		sql += "WHERE NUM BETWEEN ? AND ?";

		//Notice DTO 객체 데이타 담아서
		//List 형태로 리턴
		//Object[] arr = {"%"+query+"%" , srow , erow}
		//결과를 notice 객체를 만들어서 데이터 건수만큼 .. List 추가 .....

		return this.jdbctemplate.query(sql, new Object[]{"%"+query+"%",srow,erow}, 
				new BeanPropertyRowMapper<Notice>(Notice.class));
											//파라미터로 들어오는것들을 배열로 만들어서 주는데
		                                    //왜 object 타입이니? 어떤게 들어올줄 모르잖아
	}

	@Override
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException {
		String sql = "SELECT seq,title,writer,content,regdate,hit,filesrc,filesrc2 FROM NOTICES WHERE SEQ="+seq;

		//자동 매핑이 되지 않는 경우
		//자동 매핑 (select 컬럼명 == dto member field 일치)
		//아래와 같은 작업을 할것이라면 굳이 왜 template 쓰냐, 쓸 이유가 없다
		//자동화에 대한 준비를 하자 = 무슨 준비 ? 위에꺼 (select 컬럼명 == dto member field 일치) 이거
		//(ui (input name = ("name태그값") == dto (memberfield명) == DB(컬럼명) 3개를 똑같이 맞추자))

		return this.jdbctemplate.queryForObject(sql,new RowMapper<Notice>(){

			@Override
			public Notice mapRow(ResultSet rs, int rownum) throws SQLException {
	
				Notice n = new Notice();
		
				//Notice 객체의 생성자 구현 new Notice(rs.getString("seq") ,...
				//하나씩 mapping
				n.setSeq(rs.getString("seq"));
				n.setTitle(rs.getString("title"));
				n.setWriter(rs.getString("writer"));
				n.setRegdate(rs.getDate("regdate"));
				n.setHit(rs.getInt("hit"));
				n.setContent(rs.getString("content"));
				n.setFileSrc(rs.getString("fileSrc"));
				n.setFileSrc2(rs.getString("fileSrc2"));
				return n;

		}

		});
	}
	
	@Override
	public int delete(String seq) throws ClassNotFoundException, SQLException {

		String sql = "DELETE NOTICES WHERE SEQ=?";
		return this.jdbctemplate.update(sql, seq);		//update 함수, 내장된 함수
	}

	@Override
	public int update(Notice notice) throws ClassNotFoundException, SQLException {

		String sql = "UPDATE NOTICES SET TITLE=?, CONTENT=?, FILESRC=? , FILESRC2=? WHERE SEQ=?";
		return this.jdbctemplate.update(sql,notice.getTitle(),notice.getContent(),
		notice.getFileSrc(),notice.getFileSrc2() ,notice.getSeq());
	}


	@Override
	public int insert(Notice n) throws ClassNotFoundException, SQLException {

		String sql = "INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC , FILESRC2) "
				+ "VALUES( (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'bituser', SYSDATE, 0, ?,?)";
		return this.jdbctemplate.update(sql, n.getTitle(),n.getContent(),n.getFileSrc(),n.getFileSrc2());
		}

	}