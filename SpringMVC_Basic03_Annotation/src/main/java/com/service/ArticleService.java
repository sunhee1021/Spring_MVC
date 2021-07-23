package com.service;

import com.model.NewArticleCommand;

public class ArticleService {
	/*
	 @Service
	 public class ArticleService
	 
	 servlet.xml 상단에 적으면 scan을 한대요, com.service 폴더밑에 있는걸 싹 찾아서 객체로?빈으로 만든대요
	 <context:component-scan base-package="com.service">
	 * */
	public ArticleService() {
		System.out.println("ArticleService 생성자 호출");
	}
	
	public void writeArticle(NewArticleCommand command) {
		//DAO 있다고 가정
		//insert 실행되었다고 가정하고 syso만 할께요
		System.out.println("글쓰기 작업 완료 : " + command.toString());
	}
}
