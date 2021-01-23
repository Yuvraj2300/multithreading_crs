package com.lrn.thrd.gnrl.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExService {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch cdl = new CountDownLatch(5);
		CountDownLatch cdl2 = new CountDownLatch(5);
		CountDownLatch cdl3 = new CountDownLatch(5);
		CountDownLatch cdl4 = new CountDownLatch(5);
		ExecutorService es = Executors.newFixedThreadPool(2);

		System.out.println("Starting");

		es.execute(new MyThread(cdl, "A"));
		es.execute(new MyThread(cdl2, "B"));
		es.execute(new MyThread(cdl3, "C"));
		es.execute(new MyThread(cdl4, "D"));

		cdl.await();
		cdl2.await();
		cdl3.await();
		cdl4.await();

		es.shutdown();
		System.out.println("Done");

	}
}

class MyThread implements Runnable {
	String name;
	CountDownLatch cdl;

	public MyThread(CountDownLatch cdl, String name) {
		this.name = name;
		this.cdl = cdl;
		new Thread(this, name);
	}

	public void run() {
		for (int i = 5; i > 0; i--) {
			System.out.println(name + ": " + i);
			cdl.countDown();
		}
	}

}