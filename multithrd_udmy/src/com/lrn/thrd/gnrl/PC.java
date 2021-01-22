package com.lrn.thrd.gnrl;

public class PC {
	public static void main(String[] args) {
		Q q = new Q();
		new Producer(q);
		new Consumer(q);

		System.out.println("The war is on");
	}
}

class Q {
	int n;
	boolean valueSet = false;

	synchronized void put(int n) {
		while (valueSet) {
			try {
				wait();
			} catch (Exception e) {

			}
		}

		this.n = n;
		valueSet = true;
		System.out.println("Put: " + n);
		notify();
	}

	synchronized int get() {

		while (!valueSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
			}
		}
		System.out.println("Got: " + n);
		valueSet = false;
		notify();
		return n;
	}
}

class Producer implements Runnable {
	Q q;

	public Producer(Q q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}

	@Override
	public void run() {
		int i = 0;
		// TODO Auto-generated method stub
		while (true) {
			q.put(i++);
		}
	}
}

class Consumer implements Runnable {
	Q q;

	public Consumer(Q q) {
		this.q = q;
		new Thread(this, "Consumer").start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			q.get();
		}
	}
}
