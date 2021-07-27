package schedulerService;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import dao.MemberDAO;



public class insertService {	
	@Autowired
	private SqlSession sqlsession;
	
	                 //초 분 시 일 월 요일
	@Scheduled(cron = "0 34 14 * * *") 
	public void insert(){
		System.out.println("스케줄러 insert 실행");
		MemberDAO dao = sqlsession.getMapper(MemberDAO.class);
		dao.insert();
	}
}
