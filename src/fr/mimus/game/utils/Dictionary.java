package fr.mimus.game.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Dictionary {
	private static HashMap<String, Language> dictionary = new HashMap<String, Language>();
	
	public static Language getLanguage(String name) {
		Language lang = dictionary.get(name);
		if(lang == null) {
			dictionary.put(name, new Language());
			lang = dictionary.get(name);
		}
		return lang;
	}
	
	public static String[] getLangList() {
		String[] langs = new String[dictionary.size()];
		dictionary.keySet().toArray(langs);
		return langs;
	}
	
	public static String getWord(String key) {
		Language l = getLanguage(LangOption.lang);
		if(l != null) {
			return l.getWord(key);
		}
		return key;
	}
	
	public static String getWord(String lang, String key) {
		Language l = getLanguage(lang);
		if(l != null) {
			return l.getWord(key);
		}
		return key;
	}
	
	public static void load(String name, String path) throws IOException {
		Language lang = getLanguage(name);
		if(lang != null) {
			if((new File(path+name+".lang")).exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(path+name+".lang"));
				String line = "";
				try {
					while((line=reader.readLine())!=null) {
						String values[] = line.split("=");
						lang.putWord(values[0], values[1]);
					}
				} finally {
					reader.close();
				}
			}
		}
		
	}
	
}
