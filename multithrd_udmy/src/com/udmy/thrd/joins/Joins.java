package com.udmy.thrd.joins;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Joins {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {

		List<Long> inputs = Arrays.asList(0L, 435L, 43532L, 984L, 2000000000000001L);

		List<FactorialThread> threads = new ArrayList<Joins.FactorialThread>();

		for (long inp : inputs) {
			threads.add(new FactorialThread(inp));
		}

		for (Thread th : threads) {
			th.setDaemon(true);
			th.start();
		}

		for (Thread th : threads) {
			try {
			th.join(2000);}catch(InterruptedException e) {
				System.out.println("Interrupt was received for the thread");
			}
		}

		for (int i = 0; i < inputs.size(); i++) {
			FactorialThread fact = threads.get(i);
			if (fact.isFinished) {
				System.out.println("Factorial for " + inputs.get(i) + " is " + fact.getResult());
			} else {

				System.out.println("The result is still being calculated for : " + inputs.get(i));
			}

		}
	}

	public static class FactorialThread extends Thread {
		private long inp;
		private BigInteger result = BigInteger.ZERO;
		private boolean isFinished = false;

		public FactorialThread(long inp) {
			super();
			this.inp = inp;
		}

		@Override
		public void run() {
			this.result = fact(inp);
			this.isFinished = true;
		}

		public BigInteger fact(long n) {
			BigInteger tempResult = BigInteger.ONE;

			for (long i = n; i > 0; i--) {
				tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
			}

			return tempResult;
		}

		public BigInteger getResult() {
			return result;
		}

		public boolean isFinished() {
			return isFinished;
		}
	}
}
