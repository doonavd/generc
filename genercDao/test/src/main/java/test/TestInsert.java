package test;

import java.io.IOException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liuyao.test.Daotest;
import com.liuyao.test.ForeumDmo;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public class TestInsert {
	
	
	public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-db.xml");
			
			Daotest dao = (Daotest) ctx.getBean("dao");
			
			ForeumDmo dmo = new ForeumDmo();
			dmo.setCity("Beijing");
			dmo.setContent("I love Beijing");
			dmo.setCreateTime(new Date());
			dmo.setTopicTitle("I love Beijing");
			dmo.setUsername("liuyao");
			int count = dao.insert(dmo);
			if(count == 1) {
				System.out.println("插入成功");
			}else {
				System.out.println("插入失败");
			}
	}
}
