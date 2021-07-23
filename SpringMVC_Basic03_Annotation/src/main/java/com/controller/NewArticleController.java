package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model.NewArticleCommand;
import com.service.ArticleService;


/*중요
-클라이언트 요청
1.화면주세요(글쓰기, 로그인하기) : write.do
2.처리해주세요(글쓰기 입력처리, 로그인완료 처리 등) : writeOk.do

요청주소가 : write.do >> 화면
요청주소가 : writeOk.do >> 처리

클라이언트의 요청 /주소 1개를 가지고 나누어서 쓰고 싶은거임
요청주소 1개로 (화면, 처리) 판단 할수 있을까? >> 근거 : 전송방식이 다르잖아? GET,POST
>> http://localhost:8090/SpringMVC/article/newArticle.do 이렇게 들어갔을때
그 전송방식이 
1.GET 방식이라면 >> 화면이구나 >> view 제공
2.POST 방식이라면 >> 처리(서비스)구나 >> insert,update 같은 service 처리

 */
@Controller
@RequestMapping("/article/newArticle.do")
public class NewArticleController {		
	
	private ArticleService articleservice;
	
	//setter를 만들자
	@Autowired //자동 주소값 주입, but 쓰려면 뭔가 ..... 해야할게 있었잖아.....? dispatcher-servlet.xml 파일 확인
	public void setArticleservice(ArticleService articleservice) {
		this.articleservice = articleservice;
	}


	@RequestMapping(method=RequestMethod.GET)  
	//당신이 던진 방식이 get방식이라면 (열거형) 당신에게 화면을 제공하겠다 , 서비스 만들지 않고 setpath한거랑 같은거야
	public String form() {  //아까는 return 타입이 modelandview 였는데? 차이를 확인
							//return 되는 함수의 타입이 String 이라면 view 주소라고 하자(약속 같은거래)
		return "article/newArticleForm";
		//실제로는 resolver 때문에 /WEB-INF/views/ + article/newArticleForm + jsp
	}
	
	
	
	/*1. 무식한 방법이자 데이터를 받는 가장 전통적인 방법 >> submit(HttpServletRequest request) >> 코드량 때문에 spring에선 선호하지 않음
	@RequestMapping(method=RequestMethod.POST) //insert 처리하겠다
	public ModelAndView submit(HttpServletRequest request){ //얘는 데이터를 가지고 갈거라서 ModelAndView 타입으로 적어줘야한대
		
		NewArticleCommand article = new NewArticleCommand();
		article.setParentId(Integer.parseInt(request.getParameter("parentId")));
		article.setTitle(request.getParameter("title"));
		article.setContent(request.getParameter("content"));
		
		//서비스 객체가 필요해 31번째줄 NewArticleController class가
		//집합연관, 복합연관/ setter 생성자 주입으로 주소값을 받겠대
		this.articleservice.writeArticle(article);
		//dispatcher-servlet에 annotation-config를 해줘서 가능한거야(autowired)
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("newArticleCommand",article);
		mv.setViewName("article/newArticleSubmitted");
		
		return mv;
	}
	
	
	//2.Spring 에서 parameter 를 DTO 객체로 받기
	//2.1 자동화 >> 전제조건 >> input 태그의 name 속성값이 DTO객체의 memberfield 명과 동일해야함 대소문자까지!!!!!!!!!
	@RequestMapping(method=RequestMethod.POST) 
	public ModelAndView submit(NewArticleCommand command){ //DTO객체가 바로 들어감,그래서 new도 필요없고 set도 필요없지
		//1. 자동 DTO 객체 생성 : NewArticleCommand command = new NewArticleCommand();
		//2. 넘어온 parameter 값이 setter 통해서 자동주입
		//3. NewArticleCommand 객체가 IOC 컨테이너 안에 자동생성 >> 자동생성 될때 id값이 자동으로 만들어짐 >> id="newArticleCommand"
		
		this.articleservice.writeArticle(command);
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("newArticleCommand",command);
		mv.setViewName("article/newArticleSubmitted");
		
		return mv;
	}
	*/
	
	//3.
	@RequestMapping(method=RequestMethod.POST) 
	public String submit(@ModelAttribute("Articledata") NewArticleCommand command){ 
		
		this.articleservice.writeArticle(command);
		
		//view페이지가 데이터를 받아야 되는데.....? 코드가 다없어져버렷네
		//헐 자동화한대
		//NewArticleCommand 객체가 IOC 컨테이너 안에 자동생성 >> 자동생성 될때 id값이 자동으로 만들어짐 >> id="newArticleCommand"
		
		//되고 자동으로 forward가 된대 >> 그럼 view에서 어떤 이름으로 받는거야.....?
		//id값인 newArticleCommand가 key값이되서 자동 forward가 된대
		
		//forward되는 key의 이름은 내가 정하고 싶은데요 >> mv.addObject("내마음대로",command);
		//방법? 어노테이션이 있죠 @ModelAttribute
		//저렇게 쓰면 mv.addObject("Articledata",command); 가 되는거죠
		
		return "article/newArticleSubmitted";
	}
}













