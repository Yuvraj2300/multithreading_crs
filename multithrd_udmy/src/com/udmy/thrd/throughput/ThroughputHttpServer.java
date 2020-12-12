package com.udmy.thrd.throughput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpsServer;

public class ThroughputHttpServer {
	private static final String INPUT_FILE = "resources/throughput/war_and_peace.txt";

	public static void main(String[] args) throws IOException {
		String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
		startServer(text);
	}

	public static void startServer(String text) {
		HttpsServer	
	}

}
