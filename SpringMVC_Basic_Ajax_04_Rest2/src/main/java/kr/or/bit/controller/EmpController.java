package kr.or.bit.controller;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit.dto.EmpDto;
import kr.or.bit.service.EmpService;

@Controller
@RequestMapping("/emp/*")
public class EmpController {

	private static final Logger logger = LoggerFactory.getLogger(EmpController.class);
	
	@Autowired
	private EmpService service;
	
	// 날짜 String을 Date타입으로 변환
	/*
	jquery datepicker를 이용하여 날짜를 지정해도 결국 넘어오는 타입은
	"2020-11-07" 과 같이 String입니다.
	String타입을 Date형으로 변환해야 할 일이 있는데 그럴때 @InitBinder를 이용하면 됩니다.
    그런데 initBinder의 역할이 단순히 String -> Date 의 변환을 자동으로 해주는 것이 아니라
	validator의 역할로서 개발자가 정의한 내용에 따라 데이터타입을 맞춰주게 됩니다.
	그러니 각 이슈에 맞게 initBinder를 정의해주면 됩니다.
    */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insertEmp() {
		logger.info("[GET] insertEmp()");
		
		return "emp/insertEmp";
	}

	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insertEmp(EmpDto dto) {
		logger.info("[POST] insertEmp()");
		logger.info(dto.toString());
		
		try {
			service.registerEmp(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/emp/list";
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listEmp(Model model) {
		logger.info("[GET] listEmp()");
		try {
			List<EmpDto> empList=service.listEmp();
			
			model.addAttribute("emplist", empList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "emp/listEmp";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String viewEmp(int empno, Model model) {
		logger.info("[GET] viewEmp()");
		logger.info("EMPNO : " + empno);
		try {
			EmpDto dto = service.readEmp(empno);	
			model.addAttribute("emp", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "emp/viewEmp";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String editEmp(int empno, Model model) {
		logger.info("[GET] editEmp()");
		logger.info("EMPNO : " + empno);
		
		try {
			EmpDto dto = service.readEmp(empno);
			model.addAttribute("emp", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "emp/updateEmp";
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String editEmp(EmpDto dto) {
		logger.info("[POST] editEmp()");
		logger.info("EMP : " + dto.toString());
		int empno = 0;
		try {
			empno = dto.getEmpno();
			service.updateEmp(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/emp/view?empno=" + empno;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteEmp(int empno) {
		logger.info("[POST] deleteEmp()");
		logger.info("EMPNO : " + empno);
		try {
			service.removeEmp(empno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/emp/list";
	}

}
