package com.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

/*
 이제 안쓸꺼야~~~~~~~~~~
 implements Controller 작업해서 handleRequest 함수를 쓰는 방식은
 단점 : 서비스요청 개수만큼 Controller 생성
 게시판 : 목록보기 >> listController
 		글쓰기  >> writeController
 		수정하기 >> editController
 		
public class HelloController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
***대안은 ? Annotation 이다!
   @Controller >> method 단위로 service 매핑 (함수만 달라짐)
   하나의 controller 안에서
   게시판 : 목록보기 >> list() 
 		  글쓰기  >> write()
 		  수정하기 >> edit()    //list함수,write함수,edit함수
*/


@Controller
public class HelloController{
	public HelloController() {
		System.out.println("HelloController 생성자 호출");
	}
	
	/*@RequestMapping("/hello.do")       //mapping 단위가 클래스에서 함수로 내려온대 그래서 여기다가 ("/.do")를 쓸수있대
	public ModelAndView hello() {	   //<a href="hello.do"></a>
		System.out.println("[hello.do method call]");
		ModelAndView mv = new ModelAndView();
		mv.addObject("greeting","hello");
		mv.setViewName("Hello");
		
		return mv;
	}*/
	
	@RequestMapping("/hello.do")       //mapping 단위가 클래스에서 함수로 내려온대 그래서 여기다가 ("/.do")를 쓸수있대
	public ModelAndView hello() {	   //<a href="hello.do"></a>
		System.out.println("[hello.do method call]");
		ModelAndView mv = new ModelAndView();
		mv.addObject("greeting",getGreeting());
		mv.setViewName("Hello");
		
		return mv;
	}
	
private String getGreeting() {
	int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	String data="";
	if(hour >= 6 && hour <= 10) {
		data="학습시간";
	}else if(hour >= 11 && hour <= 13) {
		data="배고픈 시간";
	}else if(hour >= 14 && hour <= 18) {
		data="졸려운 시간";
	}else {
		data="go home";
	}
	return data;
}
}












