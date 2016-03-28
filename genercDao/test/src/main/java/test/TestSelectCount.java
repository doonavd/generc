package test;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liuyao.test.Daotest;
import com.liuyao.test.ForeumDmo;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public class TestSelectCount {
	public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-db.xml");

		Daotest dao = (Daotest) ctx.getBean("dao");

		ForeumDmo dmo = new ForeumDmo();
		dmo.setUsername("liuyao");
		dmo.setCity("NNnjing");
		Long count = dao.selectCount(dmo);
		System.out.println(count);
		
	}
}
