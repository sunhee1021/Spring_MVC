package kr.or.bit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dto.EmpDto;
import kr.or.bit.service.EmpService;

@RestController
@RequestMapping("/emp")
public class AjaxController {

	@Autowired
	private EmpService service;

	//INSERT
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<String> insertEmp(@RequestBody EmpDto dto){
		
		ResponseEntity<String> entity = null;
		
		try {
			System.out.println("등록 성공");
			System.out.println(dto.toString());
			service.registerEmp(dto);
			
			return new ResponseEntity<String>("INSERT SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);  //400
		}

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
		@RequestMapping(value="/{empno}", method= {RequestMethod.PUT})
	    public ResponseEntity<String> editEmp(@PathVariable("empno") int empno, @RequestBody EmpDto dto) {
	        
			ResponseEntity<String> entity = null;
						
	        try {
	            
	            service.updateEmp(dto);
	            return new ResponseEntity<String>("UPDATE SUCCESS", HttpStatus.OK);
	        } catch (Exception e) {
	        	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	    }

		
	//DELETE		( /empdata/100)    >>   100번 게시물 수정
	@RequestMapping(value = "/{empno}" , method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmp(@PathVariable("empno")int empno){
		
		ResponseEntity<String> entity = null;
		
		try {
			//등록
			service.removeEmp(empno);
			System.out.println("삭제요청 사원번호 : " + empno);
			System.out.println("삭제 성공");
			return new ResponseEntity<String>("DELETE SUCCESS", HttpStatus.OK);  //200
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);  //400
		}
	}
	
	//SELECT
	
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
}
