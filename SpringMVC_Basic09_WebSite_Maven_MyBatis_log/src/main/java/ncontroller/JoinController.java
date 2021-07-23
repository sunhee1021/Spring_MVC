package ncontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.MemberDao;
import vo.Member;

@Controller
@RequestMapping("/joinus/")
public class JoinController {

	private MemberDao memberdao;
	
	@Autowired
	public void setMemberdao(MemberDao memberdao) {
		this.memberdao = memberdao;
	}
	
	//회원가입 페이지 (GET)
	@RequestMapping(value="join.htm" , method = RequestMethod.GET)
	public String join() {
		return "joinus/join";  //view  주소
		//    /WEB-INF/views/ + joinus/join + .jsp
	}
	
	//회원가입 처리 (POST)
	@RequestMapping(value="join.htm" , method = RequestMethod.POST)
	public String join(Member member) {
		System.out.println(member.toString());
	
		try {
			memberdao.insert(member);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/index.htm";
		//http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
	}
}








