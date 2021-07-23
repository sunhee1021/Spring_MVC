package ncontroller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Member;

public class MemberDao {
	public Member getMember(String userid) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * FROM MEMBER WHERE USERID=?";
		// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"springuser", "1004");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, userid);
		// 3. 결과
		ResultSet rs = st.executeQuery();
		
		Member member = null;
		
		if(rs.next())
		{
			member = new Member();
			member.setUserid(rs.getString("userid"));
			member.setPwd(rs.getString("pwd"));
			member.setName(rs.getString("name"));
			member.setGender(rs.getString("gender"));
			member.setBirth(rs.getString("birth"));
			member.setIsLunar(rs.getString("is_lunar"));
			member.setCphone(rs.getString("cphone"));
			member.setEmail(rs.getString("email"));
			member.setHabit(rs.getString("habit"));
			member.setRegDate(rs.getDate("regdate"));
		}
		
		rs.close();
		st.close();
		con.close();
		
		return member;
	}
	
	public int insert(Member member) throws ClassNotFoundException, SQLException
	{
		String sql = "INSERT INTO MEMBER(USERID, PWD, NAME, GENDER, BIRTH, IS_LUNAR, CPHONE, EMAIL, HABIT, REGDATE) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		// 0. 드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"springuser", "1004");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, member.getUserid());
		st.setString(2, member.getPwd());
		st.setString(3, member.getName());
		st.setString(4, member.getGender());
		st.setString(5, member.getBirth());
		st.setString(6, member.getIsLunar());
		st.setString(7, member.getCphone());
		st.setString(8, member.getEmail());
		st.setString(9, member.getHabit());
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}
}
