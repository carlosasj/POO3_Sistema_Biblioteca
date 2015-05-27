import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Books extends Database implements FileInterface {
	private List<Book> books;

	Books (String filename){
		this.path = "books.csv";
		this.books = new LinkedList<Book>();
		this.OpenFile(filename);
	}

	public void ReadFile(){
		String line = null;
		String splitSign = ",";

		try {
			while ((line = br.readLine()) != null){
				String[] readed = line.split(splitSign);
				// Cria objeto Book
				// Escreve os valores nele
				// Adiciona na LinkedList
				// Usar um método para fazer isso.
			}
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}
	}

	public void WriteFile(){}
}
