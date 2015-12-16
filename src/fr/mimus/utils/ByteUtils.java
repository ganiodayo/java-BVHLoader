package fr.mimus.utils;

public class ByteUtils {
	public static byte[] writeInt(int w) {
		byte[] bits = new byte[4];
		bits[0] = (byte) (w >> 24);
		bits[1] = (byte) (w >> 16);
		bits[2] = (byte) (w >> 8);
		bits[3] = (byte) w;
		return bits;
	}
}
