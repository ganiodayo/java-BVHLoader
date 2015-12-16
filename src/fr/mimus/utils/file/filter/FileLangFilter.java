package fr.mimus.utils.file.filter;

import java.io.File;
import java.io.FilenameFilter;

public class FileLangFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		String lowercaseName = name.toLowerCase();
		if (lowercaseName.endsWith(".lang")) {
			return true;
		}
		return false;
	}

}
