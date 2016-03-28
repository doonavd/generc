package test;

import java.io.IOException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liuyao.test.Daotest;
import com.liuyao.test.ForeumDmo;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public class TestUpdate {
	public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-db.xml");
		
		Daotest dao = (Daotest) ctx.getBean("dao");
		
		ForeumDmo dmo = new ForeumDmo();
		dmo.setCity("Beijing");
		ForeumDmo dbDmo = dao.selectOne(dmo);
		if(dbDmo == null) {
			System.out.println("测试失败或该数据不存在");
		}else {
			dbDmo.setCity("Nanjing");
			int i = dao.update(dbDmo);
			if(i<1) {
				System.out.println("更新失败");
			}else {
				System.out.println("更新成功");
			}
		}
}
}
