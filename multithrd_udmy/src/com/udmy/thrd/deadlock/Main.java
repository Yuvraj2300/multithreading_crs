package com.udmy.thrd.deadlock;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Intersection i = new Intersection();
		Thread trainA = new Thread(new TrainA(i));
		Thread trainB = new Thread(new TrainB(i));

		trainA.start();
		trainB.start();
	}

	public static class TrainB implements Runnable {
		Intersection intersection;
		Random random = new Random();

		public TrainB(Intersection intersection) {
			super();
			this.intersection = intersection;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (true) {
				try {
					Thread.sleep(random.nextInt(5));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				intersection.takeRoadB();
			}

		}

	}

	public static class TrainA implements Runnable {
		Intersection intersection;
		Random random = new Random();

		public TrainA(Intersection intersection) {
			super();
			this.intersection = intersection;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true) {
				try {
					Thread.sleep(random.nextInt(5));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intersection.takeRoadA();
			}
			
		}

	}

	public static class Intersection {
		private Object roadA = new Object();
		private Object roadB = new Object();

		public void takeRoadA() {
			synchronized (roadA) {
				System.out.println("Road A is taken by : " + Thread.currentThread().getName());

				synchronized (roadB) {
					System.out.println("Train is passing on road A");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		public void takeRoadB() {
			synchronized (roadA) {
				System.out.println("Road B is taken by : " + Thread.currentThread().getName());

				synchronized (roadB) {
					System.out.println("Train is passing on road B");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}

}
