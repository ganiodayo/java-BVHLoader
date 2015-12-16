package fr.mimus.utils.file.filter;

import java.io.File;
import java.io.FilenameFilter;

public class FileImageFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		String lowercaseName = name.toLowerCase();
		if (lowercaseName.endsWith(".png")
			 || lowercaseName.endsWith(".jpg")
			 || lowercaseName.endsWith(".jpeg")
			 || lowercaseName.endsWith(".gif")
			 || lowercaseName.endsWith(".bmp")
			 || lowercaseName.endsWith(".jpe")
			 || lowercaseName.endsWith(".jfif")) {
			return true;
		}
		return false;
	}

}
