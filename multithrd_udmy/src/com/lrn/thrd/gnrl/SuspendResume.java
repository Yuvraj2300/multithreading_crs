package com.lrn.thrd.gnrl;

public class SuspendResume {
	public static void main(String[] args) {
		NewThread_1 ob1 = new NewThread_1("1");
		NewThread_1 ob2 = new NewThread_1("2");

		try {
			Thread.sleep(1000);
			ob1.mysuspend();
			System.out.println("Suspending thread One");
			Thread.sleep(1000);
			ob1.myresume();
			System.out.println("Resuming thread One");
			ob2.mysuspend();
			System.out.println("Suspending thread Two");
			Thread.sleep(1000);
			ob2.myresume();
			System.out.println("Resuming thread Two");
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}

		try {
			System.out.println("Waiting for threads to finish.");
			ob1.t.join();
			ob2.t.join();
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}

		System.out.println("Main thread exiting.");
	}
}

class NewThread_1 implements Runnable {

	String name;
	Thread t;
	boolean suspendFlag;

	public NewThread_1(String name) {
		this.name = name;
		t = new Thread(this, name);
		System.out.println("New Thread Created with name: " + name);
		suspendFlag = false;
		t.start();
	}

	public void run() {
		try {
			for (int i = 15; i > 0; i--) {
				System.out.println(name + ": " + i);
				Thread.sleep(200);
				synchronized (this) {
					while (suspendFlag) {
						wait();
					}
				}
			}
		} catch (Exception e) {

		}
		System.out.println(name + " exting.");
	}

	synchronized void mysuspend() {
		suspendFlag = true;
	}

	synchronized void myresume() {
		suspendFlag = false;
		notify();
	}
}