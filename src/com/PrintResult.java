package com;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Creates a new file and writes the result to this file.
 *
 * @author elizavetavolianica
 */
public class PrintResult {
	File 		file;
	FileWriter	writer;

	public PrintResult(){
		file = new File("results.txt");
	}

	/**
	 * Prints the result of comparing archives. Depending
	 * on the status of the files prints with a particular formatting.
	 *
	 * @param archives
	 * @throws IOException
	 */
	public void getResults(ArchiveComparator archives) throws IOException {
		writer = new FileWriter(file);
		Archive one = archives.getOldArch();
		Archive two = archives.getNewArch();

		printHeader(one.getName(), two.getName());

		Iterator it = archives.getMap().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			printPair(pair);
		}
		writer.close();
	}

	private void printHeader(String nameOld, String nameNew) throws IOException {
		writer.write(String.format("%-64.64s|%-64.64s\n",
				nameOld,
				nameNew));
		for (int i = 0; i < 64; i++)
			writer.write("-");
		writer.write("+");
		for (int i = 0; i < 64; i++)
			writer.write("-");
		writer.write("\n");
	}
	private void printPair(Map.Entry <Files.Status, String> pair) throws IOException {
		switch (pair.getKey()) {
			case DELETED:
				printDeleted(pair.getValue());
				break;
			case NEW:
				printNew(pair.getValue());
				break;
			case UPDATED:
				printUpdated(pair.getValue());
				break;
			case RENAMED:
				printRenamed(pair.getValue());
				break;
			default:
				break;
		}
	}

	private void printDeleted(String deleted) throws IOException {
		String[] pairs = deleted.split("}\\{");
		for (String file:
			 pairs) {
			if (file.contains("{"))
				file = file.substring(1, file.length());
			if (file.contains("}"))
				file = file.substring(0, file.length() - 1);
			writer.write(String.format("- %-62.62s|%-64.64s\n",
					file,
					""));
		}
	}

	private void printNew(String newFiles) throws IOException {
		String[] pairs = newFiles.split("}\\{");
		for (String file:
				pairs) {
			if (file.contains("{"))
				file = file.substring(1, file.length());
			if (file.contains("}"))
				file = file.substring(0, file.length() - 1);
			writer.write(String.format("%-64.64s|+ %-62.62s\n",
					"",
					file));
		}
	}

	private void printUpdated(String updated) throws IOException {
		String[] pairs = updated.split("}\\{");
		for (String pair:
				pairs) {
			if (pair.contains("{"))
				pair = pair.substring(1, pair.length());
			if (pair.contains("}"))
				pair = pair.substring(0, pair.length() - 1);
			String[] name = pair.split("\\|");
			writer.write(String.format("* %-62.62s|* %-62.62s\n",
					name[0],
					name[1]));
		}
	}

	private void printRenamed(String updated) throws IOException {
		String[] pairs = updated.split("}\\{");
		for (String pair:
				pairs) {
			if (pair.contains("{"))
				pair = pair.substring(1, pair.length());
			if (pair.contains("}"))
				pair = pair.substring(0, pair.length() - 1);
			String[] name = pair.split("\\|");
			writer.write(String.format("? %-62.62s|? %-62.62s\n",
					name[0],
					name[1]));
		}
	}
}
