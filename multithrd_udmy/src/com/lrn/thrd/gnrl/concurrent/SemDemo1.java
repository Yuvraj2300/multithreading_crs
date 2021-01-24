package com.lrn.thrd.gnrl.concurrent;

import java.util.concurrent.Semaphore;

public class SemDemo1 {
	public static void main(String[] args) {
		Q_1 q = new Q_1();
		new HelloThread(q);
		new HiThread(q);
	}
}

class Q_1 {
	Semaphore semHi = new Semaphore(0);
	Semaphore semHello = new Semaphore(1);

	void printHi(String msg) throws InterruptedException {
		semHi.acquire();
		System.out.println(msg);
		semHello.release();
	}

	void printHello(String msg) throws InterruptedException {
		semHello.acquire();
		System.out.println(msg);
		semHi.release();
	}
}

class HelloThread implements Runnable {
	Q_1 q;

	public HelloThread(Q_1 q) {
		this.q = q;
		new Thread(this, "Hello_Thread").start();
	}

	public void run() {
		while (true) {
			try {
				q.printHello("Hello");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class HiThread implements Runnable {
	Q_1 q;

	public HiThread(Q_1 q) {
		this.q = q;
		new Thread(this, "Hi_Thread").start();
	}

	public void run() {
		while (true) {
			try {
				q.printHi("Hi");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
