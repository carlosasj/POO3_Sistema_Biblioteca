import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Books extends Database implements FileInterface {
	private List<Book> books;

    private List<Book> books;

	public Books (String filename){
        books = new LinkedList<Book>();
		this.path = "books.csv";
		this.books = new LinkedList<Book>();
		this.OpenFile(filename);
	}

    public void AddBook(String type, int id, String title, String author, String editor, int year, int totalquantity, int avaliablequantity){
        Book book = null;
        
        if(type.equals("Tex")){
            book = new Text(id, title, author, editor, year);
        } else {
            book = new General(id, title, author, editor, year);
        }

        this.books.add(book);
    }

	public void ReadFile(){
		String line = null;
		String splitSign = ",";

		try {
			while ((line = br.readLine()) != null){
				String[] readed = line.split(splitSign);
                this.AddBook(readed[0], Integer.parseInt(readed[1]), readed[2], readed[3], readed[4], Integer.parseInt(readed[5]), Integer.parseInt(readed[6]), Integer.parseInt(readed[7]));
			}
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}
	}

	public void WriteFile() throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(this.path));

        for (int i = 0; i < this.books.size(); i++) {
            bw.write(this.books.get(i).Type);
            bw.write(",");
            bw.write(this.books.get(i).ID);
            bw.write(",");
            bw.write(this.books.get(i).getTitle());
            bw.write(",");
            bw.write(this.books.get(i).getAuthor());
            bw.write(",");
            bw.write(this.books.get(i).getEditor());
            bw.write(",");
            bw.write(this.books.get(i).getYear());
            bw.write(",");
            bw.write(this.books.get(i).getTotalQuantity());
            bw.write(",");
            bw.write(this.books.get(i).getAvaliableQuantity());
            bw.write("\n");

        }
    }
}
