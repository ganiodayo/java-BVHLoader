package fr.mimus.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileLoader {
	public static void saveObject(String file, Object obj) {
		try {
			FileOutputStream fichier = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public static Object loadObject(String file) {
		try {
			FileInputStream fichier = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			Object obj = ois.readObject();
			fichier.close();
			ois.close();
			return obj;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public static String loadFileToString(String input) {
		String r = "";

		try {
			FileInputStream fis = new FileInputStream(input);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
					r += buffer + "\n";
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	
}
