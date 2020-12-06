package com.udmy.thrd.performace;

public class Main {
	public static void main(String[] args) {

	}

	public static boolean isShadeOfGray(int r, int g, int b) {
		return Math.abs(r - g) < 30 && Math.abs(r - b) < 30 && Math.abs(g - b) < 30;
	}

	public static int createRGBFromColors(int r, int g, int b) {
		int rgb = 0;

		rgb |= b;
		rgb |= g << 8;
		rgb |= r << 16;

		rgb |= 0xFF000000;

		return rgb;

	}

	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}

	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8;
	}

	public static int getBlue(int rgb) {
		return rgb & 0x000000FF;
	}
}
