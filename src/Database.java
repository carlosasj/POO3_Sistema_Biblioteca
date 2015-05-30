import java.io.*;
import static java.lang.System.out;

abstract public class Database {
	private File file = null;
	public BufferedReader br = null;
	public FileWriter fw = null;
	public String path = null;

    public int nextID;

	public void OpenFile(String filename) {
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

	public void OpenReader() {
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			out.println("Erro ao criar o Buffered Reader");
			e.printStackTrace();
		}
	}

	public void OpenWriter() {
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			out.println("Erro ao criar o File Writer");
			e.printStackTrace();
		}
	}

	public void CloseFile(){
		try {
			fw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
