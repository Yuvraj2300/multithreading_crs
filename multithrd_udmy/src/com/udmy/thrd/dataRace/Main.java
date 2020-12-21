package com.udmy.thrd.dataRace;

public class Main {
	public static void main(String[] args) {
		SharedClass sharedClass = new SharedClass();
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				sharedClass.increment();
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				sharedClass.checkForDataRaces();
			}
		});

		thread1.start();
		thread2.start();
	}

	public static class SharedClass {
		private volatile int x;
		private volatile int y;

		public void increment() {
			x++;
			y++;
		}

		public void checkForDataRaces() {
			if (y > x) {
				System.out.println("y > x ---> Data Race is detected");
			}
		}

	}
}
