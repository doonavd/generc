package test;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liuyao.test.Daotest;
import com.liuyao.test.ForeumDmo;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public class TestSelectList {
	public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-db.xml");
		
		Daotest dao = (Daotest) ctx.getBean("dao");
		
		ForeumDmo dmo = new ForeumDmo();
		//dmo.setUsername("liuyao");
		List<ForeumDmo> list = dao.selectList(dmo);
		for(ForeumDmo dmoA : list) {
			System.out.println(dmoA);
		}
}
}
