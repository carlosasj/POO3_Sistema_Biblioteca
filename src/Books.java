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
		String line;
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
        String SEPARATOR = ",";
        String ENDLINE = "\n";
        String HEADER = "Type,ID,Title,Author,Editor,Year,TotalQuantity,AvaliableQuantity";

        fw.append(Integer.valueOf(this.nextID).toString());
        fw.flush();
        fw.append(HEADER);
        fw.flush();

        for (Book b : books) {
            fw.append(b.Type);
            fw.append(SEPARATOR);

            fw.append(b.ID);
            fw.append(SEPARATOR);

            fw.append(b.getTitle());
            fw.append(SEPARATOR);

            fw.append(b.getAuthor());
            fw.append(SEPARATOR);

            fw.append(b.getEditor());
            fw.append(SEPARATOR);

            fw.append(b.getYear());
            fw.append(SEPARATOR);

            fw.append(b.getTotalQuantity());
            fw.append(SEPARATOR);

            fw.append(b.getAvaliableQuantity());
            fw.append(ENDLINE);

            fw.flush();
        }
    }
}
