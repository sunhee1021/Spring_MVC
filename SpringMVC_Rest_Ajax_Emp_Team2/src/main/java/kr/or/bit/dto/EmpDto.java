package kr.or.bit.dto;

import lombok.Data;

@Data
public class EmpDto {
	private int empno;
	private String ename;
	private int sal;
	
	@Override
	public String toString() {
		return "EmpDto [empno=" + empno + ", ename=" + ename + ", sal=" + sal + "]";
	}

}
