package test;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liuyao.test.Daotest;
import com.liuyao.test.ForeumDmo;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public class TestSelect {
	
	
	public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-db.xml");
			
			Daotest dao = (Daotest) ctx.getBean("dao");
			
			ForeumDmo dmo = new ForeumDmo();
			dmo.setContent("I love Nanjing");
			ForeumDmo d  = dao.selectOne(dmo);
			System.out.println(d);
	}
}
