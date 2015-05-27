import java.io.*;

abstract public class Database {
	public BufferedReader br = null;
	public String path = null;

    public int nextID;

	public void OpenFile(String filename) {
		if (filename != null) { path = filename; }

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			try {
				new File(path).createNewFile();
				br = new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException f) {
				System.out.println("Problemas na Cria��o e/ou leitura do arquivo.");
				System.out.println("Verifique se voc� tem permiss�o para ler e/ou criar arquivos.");
			} catch (IOException g){
				System.out.println("Problemas na Cria��o e/ou leitura do arquivo.");
				System.out.println("Verifique se voc� tem permiss�o para ler e/ou criar arquivos.");
			}
		}
	}

	public void CloseFile(){}
}
