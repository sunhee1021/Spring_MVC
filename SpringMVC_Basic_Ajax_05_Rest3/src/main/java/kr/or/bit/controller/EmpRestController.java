package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dto.Emp;

@RestController
@RequestMapping("/sample")
public class EmpRestController {
	
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello world";
	}
	
	@RequestMapping("/empvo")
	public Emp empvo() {
		Emp emp = new Emp();
		emp.setEmpno(2000);
		emp.setEname("홍길동");
		return emp;
	}
	
	@RequestMapping("/emplist")
	public ResponseEntity<List<Emp>> sendList(){
		List<Emp>  list = new ArrayList<Emp>();
		for(int i = 0; i < 3 ;i++) {
			Emp emp = new Emp();
			emp.setEmpno(i);
			emp.setEname("아무개");
			list.add(emp);
		}
		
		
		return  new ResponseEntity<List<Emp>>(list,HttpStatus.NOT_FOUND);
	}
	
	
	@RequestMapping("/emplist2")
	public List<Emp>  sendList2(){
		List<Emp>  list = new ArrayList<Emp>();
		for(int i = 0; i < 3 ;i++) {
			Emp emp = new Emp();
			emp.setEmpno(i);
			emp.setEname("아무개");
			list.add(emp);
		}
		return  list;
	}
}



