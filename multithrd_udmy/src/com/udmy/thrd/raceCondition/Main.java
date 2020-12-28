package com.udmy.thrd.raceCondition;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		InventoryCounter invCounter = new InventoryCounter();
		IncrementingThread incThread = new IncrementingThread(invCounter);
		DecrementingThread decThread = new DecrementingThread(invCounter);

		incThread.start();
		decThread.start();

		incThread.join();
		decThread.join();

		System.out.println("We have following number of items in the inventory : " + invCounter.getItems());

	}

	public static class DecrementingThread extends Thread {
		private InventoryCounter inventoryCounter;

		public DecrementingThread(InventoryCounter inventoryCounter) {
			super();
			this.inventoryCounter = inventoryCounter;
		}

		@Override
		public void run() {

			for (int i = 0; i < 10000; i++) {
				inventoryCounter.decrement();
			}

		}

	}

	public static class IncrementingThread extends Thread {
		private InventoryCounter inventoryCounter;

		public IncrementingThread(InventoryCounter inventoryCounter) {
			super();
			this.inventoryCounter = inventoryCounter;
		}

		@Override
		public void run() {

			for (int i = 0; i < 10000; i++) {
				inventoryCounter.increment();
			}

		}

	}

	private static class InventoryCounter {
		private int items = 0;

		Object lock = new Object();

		public void increment() {

			synchronized (lock) {
				items++;
			}

		}

		public void decrement() {

			synchronized (lock) {
				items--;
			}

		}

		public int getItems() {

			synchronized (lock) {
				return items;
			}

		}

	}
}
