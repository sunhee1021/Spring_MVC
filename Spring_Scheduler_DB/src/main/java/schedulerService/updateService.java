package schedulerService;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import dao.MemberDAO;


public class updateService {
	
	@Autowired
	private SqlSession sqlsession;
	
	public void update(){
		System.out.println("스케줄러 update 완료");
		
		MemberDAO dao = sqlsession.getMapper(MemberDAO.class);
		dao.update("team2");
	}
}