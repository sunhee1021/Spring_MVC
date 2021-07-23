package kr.or.bit.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dto.EmpDto;
import kr.or.bit.service.EmpService;

@RestController
@RequestMapping("/emps")
public class EmpRestController {

	
	@Autowired
	private EmpService service;
	
	//날짜 String을 Date타입으로 변환 
	//해당 Controller로 들어오는 요청에 대해
	//추가적인 설정을 하고 싶을 때 사용할 수 있다.
	//또한 모든 요청전에 InitBinder를 선언한 메소드가 실행된다.
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<EmpDto> listEmp() {
		List<EmpDto> empList = null;
		try {
			empList=service.listEmp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(empList);
		
		return empList;
	}
	
	// 조회 
	@RequestMapping(value="/{empno}", method=RequestMethod.GET)
	public EmpDto viewEmp(@PathVariable("empno") int empno) {
		EmpDto dto = null;
		try {
			dto = service.readEmp(empno);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	// Update 시작 
	@RequestMapping(value="/{empno}/update", method= {RequestMethod.GET })
	public EmpDto editEmp(@PathVariable("empno") int empno) {
		EmpDto dto = null;
		try {
			dto = service.readEmp(empno);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	
	// Update 
	@RequestMapping(value="/{empno}", method= {RequestMethod.PUT })
    public ResponseEntity<String> editEmp(@PathVariable("empno") int empno, @RequestBody EmpDto dto) {
        
		ResponseEntity<String> entity = null;
					
        try {
            
            service.updateEmp(dto);
            entity = new ResponseEntity<String>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }
		
   
	// Delete
	@RequestMapping(value="/{empno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmp(@PathVariable("empno") int empno) {
		
		ResponseEntity<String> entity = null;
		
		try {
			service.removeEmp(empno);
            entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	// Insert
	@RequestMapping(value="/new", method= {RequestMethod.POST })
    public ResponseEntity<String> insrtEmp(@RequestBody EmpDto dto) {
		System.out.println("EmpDto" + dto.toString());
		ResponseEntity<String> entity = null;
		
        try {
            service.registerEmp(dto);
            entity = new ResponseEntity<String>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(entity);
        
        return entity;
    }

}
