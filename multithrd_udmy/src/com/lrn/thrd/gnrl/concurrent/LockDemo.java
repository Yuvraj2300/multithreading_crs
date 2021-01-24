package com.lrn.thrd.gnrl.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		new LockThread("A", lock);
		new LockThread("B", lock);
	}
}

class Shared_1 {
	static int count;
}

class LockThread implements Runnable {
	private String name;
	private Lock lock;

	public LockThread(String name, Lock lock) {
		this.name = name;
		this.lock = lock;
		new Thread(this, name).start();
	}

	public void run() {
		System.out.println("Starting : " + name);
		try {
			System.out.println(name + " is waiting to lock count.");

			lock.lock();
//			lock.tryLock();

			System.out.println(name + " is locking count.");

			Shared_1.count++;

			System.out.println(name + ": " + Shared_1.count);

			System.out.println(name + " is sleeping.");
			Thread.sleep(1000);
		} catch (Exception e) {

		} finally {

			System.out.println(name + " is unlocking count.");
			lock.unlock();
		}
	}

}
