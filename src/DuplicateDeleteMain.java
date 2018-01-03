import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DuplicateDeleteMain {
	public static void main(String[] args) {
		for (String arg : args) {
			deleteDupes(arg);
		}
	}

	static void deleteDupes(String directory) {
		File[] files = readFiles(directory);
		HashMap<Integer, ArrayList<File>> duplicates = findDuplicates(files);
		deleteDuplicates(duplicates);
	}

	static HashMap<Integer, ArrayList<File>> findDuplicates(File[] files) {
		File currentFile;
		String currentFileName;
		int index = 0;
		HashMap<Integer, ArrayList<File>> dups = new HashMap<>();

		for (int i = 0; i < files.length; i++) {
			currentFile = files[i];
			currentFileName = files[i].getName();
			for (int j = 0; j < files.length; j++) {
				if (i == j) {
					continue;
				}

				if (files[j].getName().contains(currentFileName.substring(0, currentFileName.lastIndexOf("_")))) {
					if (!dups.containsKey(index)) {
						dups.put(index, new ArrayList<>());
					}
					dups.get(index).add(currentFile);
					dups.get(index).add(files[j]);
					index++;
					break;
				}
			}
		}

		return dups;
	}

	static void deleteDuplicates(HashMap<Integer, ArrayList<File>> duplicates) {
		HashMap<String, Boolean> logs = new HashMap<>();

		for (int i = 0; i < duplicates.size(); i++) {
			if (duplicates.get(i).get(0).length() > duplicates.get(i).get(1).length()) {
				logs.put(duplicates.get(i).get(1).getName(), duplicates.get(i).get(1).delete());
			} else {
				logs.put(duplicates.get(i).get(1).getName(), duplicates.get(i).get(0).delete());
			}
		}

		logs.forEach((k, v) -> System.out.println(k + ": " + v));
	}

	static File[] readFiles(String directory) {
		return new File(directory).listFiles();
	}
}
