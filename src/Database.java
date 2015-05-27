import java.io.*;

abstract public class Database {
	public BufferedReader br = null;
	public String path = null;


	public void OpenFile(String filename) {
		if (filename != null) { path = filename; }

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			try {
				new File(path).createNewFile();
				br = new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException f) {
				System.out.println("Problemas na Criação e/ou leitura do arquivo.");
				System.out.println("Verifique se você tem permissão para ler e/ou criar arquivos.");
			} catch (IOException g){
				System.out.println("Problemas na Criação e/ou leitura do arquivo.");
				System.out.println("Verifique se você tem permissão para ler e/ou criar arquivos.");
			}
		}
	}

	public void CloseFile(){}
}
