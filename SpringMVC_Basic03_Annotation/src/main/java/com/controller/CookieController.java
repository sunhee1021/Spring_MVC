package com.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CookieController {
	
	@RequestMapping("/cookie/make.do")
	public String make(HttpServletResponse response) {
		response.addCookie(new Cookie("auth", "1004")); //jsp & servlet과 동일
		return "cookie/CookieMake";
		
	}
	
	@RequestMapping("/cookie/view.do")
	public String view(@CookieValue(value="auth", defaultValue="1007") String auth) {  
										 //request로 읽어도 되는데 그건 너무 전통적인 방법이고
		                                 //어노테이션 쓰면 기본값을 잡아서 쓸수 있게 해줘요
		System.out.println("클라이언트에서 read한 쿠키값 : " +auth);
		return "cookie/CookieView";
		//f12눌러서 cookie 지워도
		//console에서 default 1007 찍히는걸 확인할수 있음
		
	}
}
