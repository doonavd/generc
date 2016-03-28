package com.ccq.framework.dao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccq.framework.lyorm.InterfaceMaker;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public class GenercDao implements InitializingBean{

	//使用sqlSessionaTemplete的线程安全会话
	@Autowired
	private SqlSession sqlSession;

	/**
	 * key-value    dmoclass   mapperInterfaceClass
	 */
	public static ConcurrentMap<Class<?>, Class<?>>  classMap = new ConcurrentHashMap<Class<?>, Class<?>>();

	public void afterPropertiesSet() throws Exception {

		//校验工作
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	//获取Sqlsession的方式
	public SqlSession getSqlSession(Class<?> dmoClass) throws CannotCompileException, IOException, NotFoundException {

		//如果当前的mapper中没有这个类型的mapper，则创建新的
		if(!classMap.containsKey(dmoClass)) {
			//InterfaceMaker maker = new InterfaceMaker();
			Class<?> targetClass = InterfaceMaker.make(dmoClass);

			//添加映射注解
			classMap.put(dmoClass, targetClass);
			getSqlSession().getConfiguration().addMapper(targetClass);
		}
		return sqlSession;
	}

	public <T> int insert(T dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return getSqlSession(dmo.getClass()).insert(dmo.getClass().getSimpleName()+".insert",dmo);
	}
	
	public <T> int update(T dmo) throws CannotCompileException, IOException, NotFoundException {
		
		return getSqlSession(dmo.getClass()).update(dmo.getClass().getSimpleName()+".update",dmo);
	}

	public <T> T selectOne(T dmo) throws CannotCompileException, IOException, NotFoundException {
		// TODO Auto-generated method stub
		return getSqlSession(dmo.getClass()).selectOne(dmo.getClass().getSimpleName()+".selectOne",dmo);
	}

	public <T> List<T> selectList(T dmo) throws CannotCompileException, IOException, NotFoundException {
		// TODO Auto-generated method stub
		return getSqlSession(dmo.getClass()).selectList(dmo.getClass().getSimpleName()+".selectList",dmo);
	}
	public <T> Long selectCount(T dmo) throws CannotCompileException, IOException, NotFoundException {
		// TODO Auto-generated method stub
		return getSqlSession(dmo.getClass()).selectOne(dmo.getClass().getSimpleName()+".selectCount",dmo);
	}
}
