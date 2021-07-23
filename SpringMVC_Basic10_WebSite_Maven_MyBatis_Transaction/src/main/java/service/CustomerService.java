package service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dao.NoticeDao;
import vo.Notice;

@Service
public class CustomerService {
	// DB작업
	// Mybatis >> Root IOC >> DI
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// 글목록보기 서비스함수
	public List<Notice> notices(String pg, String f, String q) {

		// default
		int page = 1;
		String field = "TITLE";
		String query = "%%";

		// 조건처리
		if (pg != null && !pg.equals("")) {
			page = Integer.parseInt(pg);
		}

		if (f != null && !f.equals("")) {
			field = f;
		}

		if (q != null && !q.equals("")) {
			query = q;
		}

		// DAO 데이터 받아오기
		List<Notice> list = null;
		try {
			// mapper 를 통한 인터페이스 연결
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			//
			list = noticedao.getNotices(page, field, query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 글상세보기 서비스 함수
	public Notice noticeDetail(String seq) {
		Notice notice = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notice;
	}

	// 글쓰기처리 서비스 함수
		// 트랜잭션 처리
		@Transactional
		public String noticeReg(Notice n, HttpServletRequest request) throws Exception {

			List<CommonsMultipartFile> files = n.getFiles();
			List<String> filenames = new ArrayList<String>(); // 파일명관리

			if (files != null && files.size() > 0) { // 최소 1개의 업로드가 있다면
				for (CommonsMultipartFile multifile : files) {
					String filename = multifile.getOriginalFilename();
					String path = request.getServletContext().getRealPath("/customer/upload");

					String fpath = path + "\\" + filename;

					if (!filename.equals("")) { // 실 파일 업로드
						FileOutputStream fs = new FileOutputStream(fpath);
						fs.write(multifile.getBytes());
						fs.close();
					}
					filenames.add(filename); // 파일명을 별도 관리 (DB insert)
				}

			}

			// DB 파일명 저장
			n.setFileSrc(filenames.get(0));
			n.setFileSrc2(filenames.get(1));

			
			/*
					insert    : noticedao.insert(n);
					update  : dao 인터페이스 >> 함수(updateOfMemberPoint)  >>  mapper(updateOfMemberPoint) 
			
				   1. transactionManager bean객체 생성
				   2. @Transactional 동작을 지원하는 <tx:annotation-driven 
				   3. 적용될 함수에 @Transactional 을 선언
				   4. 두개의 dao함수를 실행  (insert , update)
				   4.1 : 정상 Commit  (게시글 2개 ...
				   4.2: 실패 Rollback  (게시글 3.... 
				   5. 다양한 처리 방법 중에서 [예외] 발생하면 default rollback
				       transactionManager @Transactional 붙은 함수를 감시하다가 exception 발생하면 모든 처리 rollback
				       예외) check 제약 위반
			*/
			//mapper 사용
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);

			try {
				noticedao.insert(n);
				noticedao.updateOfMemberPoint("kglim");
				System.out.println("정상 : notice > insert ,   member > update");
			} catch (Exception e) {
					//POINT
				    System.out.println("Transaction 문제 발생 : " + e.getMessage());
				    throw e;
				    //서비스르 실해한 주체가  controller 고  이 주체에게 예외 던지기 ....
			}

			return "redirect:notice.htm"; // 문자열로 리턴

		}

	// 글삭제하기 서비스 함수
	public String noticeDel(String seq) throws ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		noticedao.delete(seq);
		return "redirect:notice.htm";
	}

	// 글수정하기 서비스 함수 (select 화면)
	public Notice noticeEdit(String seq) throws ClassNotFoundException, SQLException {
	
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		Notice notice = noticedao.getNotice(seq);

		return notice;
	}

	// 글수정하기 처리 서비스 함수(update 처리)
	public String noticeEdit(Notice n, HttpServletRequest request)
			throws IOException, ClassNotFoundException, SQLException {

		List<CommonsMultipartFile> files = n.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명관리

		if (files != null && files.size() > 0) { // 최소 1개의 업로드가 있다면
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload");

				String fpath = path + "\\" + filename;

				if (!filename.equals("")) { // 실 파일 업로드
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename); // 파일명을 별도 관리 (DB insert)
			}

		}

		// DB 파일명 저장
		n.setFileSrc(filenames.get(0));
		n.setFileSrc2(filenames.get(1));

		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		noticedao.update(n);
		return "redirect:noticeDetail.htm?seq=" + n.getSeq();
	}

	// 파일 다운로드 서비스 함수
	public void download(String p, String f, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String fname = new String(f.getBytes("euc-kr"), "8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");

		String fullpath = request.getServletContext().getRealPath("/customer/" + p + "/" + f);
		System.out.println(fullpath);
		FileInputStream fin = new FileInputStream(fullpath);

		ServletOutputStream sout = response.getOutputStream();
		byte[] buf = new byte[1024]; // 전체를 다읽지 않고 1204byte씩 읽어서
		int size = 0;
		while ((size = fin.read(buf, 0, buf.length)) != -1) {
			sout.write(buf, 0, size);
		}
		fin.close();
		sout.close();
	}
}
