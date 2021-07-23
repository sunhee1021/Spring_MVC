package ncontroller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.MemberDao;
import vo.Member;

@Controller
@RequestMapping("/joinus/")
public class JoinController {
	
		private MemberDao  memberdao;

		@Autowired
		public void setMemberdao(MemberDao memberdao) {
			this.memberdao = memberdao;
		}
		
		//회원가입 페이지(GET)
		@RequestMapping(value = "join.htm" , method = RequestMethod.GET)
		public String join() {
			//return "join.jsp";
			return "joinus/join";
		}
		
		//회원가입 처리(POST)
		@RequestMapping(value = "join.htm" , method = RequestMethod.POST)
		public String join(Member member) {
			System.out.println(member.toString());
			//Member [userid=null, pwd=2, name=3, gender=남성, birth=, isLunar=Solar, cphone=7, email=8, habit=on, regDate=null]
			try {
			     	memberdao.insert(member);
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
			return "redirect:/index.htm";
			
		  //	http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/joinus/join.htm
	 	  //  return "redirect:index.htm";
		 //   http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/joinus/index.htm	
			
			
		 //   /index.htm    (root경로)
		 //   http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		}
	
}
