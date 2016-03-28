package com.liuyao.test;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccq.framework.dao.GenercDao;

import javassist.CannotCompileException;
import javassist.NotFoundException;

@Repository("dao")
public class Daotest extends GenercDao{

	public int insert(ForeumDmo dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return super.insert(dmo);
	}
	
	
	public int update(ForeumDmo dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return super.update(dmo);
	}
	
	public ForeumDmo selectOne(ForeumDmo dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return super.selectOne(dmo);
	}
	
	public List<ForeumDmo> selectList(ForeumDmo dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return super.selectList(dmo);
	}
	
	public Long selectCount(ForeumDmo dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return super.selectCount(dmo);
	}
	
}

