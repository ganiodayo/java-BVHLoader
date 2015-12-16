package fr.mimus.utils;

public class MathUtils {
	public static double getRotate(double dx, double dy, double ax, double ay) {
		return Math.toDegrees(Math.atan2(ay-dy, ay-dx));
	}
	
}
