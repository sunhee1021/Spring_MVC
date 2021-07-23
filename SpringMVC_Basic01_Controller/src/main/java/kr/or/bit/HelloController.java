package kr.or.bit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller; //servlet에서 frontcontroller 역할

import org.springframework.web.servlet.DispatcherServlet;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class HelloController implements Controller{ //서블릿은 자동으로 new가 됐지 그치만 여기선 안돼
	
	public HelloController() {
		System.out.println("HelloController 객체생성");
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("HelloController 요청이 실행되고 : handleRequest라는 함수가 실행됨");
		
		ModelAndView mav = new ModelAndView();
		/*
		 ModelAndView(String viewName) : 응답할 view

		 ModelAndView(String viewName, Map values) : 응답할 view와 view로 전달할 값들을 저장한 Map 객체

		 ModelAndView(Stirng viewName, String name, Object value) : 응답할 view이름, view로 넘길 객체의 name 과 value
		 */
		
		mav.addObject("name","bituser");  //굳이 비교하자면 request.setAttribute("name","bituser")랑 같음
		//view에 전달할 값들을 설정
		
		
		mav.setViewName("Hello");
		//응답할 view 이름을 설정
		//InternalResourceViewResolver 에 의해서 view단의 주소가 조합
		// /WEB-INF/views/ + Hello + .jsp
		// >> 이게 합쳐지면 /WEB-INF/views/Hello.jsp
		
		
		return mav;
	}

}
