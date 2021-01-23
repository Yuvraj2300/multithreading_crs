package com.lrn.thrd.gnrl.concurrent;

import java.util.concurrent.Semaphore;

public class SemDemo {
	public static void main(String[] args) {
		Semaphore sem = new Semaphore(1);
		new IncThread(sem, "IncThread");
		new DecThread(sem, "DecThread");
	}
}

class Shared {
	static int count = 0;
}

class IncThread implements Runnable {

	String name;
	Semaphore sem;

	public IncThread(Semaphore sem, String name) {
		this.sem = sem;
		this.name = name;
		new Thread(this, name).start();
	}

	public void run() {
		System.out.println("Starting " + name);
		try {
			System.out.println(name + " is waiting for a permit.");
			sem.acquire();
			System.out.println(name + " gets a permit.");

			// Now, access shared resource.
			for (int i = 0; i < 5; i++) {
				Shared.count++;
				System.out.println(name + ": " + Shared.count);
				// Now, allow a context switch -- if possible.
				Thread.sleep(10);
			}

		} catch (Exception e) {
		}
		// Release the permit.
		System.out.println(name + " releases the permit.");
		sem.release();
	}
}

class DecThread implements Runnable {
	String name;
	Semaphore sem;

	DecThread(Semaphore s, String n) {
		sem = s;
		name = n;
		new Thread(this).start();
	}

	public void run() {
		System.out.println("Starting " + name);
		try {
// First, get a permit.
			System.out.println(name + " is waiting for a permit.");
			sem.acquire();
			System.out.println(name + " gets a permit.");
// Now, access shared resource.
			for (int i = 0; i < 5; i++) {
				Shared.count--;
				System.out.println(name + ": " + Shared.count);
// Now, allow a context switch -- if possible.
				Thread.sleep(10);
			}
		} catch (InterruptedException exc) {
			System.out.println(exc);
		}
// Release the permit.
		System.out.println(name + " releases the permit.");
		sem.release();
	}
}
