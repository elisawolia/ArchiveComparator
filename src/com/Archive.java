package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

/**
 * Object of the archive. Keeps the list of all the files {@link Files}.
 *
 * @author elizavetavolianica
 */
public class Archive extends JarFile{
	private List<Files>	files;

	public Archive(String name) throws IOException {
		super(name);
		files = new ArrayList<Files>();

		Enumeration<JarEntry> entries = this.entries();
		while (entries.hasMoreElements()) {
			JarEntry file = entries.nextElement();
			files.add(new Files(file.getName(), file.getSize()));
		}
	}

	public List<Files> getFiles() {
		return files;
	}
}
