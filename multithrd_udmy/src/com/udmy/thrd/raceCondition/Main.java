package com.udmy.thrd.raceCondition;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Metrics metrics = new Metrics();

		BusinessLogic bl1 = new BusinessLogic(metrics);
		BusinessLogic bl2 = new BusinessLogic(metrics);
		
		MetricsPrinter	metricsPrinter	=	new	MetricsPrinter(metrics);
	
		bl1.start();
		bl2.start();
		metricsPrinter.start();
	}

	public static class MetricsPrinter extends Thread {
		private Metrics metrics;

		public MetricsPrinter(Metrics metrics) {
			super();
			this.metrics = metrics;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				double currentAverage = metrics.getAverage();

				System.out.println("Average is :- " + currentAverage);
			}
		}

	}

	public static class BusinessLogic extends Thread {
		private Metrics metrics;
		private Random random = new Random();

		public BusinessLogic(Metrics metrics) {
			this.metrics = metrics;
		}

		@Override
		public void run() {

			while (true) {
				long start = System.currentTimeMillis();

				try {
					Thread.sleep(random.nextInt(10));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				long end = System.currentTimeMillis();

				metrics.addSample(end - start);
			}
		}
	}

	public static class Metrics {
		private long count = 0;
		private volatile double average = 0.0;

		public synchronized void addSample(long sample) {
			double currentSum = average * count;
			count++;
			average = (currentSum + sample) / count;
		}

		public double getAverage() {
			return average;
		}

	}
}