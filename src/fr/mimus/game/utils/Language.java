package fr.mimus.game.utils;

import java.util.HashMap;

public class Language {
	private HashMap<String, String> words = new HashMap<String, String>();
	
	public String getWord(String key) {
		String word = words.get(key);
		if(word == null) {
			return key;
		}
		return word;
	}
	
	public void putWord(String key, String word) {
		words.put(key, word);
	}
}
