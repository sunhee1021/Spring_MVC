package controllers.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import dao.NoticeDao;
import vo.Notice;

/*
 게시판 목록 조회 
 
 controller 가 -> Model의존 (DAO, DTO)가 필요
 
 NoticeController는 NoticeDAO에 의존한다
 필요하면 값을 받아야 해요 ... (spring에서는 DI,injection(생성자,함수(setter)를 통해서 주입받는다) 
 
 
 */

public class NoticeController implements Controller{
	
	public NoticeController() {
		System.out.println("[NoticeController]");
	}
	
	private NoticeDao noticedao;
	public void setNoticedao(NoticeDao noticedao) {
		this.noticedao = noticedao;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//DAO 객체 사용
		//public List<Notice> getNotices(int page,String field,String query)
		
		String _page = request.getParameter("pg");
		String _field = request.getParameter("f");
		String _query = request.getParameter("q");
		
		//default 값 설정
		int page = 1;
		String field="TITLE";
		String query = "%%";
		if(_page != null && !_page.equals("")) {
			page = Integer.parseInt(_page);
		}
		
		if(_field != null && !_field.equals("")) {
			field = _field;
		}
		
		if(_query != null && !_query.equals("")) {
			query = _query;
		}
		
		//DAO 작업 
		List<Notice> list = noticedao.getNotices(page,field,query);
		
		
		//Spring 적용
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("notice.jsp");

		//resolver 가 없으면 폴더를 기준으로 스스로 찾아간대
		//resolver 가 있으면 거기에 붙어가는거고, 필수사항은 아니지만 거의다 쓴대
	
		return mv;
	}

}
