package com.ccq.framework.lyorm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.ibatis.jdbc.SQL;

import com.ccq.framework.exception.AppException;
import com.ccq.framework.lang.Result;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.ConstPool;

//用于创建 select provider
public class AnnotationProvider {

	//数据模型类
	private Class<?> dmoClass;
	//生成目标类
	private Class<?> targetClass;
	//常量池
	private ConstPool constPool;

	private CtClass proxClass;

	private ColumnMap columnMap;

	public ColumnMap getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(ColumnMap columnMap) {
		this.columnMap = columnMap;
	}

	public CtClass getProxClass() {
		return proxClass;
	}

	public void setProxClass(CtClass proxClass) {
		this.proxClass = proxClass;
	}

	public Class<?> getDmoClass() {
		return dmoClass;
	}

	public void setDmoClass(Class<?> dmoClass) {
		this.dmoClass = dmoClass;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public ConstPool getConstPool() {
		return constPool;
	}

	public void setConstPool(ConstPool constPool) {
		this.constPool = constPool;
	}

	/**
	 * gouzao
	 * @param dmoClass
	 */
	public AnnotationProvider() {
		super();
	}

	/**
	 * gouzao
	 * @param dmoClass
	 * @throws CannotCompileException 
	 */
	public AnnotationProvider(Class<?> dmoClass) {
		super();
		this.dmoClass = dmoClass;
		//编译columnmap信息
		columnMap = new AnnotationProvider.ColumnMap(dmoClass);
		columnMap.compiler();

		ClassPool pool = ClassPool.getDefault();
		setProxClass(pool.makeClass(String.format("%sProvider", dmoClass.getSimpleName())));
		try{
			getProxClass().addMethod(
					makeInsertMethod(getProxClass(),dmoClass));

			getProxClass().addMethod(
					makeUpdateMethod(getProxClass(),dmoClass));

			getProxClass().addMethod(
					makeSelectOneMethod(getProxClass(),dmoClass));

			getProxClass().addMethod(
					makeSelectListMethod(getProxClass(),dmoClass));

			getProxClass().addMethod(
					makeSelectCount(getProxClass(),dmoClass));
		}catch(CannotCompileException ex) {
			//抛出应用程序错误信息
			throw new AppException(ex.getMessage());
		}
	}

	/**
	 *  编译生成的类
	 * @throws CannotCompileException 
	 */
	public void compiler() {

		//compiler maked class
		try {
			Class cls = getProxClass().toClass();
			cls.newInstance();
		} catch (CannotCompileException e) {
			//can not compile
			throw new AppException(e.getMessage());
		}catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	//创造insert方法
	public CtMethod makeInsertMethod(CtClass type, Class<?> dmoClass) {

		CtMethod method = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("public  String insert(%s insertDmo){",dmoClass.getName()));
			sb.append("org.apache.ibatis.jdbc.SQL sql = new org.apache.ibatis.jdbc.SQL();");
			sb.append(String.format("sql.INSERT_INTO(\"%s\");",getColumnMap().getTableName()));
			for(String prop:getColumnMap().columnMap.keySet()) {
				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.VALUES(\"%s\",\"#{%s}\");}",new Object[]{getter,getColumnMap().columnMap.get(prop),prop}));
			}
			sb.append("return sql.toString();}");
			System.out.println(sb.toString());
			method = CtNewMethod.make(sb.toString(), type);
		} catch (CannotCompileException e) {
			throw new AppException(e.getMessage());
		}
		return method;
	}

	//创造update方法
	public CtMethod makeUpdateMethod(CtClass type, Class<?> dmoClass) {

		CtMethod method = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("public  String update(%s updateDmo){",dmoClass.getName()));
			sb.append("org.apache.ibatis.jdbc.SQL sql = new org.apache.ibatis.jdbc.SQL();");
			sb.append(String.format("sql.UPDATE(\"%s\");",getColumnMap().getTableName()));
			for(String prop:getColumnMap().keyProperty.keySet()) {

				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(updateDmo.get%s() != null) {sql.SET(\"%s = #{%s}\");}",
						new Object[]{getter,getColumnMap().keyProperty.get(prop),prop}));
				sb.append(String.format("if(updateDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",
						new Object[]{getter,getColumnMap().keyProperty.get(prop),prop}));
			}
			
			//根据主键更新，如果没有主键则失败
			for(String prop:getColumnMap().columnMap.keySet()) {
				
				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				//根据主键更新，如果没有主键则失败
//				sb.append(String.format("if(updateDmo.get%s() != null) {sql.SET(\"%s = #{%s}\");}",
//						new Object[]{getter,getColumnMap().columnMap.get(prop),prop}));
				sb.append(String.format("if(updateDmo.get%s() != null) {sql.SET(\"%s = #{%s}\");}",
						new Object[]{getter,getColumnMap().columnMap.get(prop),prop}));
			}
			sb.append("return sql.toString();}");
			System.out.println(sb.toString());
			method = CtNewMethod.make(sb.toString(), type);
		} catch (CannotCompileException e) {
			throw new AppException(e.getMessage());
		}
		return method;
	}

	//创造selectOne方法
	public CtMethod makeSelectOneMethod(CtClass type, Class<?> dmoClass) {

		CtMethod method = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("public  String selectOne(%s insertDmo){",dmoClass.getName()));
			sb.append("org.apache.ibatis.jdbc.SQL sql = new org.apache.ibatis.jdbc.SQL();");
			for(String prop:getColumnMap().keyProperty.keySet()) {

				sb.append(String.format("sql.SELECT(\"%s as %s\");",new Object[]{getColumnMap().keyProperty.get(prop),prop}));
			}
			for(String prop:getColumnMap().columnMap.keySet()) {

				sb.append(String.format("sql.SELECT(\"%s as %s\");",new Object[]{getColumnMap().columnMap.get(prop),prop}));
			}
			sb.append(String.format("sql.FROM(\"%s\");",getColumnMap().getTableName()));
			for(String prop:getColumnMap().keyProperty.keySet()) {

				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",new Object[]{getter,getColumnMap().keyProperty.get(prop),prop}));
			}
			for(String prop:getColumnMap().columnMap.keySet()) {
				
				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",new Object[]{getter,getColumnMap().columnMap.get(prop),prop}));
				}

			sb.append("return sql.toString();}");
			System.out.println(sb.toString());
			method = CtNewMethod.make(sb.toString(), type);
		} catch (CannotCompileException e) {
			throw new AppException(e.getMessage());
		}
		return method;
	}

	//创造selectList方法
	public CtMethod makeSelectListMethod(CtClass type, Class<?> dmoClass) {

		CtMethod method = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("public  String selectList(%s insertDmo){",dmoClass.getName()));
			sb.append("org.apache.ibatis.jdbc.SQL sql = new org.apache.ibatis.jdbc.SQL();");
			for(String prop:getColumnMap().keyProperty.keySet()) {

				sb.append(String.format("sql.SELECT(\"%s as %s\");",new Object[]{getColumnMap().keyProperty.get(prop),prop}));
			}
			for(String prop:getColumnMap().columnMap.keySet()) {

				sb.append(String.format("sql.SELECT(\"%s as %s\");",new Object[]{getColumnMap().columnMap.get(prop),prop}));
			}
			sb.append(String.format("sql.FROM(\"%s\");",getColumnMap().getTableName()));
			for(String prop:getColumnMap().keyProperty.keySet()) {

				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",new Object[]{getter,getColumnMap().keyProperty.get(prop),prop}));
			}
			for(String prop:getColumnMap().columnMap.keySet()) {
				
				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",new Object[]{getter,getColumnMap().columnMap.get(prop),prop}));
				}

			sb.append("return sql.toString();}");
			System.out.println(sb.toString());
			method = CtNewMethod.make(sb.toString(), type);
		} catch (CannotCompileException e) {
			throw new AppException(e.getMessage());
		}
		return method;
	}

	//创造selectCount方法
	public CtMethod makeSelectCount(CtClass type, Class<?> dmoClass) {

		CtMethod method = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("public  String selectCount(%s insertDmo){",dmoClass.getName()));
			sb.append("org.apache.ibatis.jdbc.SQL sql = new org.apache.ibatis.jdbc.SQL();");
			sb.append("sql.SELECT(\"count(*)\");");
			sb.append(String.format("sql.FROM(\"%s\");",getColumnMap().getTableName()));
			
			for(String prop:getColumnMap().keyProperty.keySet()) {

				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",new Object[]{getter,getColumnMap().keyProperty.get(prop),prop}));
			}
			for(String prop:getColumnMap().columnMap.keySet()) {
				
				String getter = prop.substring(0, 1).toUpperCase()+prop.substring(1);
				sb.append(String.format("if(insertDmo.get%s() != null) {sql.WHERE(\"%s = #{%s}\");}",new Object[]{getter,getColumnMap().columnMap.get(prop),prop}));
			}
			sb.append("return sql.toString();}");
			System.out.println(sb.toString());
			method = CtNewMethod.make(sb.toString(), type);
		} catch (CannotCompileException e) {
			throw new AppException(e.getMessage());
		}
		return method;
	}

	class ColumnMap {

		private Map<String,String> keyProperty = new HashMap<String, String>();
		//story for key - property name mapping
		private Map<String,String> columnMap = new HashMap<String, String>();
		//主键生成类型
		private GenerationType keyGenratoyType;
		//对于oracle来说，可以使用sequence，需要制定sequence的名字    ,暂时不考虑
		private String generator;
		//表名称
		private String tableName;
		//目标类
		private Class<?> targetClass;

		public String getGenerator() {
			return generator;
		}

		public void setGenerator(String generator) {
			this.generator = generator;
		}

		public Map<String,String> getKeyProperty() {
			return keyProperty;
		}

		public void setKeyProperty(Map<String,String> keyProperty) {
			this.keyProperty = keyProperty;
		}

		public Map<String, String> getColumnMap() {
			return columnMap;
		}

		public void setColumnMap(Map<String, String> columnMap) {
			this.columnMap = columnMap;
		}

		public GenerationType getKeyGenratoyType() {
			return keyGenratoyType;
		}

		public void setKeyGenratoyType(GenerationType keyGenratoyType) {
			this.keyGenratoyType = keyGenratoyType;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public Class<?> getTargetClass() {
			return targetClass;
		}

		public void setTargetClass(Class<?> targetClass) {
			this.targetClass = targetClass;
		}

		/**
		 * 构造
		 * @param targetClass
		 */
		public ColumnMap(Class<?> targetClass) {
			super();
			this.targetClass = targetClass;
		}

		/**
		 * 编译 ,, 根据注解信息，生成映射表
		 */
		public void compiler() {

			if(!this.getTargetClass().isAnnotationPresent(Entity.class)) {

				throw new AppException("Error : target class not support Entity | NOT SUPPORT！");
			}

			//extract table name
			Entity entity = (Entity)this.getTargetClass().getAnnotation(Entity.class);
			this.setTableName(entity.name());

			//extract all property
			Field[] fields =  this.getTargetClass().getDeclaredFields();
			for(Field field : fields) {
				if(field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {

					this.getColumnMap().put(field.getName(), ((Column)field.getAnnotation(Column.class)).name());
				}else if(field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(Id.class)){
					//id ,key column
					keyProperty.put(field.getName(), ((Column)field.getAnnotation(Column.class)).name());
				}
			}
			//extract key property
			//keyGenratoyType = ((GeneratedValue)this.getTargetClass().getAnnotation(GeneratedValue.class)).strategy();

		}

	}

	//测试类
	public static void main(String[] args) {
		Result result = new Result();
		result.success("hello eotld");
		System.out.println(builderString(result));
		//System.out.println(String.format("for(String prop:%s)",""));
	}

	public static String builderString(final Result result) {
		SQL sql = new SQL();
		return sql.toString();
	}



}
