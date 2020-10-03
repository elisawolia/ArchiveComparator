package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.Files.compareFile;
import static com.Files.Status;


/**
 * Class which executes comparing of the archives.
 *
 * @author elizavetavolianica
 */
public class ArchiveComparator {
	private Archive 				oldArch;
	private Archive 				newArch;
	private HashMap<Status, String>	map;

	public ArchiveComparator(Archive one, Archive two) {
		this.oldArch = one;
		this.newArch = two;
	}

	/**
	 * Get the instances of files of each archive, then compare them.
	 * Deletes from lists all of the files which have been marked already.
	 * HashMap is a map with status as the key and a whole concreted string
	 * of the names of the files with this status as a value.
	 */
	public void compareArch() {
		List<Files> oldFiles = oldArch.getFiles();
		List<Files> newFiles = newArch.getFiles();
		String line;
		map = new HashMap<>();

		for (int i = 0; i < oldFiles.size(); i++) {
			for (int j = 0; j < newFiles.size(); j++) {
				compareFile(oldFiles.get(i), newFiles.get(j), map);
				if (newFiles.get(j).getStatus() != Status.DEFAULT)
					newFiles.remove(j);
			}
		}
		for (int i = 0; i < oldFiles.size(); i++) {
			if (oldFiles.get(i).getStatus() == Files.Status.DEFAULT) {
				oldFiles.get(i).setStatus(Files.Status.DELETED);

				if (map.containsKey(Status.DELETED))
					line = map.get(Status.DELETED)
							.concat("{" + oldFiles.get(i).getName() + "}");
				else
					line = "{" + oldFiles.get(i).getName() + "}";
				map.put(Status.DELETED, line);
			}
		}
		for (int i = 0; i < newFiles.size(); i++) {
			if (newFiles.get(i).getStatus() == Files.Status.DEFAULT) {
				newFiles.get(i).setStatus(Files.Status.NEW);

				if (map.containsKey(Status.NEW))
					line = map.get(Status.NEW)
							.concat("{" + newFiles.get(i).getName() + "}");
				else
					line = "{" + newFiles.get(i).getName() + "}";
				map.put(Status.NEW, line);
			}
		}
	}

	/**
	 * Main method. Makes new {@link Archive} objects, then with these
	 * objects creates a new {@link ArchiveComparator} object. Executes
	 * the comparing and prints the results to the file.
	 * @param nameOld path of the old archive
	 * @param nameNew path of the new archive
	 * @throws IOException
	 */
	public static void executeComparing(String nameOld, String nameNew) throws IOException {
		try {
			Archive oldArch = new Archive(nameOld);
			Archive newArch = new Archive(nameNew);
			PrintResult pr = new PrintResult();
			ArchiveComparator ac = new ArchiveComparator(oldArch, newArch);
			ac.compareArch();
			pr.getResults(ac);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public HashMap<Status, String> getMap() {
		return map;
	}

	public Archive getOldArch() {
		return oldArch;
	}

	public Archive getNewArch() {
		return newArch;
	}
}
