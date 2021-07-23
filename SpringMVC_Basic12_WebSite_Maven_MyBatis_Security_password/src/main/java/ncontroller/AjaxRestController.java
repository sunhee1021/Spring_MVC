package ncontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import service.JoinService;


@RestController   //@Controller + @ResponseBody
@RequestMapping("/joinus/")
public class AjaxRestController {
	
	@Autowired
	private JoinService joinservice;
	
	/*@RequestMapping(value="checkId.do")
	public String checkId(){
		System.out.println("아이디 중복확인");
		return "checkId.do";
	}*/
	
	@RequestMapping(value = "checkId.do", method = RequestMethod.POST)
	public String idCheck(@RequestParam("userid") String userid) {
		int result = joinservice.idCheck(userid);
		String str = "";
		if (result > 0) {
			System.out.println("아이디 중복");
			return str = "YES";
		} else {
			System.out.println("삽입 실패");
			return str = "NO";
		}
	}
	
}
