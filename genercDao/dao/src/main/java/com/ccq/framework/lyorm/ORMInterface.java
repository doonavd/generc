/**
 *  @author liuyao
 * 
 *  �Զ�����dmo�Ľӿڣ��������ݿ���� 
 */

package com.ccq.framework.lyorm;

class a implements Runnable {
	int i = 0;
	public void run() {
		// TODO Auto-generated method stub
		for( ;i < 100;i++) {
			System.out.println(i);
		}
	}
}

public class ORMInterface {

	public static void main(String[] args) {
		ORMInterface orm = new ORMInterface();
		a l = new a();
		Thread t1 = new Thread(l);
		Thread t2 = new Thread(l);
		t1.start();
		t2.start();
	}


}
