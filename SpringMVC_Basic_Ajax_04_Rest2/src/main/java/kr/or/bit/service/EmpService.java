package kr.or.bit.service;

import java.util.List;

import kr.or.bit.dto.EmpDto;

public interface EmpService {

	public void registerEmp(EmpDto dto) throws Exception;
	
	public EmpDto readEmp(int empno) throws Exception;
	
	public List<EmpDto> listEmp() throws Exception;
	
	public void updateEmp(EmpDto dto) throws Exception;
	
	public void removeEmp(int empno) throws Exception;
	
}
