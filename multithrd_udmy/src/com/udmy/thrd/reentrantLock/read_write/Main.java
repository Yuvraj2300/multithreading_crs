package com.udmy.thrd.reentrantLock.read_write;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
	public final static int HIGHEST_PRICE = 1000;

	public static void main(String[] args) {
		InventoryDatabase db = new InventoryDatabase();

		Random random = new Random();
		for (int i = 0; i < 100000; i++) {
			db.addItem(random.nextInt(HIGHEST_PRICE));
		}

		Thread writer = new Thread(() -> {
			while (true) {
				db.addItem(random.nextInt(HIGHEST_PRICE));
				db.removeItem(random.nextInt(HIGHEST_PRICE));

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		writer.setDaemon(true);
		writer.start();

		int numberOfReader = 7;
		List<Thread> readers = new ArrayList<>();

		for (int i = 0; i < numberOfReader; i++) {
			Thread reader = new Thread(() -> {
				for (int j = 0; j < 100000; j++) {
					int upperBound = random.nextInt(HIGHEST_PRICE);
					int lowerBound = upperBound > 0 ? random.nextInt(HIGHEST_PRICE) : 0;
					db.getNumberOfItemsInPriceRange(lowerBound, upperBound);
				}
			});
			reader.setDaemon(true);
			readers.add(reader);
		}

		long startReadingTime = System.currentTimeMillis();
		for (Thread reader : readers) {
			reader.start();
		}

		for (Thread reader : readers) {
			try {
				reader.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		long endReadOp = System.currentTimeMillis();
		System.out.println(String.format("Reading took %d ms", endReadOp - startReadingTime));

	}

	public static class InventoryDatabase {
		private TreeMap<Integer, Integer> pricetToCountMap = new TreeMap<>();

		ReentrantLock lock = new ReentrantLock();
	

		public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
			lock.lock();
			try {
				Integer fromKey = pricetToCountMap.ceilingKey(lowerBound);
				Integer toKey = pricetToCountMap.floorKey(upperBound);

				if (fromKey == null || toKey == null) {
					return 0;
				}

				NavigableMap<Integer, Integer> rageOfPrices = pricetToCountMap.subMap(fromKey, true, toKey, true);

				int sum = 0;
				for (int numerOfItemsForPrice : rageOfPrices.values()) {
					sum += numerOfItemsForPrice;
				}

				return sum;
			} finally {
				lock.unlock();
			}
		}

		public void addItem(int price) {
			lock.lock();
			try {
				Integer numberOfItemsForPrice = pricetToCountMap.get(price);
				if (numberOfItemsForPrice == null) {
					pricetToCountMap.put(price, 1);
				} else {
					pricetToCountMap.put(price, numberOfItemsForPrice + 1);
				}
			} finally {
				lock.unlock();
			}

		}

		public void removeItem(int price) {

			lock.lock();
			try {
				Integer numberOfItemsForPrice = pricetToCountMap.get(price);
				if (numberOfItemsForPrice == null) {
					pricetToCountMap.remove(price, 1);
				} else {
					pricetToCountMap.put(price, numberOfItemsForPrice + 1);
				}
			} finally {
				lock.unlock();
			}

		}

	}
}
