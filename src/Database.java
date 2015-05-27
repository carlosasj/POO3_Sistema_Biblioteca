import java.io.*;

abstract public class Database {
	public BufferedReader br = null;
	public FileWriter fw = null;
	public String path = null;

    public int nextID;

	public void OpenFile(String filename) {
		if (filename != null) { path = filename; }

		// Try open the file and create the BufferedReader and FileWriter
		try {
			br = new BufferedReader(new FileReader(path));
			fw = new FileWriter(path);
		} catch (IOException e) {
			try {
				new File(path).createNewFile();
				br = new BufferedReader(new FileReader(path));
				fw = new FileWriter(path);
			} catch (IOException g) {
				System.out.println("Problemas na Criação e/ou leitura do arquivo.");
				System.out.println("Verifique se você tem permissão para ler e/ou criar arquivos.");
			}
		}
	}

	public void CloseFile(){}
}
