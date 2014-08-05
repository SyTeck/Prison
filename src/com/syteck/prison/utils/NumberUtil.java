package com.syteck.prison.utils;

public class NumberUtil {

	public static boolean canParse(String str) { try { Integer.parseInt(str); return true; } catch(NumberFormatException e) { return false; } }
	public static boolean canAfford(double current, double needed) { return current > needed; }
	public static double missing(double current, double needed) { return current - needed; }

}
