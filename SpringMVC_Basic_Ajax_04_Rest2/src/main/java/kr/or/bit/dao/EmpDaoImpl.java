package kr.or.bit.dao;

import java.text.SimpleDateFormat;
import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.bit.dto.EmpDto;

@Repository
public class EmpDaoImpl implements EmpDao {

	@Autowired
	private SqlSession session;
	
	private static String namespace = "mappers.empMapper";  
	
	@Override
	public void insertEmp(EmpDto dto) throws Exception {
		System.out.println(dto.getHiredate());
		session.insert(namespace+".insertEmp", dto);
	}

	@Override
	public List<EmpDto> getEmpList() throws Exception {
		return session.selectList(namespace+".getEmpList");
	}

	@Override
	public EmpDto getEmpByEmpNo(int empno) throws Exception {
		return session.selectOne(namespace+".getEmpByEmpNo", empno);
	}

	@Override
	public void updateEmp(EmpDto dto) throws Exception {
		session.update(namespace+".updateEmp", dto);
	}

	@Override
	public void deleteEmp(int empno) throws Exception {
		session.delete(namespace+".deleteEmp", empno);
	}

}
