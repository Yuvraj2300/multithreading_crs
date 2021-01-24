package com.lrn.thrd.gnrl.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExService {
	public static void main(String[] args) {
		CountDownLatch cdl1 = new CountDownLatch(5);
		CountDownLatch cdl2 = new CountDownLatch(5);
		CountDownLatch cdl3 = new CountDownLatch(5);
		CountDownLatch cdl4 = new CountDownLatch(5);
		ExecutorService es = Executors.newFixedThreadPool(2);

		System.out.println("Starting");

		es.execute(new MyThread(cdl1, "A"));
		es.execute(new MyThread(cdl2, "B"));
		es.execute(new MyThread(cdl3, "C"));
		es.execute(new MyThread(cdl4, "D"));

		try {
			cdl1.await();
			cdl2.await();
			cdl3.await();
			cdl4.await();
		} catch (Exception e) {

		}

		es.shutdown();
		System.out.println("Shutdown");

	}
}

class MyThread implements Runnable {
	CountDownLatch cdl;
	String name;

	public MyThread(CountDownLatch cdl, String name) {
		this.cdl = cdl;
		this.name = name;
	}

	@Override
	public void run() {

		for (int i = 0; i < 5; i++) {
			System.out.println(name + ":  is at value :- " + i);
			cdl.countDown();
		}
	}

}
