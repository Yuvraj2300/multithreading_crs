package com.lrn.thrd.gnrl.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFuture {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newFixedThreadPool(3);
		Future<Integer> f1;
		Future<Double> f2;
		Future<Integer> f3;

		f1 = es.submit(new Sum(10));
		f2 = es.submit(new Hypot(10, 20));
		f3 = es.submit(new Factorial(10));

		System.out.println(f1.get());
		System.out.println(f2.get());
		System.out.println(f3.get());
		
		es.shutdown();
	}
}

class Sum implements Callable<Integer> {
	int num;

	public Sum(int num) {
		this.num = num;
	}

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i < num; i++) {
			sum = +i;
		}
		return sum;
	}
}

class Hypot implements Callable<Double> {
	double side1, side2;

	Hypot(double s1, double s2) {
		side1 = s1;
		side2 = s2;
	}

	public Double call() {
		return Math.sqrt((side1 * side1) + (side2 * side2));
	}
}

class Factorial implements Callable<Integer> {

	int num;

	public Factorial(int num) {
		this.num = num;
	}

	@Override
	public Integer call() throws Exception {
		int fact = 1;
		for (int i = 2; i <= num; i++) {
			fact = fact * i;
		}
		return fact;
	}

}
