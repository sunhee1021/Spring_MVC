package ncontroller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NoInitialContextException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dao.NoticeDao;
import service.CustomerService;
import vo.Notice;

@Controller
@RequestMapping("/customer/")
public class CustomerController {

	private CustomerService customerservice;
	@Autowired
	public void setCustomerservice(CustomerService customerservice) {
		this.customerservice = customerservice;
	}

	// 글목록보기
	@RequestMapping("notice.htm") // /customer/notice.htm
	public String notices(String pg, String f, String q, Model model) {

		List<Notice> list= customerservice.notices(pg, f, q);
		model.addAttribute("list", list); // view까지 forward 되요
		return "customer/notice";
	}

	// 글상세보기
	@RequestMapping("noticeDetail.htm") // /customer/noticeDetail.htm
	public String noticeDetail(String seq, Model model) {
		
		Notice notice = customerservice.noticeDetail(seq);
		model.addAttribute("notice", notice);

		return "customer/noticeDetail";
	}

	// 글쓰기 화면 GET
	// @RequestMapping(value="noticeReg.htm",method=RequestMethod.GET)
	// 글쓰기 처리 POST (파일업로드)
	// @RequestMapping(value="noticeReg.htm",method=RequestMethod.POST)

	// 글삭제하기 (페이지 새로 요청)
	// hint) location.href
	// return "redirect:notice.htm"

	// 글쓰기 화면 (noticeReg.htm)
	// @RequestMapping(value="noticeReg.htm" , method=RequestMethod.GET)

	// /customer/noticeReg.htm
	@RequestMapping(value = "noticeReg.htm", method = RequestMethod.GET)
	public String noticeReg() {
		return "customer/noticeReg";
	}

	@RequestMapping(value = "noticeReg.htm", method = RequestMethod.POST)
	public String noticeReg(Notice n, HttpServletRequest request)
			throws Exception {
		 String url = customerservice.noticeReg(n, request);
		 return url;
	}

	// 글수정하기 (화면 : select .... where seq=?) : GET : seq (parameter)
	// noticedao.getNotice(seq)
	// Model model >> 화면 >> 데이터 >> noticeEdit.jsp
	
	@RequestMapping(value = "noticeEdit.htm", method = RequestMethod.GET)
	public String noticeEdit(String seq, Model model) throws ClassNotFoundException, SQLException {

		Notice notice = customerservice.noticeEdit(seq);
		model.addAttribute("notice", notice);
		return "customer/noticeEdit";

	}

	// 글수정하기(처리 : update..... where seq=?) : POST
	@RequestMapping(value = "noticeEdit.htm", method = RequestMethod.POST)
	public String noticeEdit(Notice n, HttpServletRequest request)
			throws IOException, ClassNotFoundException, SQLException {
	
		return customerservice.noticeEdit(n, request);
	
	}

	@RequestMapping("noticeDel.htm") // /customer/noticeDel.htm
	public String noticeDel(String seq) throws ClassNotFoundException, SQLException {

		return customerservice.noticeDel(seq);
	}
	
	@RequestMapping("download.htm")
	public void download(String p , String f , HttpServletRequest request , HttpServletResponse response) throws IOException {
		customerservice.download(p, f, request, response);
	}
}
