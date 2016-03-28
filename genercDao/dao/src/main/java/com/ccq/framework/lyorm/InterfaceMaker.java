package com.ccq.framework.lyorm;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public class InterfaceMaker {
	//涓嶆兂鐢ㄥ叏灞�鍙橀噺锛岀嚎绋嬩笉瀹夊叏锛屼細鍑洪棶棰�
	//	private CtClass ctClass;
	//	private ConstPool constPool;
	//	private String dmoClassName;
	//	private String dmoSimpleName;

	/**
	 * 创建一个mapper接口，通过注解的方式动态执行sql
	 * 
	 * @param dmoClass
	 * @return
	 * @throws CannotCompileException
	 * @throws IOException 
	 * @throws NotFoundException 
	 */
	public static Class make(Class<?> dmoClass) throws CannotCompileException, IOException, NotFoundException {
		
		String dmoSimpleName = dmoClass.getSimpleName();
		String qualifyName = dmoClass.getName();
		
		ClassPool pool = ClassPool.getDefault();
		//CtClass target = pool.makeClass(dmoSimpleName);
		CtClass target = pool.makeInterface(dmoSimpleName);
		
		AnnotationProvider provider = new AnnotationProvider(dmoClass);
		provider.compiler();
		
		target.addMethod(
				addMethod(target,"public void insert(%s dmo);","org.apache.ibatis.annotations.InsertProvider","insert",dmoClass));
		target.addMethod(
				addMethod(target,"public void update(%s dmo);","org.apache.ibatis.annotations.UpdateProvider","update",dmoClass));
		target.addMethod(
				addMethod(target,"public %s selectOne(%s dmo);","org.apache.ibatis.annotations.SelectProvider","selectOne",dmoClass));
		target.addMethod(
				addMethod(target,"public java.util.List selectList(%s dmo);","org.apache.ibatis.annotations.SelectProvider","selectList",dmoClass));
		target.addMethod(
				addMethod(target,"public Long selectCount(%s dmo);","org.apache.ibatis.annotations.SelectProvider","selectCount",dmoClass));
		
		if(true) {
			target.writeFile("e:/class");
		}
		
		return target.toClass();
	}
	
	/**
	 *  鏂规硶鍚嶇О,娉ㄨВ绫诲瀷,sql鏂规硶
	 * @throws CannotCompileException 
	 * 
	 */
	public static CtMethod addMethod(CtClass ctType,String methodName,String annotation,String sqlMethod,Class<?> dmoClass) throws CannotCompileException {
		
		ClassFile cf = ctType.getClassFile();
		ConstPool constpool = cf.getConstPool();
		AnnotationsAttribute aa = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		//娉ㄨВ鍚嶇О
		Annotation ant = new Annotation(String.format(annotation, dmoClass.getName()), constpool);

		//澧炲姭type娉ㄨВ
		ClassMemberValue typeMember = new ClassMemberValue(String.format("%sProvider", dmoClass.getSimpleName()),constpool);
		ant.addMemberValue("type", typeMember);
		
		//澧炲姞method娉ㄨВ
		StringMemberValue methodMember = new StringMemberValue(sqlMethod, constpool);
		ant.addMemberValue("method", methodMember);
		
		aa.addAnnotation(ant);
		System.out.println(String.format(methodName, new Object[]{dmoClass.getName(),
					dmoClass.getName()}));
		CtMethod method = CtMethod.make(String.format(methodName, new Object[]{dmoClass.getName(),
					dmoClass.getName()}), ctType);
		if(methodName.contains("List")) {
			
			String genericSignature = String.format("(L%s;)Ljava/util/List<L%s;>;", new Object[] { dmoClass.getName(), dmoClass.getName() }).replace('.', '/');
			method.setGenericSignature(genericSignature);
		}
		//澧炲姞娉ㄨВ灞炴��
		method.getMethodInfo().addAttribute(aa);
		return method;
	}
	
	
	
	public static void main(String[] args) throws CannotCompileException, IOException {
		System.out.println(String.format("%s world", new String[]{"hello"}));
	}
}
