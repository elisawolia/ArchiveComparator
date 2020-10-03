package com;

import java.util.HashMap;

/**
 * File object. Contains the name, size and status of the file.
 *
 * @author elizavetavolianica
 */
public class Files {
	private String	name;
	private long	size;
	private Status	fileStatus;

	public Files(String name, long size) {
		this.name = name;
		this.size = size;
		this.fileStatus = Status.DEFAULT;
	}

	public enum Status {
		UPDATED,
		DELETED,
		NEW,
		RENAMED,
		OK,
		DEFAULT;
	}

	/**
	 * Executes the comparing of the two files. Deletes these instances from
	 * the map if files had been marked (Status changed). If size and names are equal
	 * - file has not changed, if only the name has changed - there is a probability
	 * of renaming (Status.RENAMED), if only the size has changed - there is a probability
	 * of the updating (Status.UPDATE), else leaves Status.DEFAULT.
	 *
	 * @param oldFiles
	 * @param newFiles
	 * @param map
	 */
	public static void compareFile(Files oldFiles, Files newFiles, HashMap<Status, String> map) {
		String line;

		if (oldFiles.getName().equals(newFiles.getName())) {
			if (oldFiles.getSize() == newFiles.getSize()) {
				oldFiles.setStatus(Status.OK);
				newFiles.setStatus(Status.OK);
			}
			else {
				oldFiles.setStatus(Status.UPDATED);
				newFiles.setStatus(Status.UPDATED);

				if (map.containsKey(Status.UPDATED))
					line = map.get(Status.UPDATED)
							.concat("{" + oldFiles.getName() + "|" + newFiles.getName() + "}");
				else
					line = "{" + oldFiles.getName() + "|" + newFiles.getName() + "}";
				map.put(Status.UPDATED, line);
			}
		}
		else {
			if (oldFiles.getSize() == newFiles.getSize()) {
				oldFiles.setStatus(Status.RENAMED);
				newFiles.setStatus(Status.RENAMED);

				if (map.containsKey(Status.RENAMED))
					line = map.get(Status.RENAMED)
							.concat("{" + oldFiles.getName() + "|" + newFiles.getName() + "}");
				else
					line = "{" + oldFiles.getName() + "|" + newFiles.getName() + "}";
				map.put(Status.RENAMED, line);
			}
		}
	}

	public void setStatus(Status fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public Status getStatus() {
		return fileStatus;
	}
}
