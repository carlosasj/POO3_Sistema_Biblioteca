package Database;

import java.io.*;
import static java.lang.System.out;

abstract public class Database {
	protected File file = null;
	protected BufferedReader br = null;
	protected FileWriter fw = null;
	protected String path = null;

    protected int nextID;

	protected void OpenFile(String filename) {
		if (filename != null) { path = filename; }

		// Try open the file
		file = new File(path);
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				out.println("Erro ao criar o arquivo.");
				e.printStackTrace();
			}
		}
	}

	protected void OpenReader() {
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			out.println("Erro ao criar o Buffered Reader.");
			e.printStackTrace();
		}
	}

	protected void OpenWriter() {
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			out.println("Erro ao criar o File Writer.");
			e.printStackTrace();
		}
	}

	protected void CloseFile(){
		try {
			fw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
