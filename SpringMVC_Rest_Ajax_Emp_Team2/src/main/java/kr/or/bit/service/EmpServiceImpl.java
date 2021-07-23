package kr.or.bit.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.bit.dao.EmpDao;
import kr.or.bit.dto.EmpDto;

@Service
public class EmpServiceImpl implements EmpService {

	@Inject
	private EmpDao dao;
	
	@Override
	public void registerEmp(EmpDto dto) throws Exception {
		dao.insertEmp(dto);
	}

	@Override
	public EmpDto readEmp(int empno) throws Exception {
		return dao.getEmpByEmpNo(empno);
	}

	@Override
	public List<EmpDto> listEmp() throws Exception {
		return dao.getEmpList();
	}

	@Override
	public void updateEmp(EmpDto dto) throws Exception {
		dao.updateEmp(dto);
	}

	@Override
	public void removeEmp(int empno) throws Exception {
		dao.deleteEmp(empno);	
	}

}
