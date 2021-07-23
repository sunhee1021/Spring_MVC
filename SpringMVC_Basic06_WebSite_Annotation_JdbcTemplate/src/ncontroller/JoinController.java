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
	
	private MemberDao memberdao;

	@Autowired
	public void setMemberdao(MemberDao memberdao) {
		this.memberdao = memberdao;
	}
	
	
	//회원가입 페이지 요청(GET)
	@RequestMapping(value="join.htm" , method=RequestMethod.GET)
	public String join() {
		return "join.jsp";
	}
	
	
	
	//회원가입 페이지 처리(POST)
	@RequestMapping(value="join.htm" , method=RequestMethod.POST)
	public String join(Member member) {
		System.out.println(member.toString());
		//Member [userid=null, pwd=2, name=3, gender=남성, birth=, isLunar=Solar, cphone=7, email=8, habit=on, regDate=null]
		
		try {
			memberdao.insert(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/index.htm";
		   //리다이렉트 다음에 / 를 쓰면 root 경로로 빠져버린대
		
		//http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/customer/notice.htm
		//   /index.htm 으로 해주면 root 경로로 빠져서 더 상위 경로를 무시하고 아래로 인식
		//http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
	}
}





















