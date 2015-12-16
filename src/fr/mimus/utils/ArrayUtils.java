package fr.mimus.utils;

public class ArrayUtils {
	
	public static float[] fusion(float[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		float[] na = new float[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	
	public static double[] fusion(double[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		double[] na = new double[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	
	public static boolean[] fusion(boolean[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		boolean[] na = new boolean[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	
	public static byte[] fusion(byte[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		byte[] na = new byte[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	
	public static short[] fusion(short[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		short[] na = new short[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	

	public static int[] fusion(int[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		int[] na = new int[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	
	public static long[] fusion(long[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		long[] na = new long[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
	
	public static Object[] fusion(Object[]... array) {
		int size = 0;
		for(int i = 0; i<array.length; i++) {
			size += array[i].length;
		}
		Object[] na = new Object[size];
		int k = 0;
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				na[k] = array[i][j];
				k++;
			}
		}
		return na;
	}
}
