package kr.or.bit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class IntroController implements Controller{

	@Override			//강제적으로 handleRequest함수를 구현해야함
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("IntroController 요청 실행 : handleRequest 함수 실행");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("name","hong");
		mav.setViewName("Intro");
		
		return mav;
	}

}
