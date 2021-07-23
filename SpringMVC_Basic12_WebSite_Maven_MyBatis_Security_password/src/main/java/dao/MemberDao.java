package dao;

import java.sql.SQLException;

import vo.Member;

public interface MemberDao {
	    //회원가입
		public int insert(Member member) throws ClassNotFoundException, SQLException;
		
		//회원정보 얻기
		public Member getMember(String uid) throws ClassNotFoundException, SQLException;

		//회원 ID 검증 (비동기 처리)
		public int idCheck(String userid);
		
		//로그인
		public int loginCheck(String username, String password);
		
		//회원수정
		public int updateMember(Member member);

}
