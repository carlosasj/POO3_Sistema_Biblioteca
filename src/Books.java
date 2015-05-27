import javax.swing.text.EditorKit;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Books extends Database implements FileInterface {

    private List<Book> books;

	public Books (String filename){

        this.nextID = 0;
        books = new LinkedList<Book>();
		this.path = "books.csv";
		this.books = new LinkedList<Book>();
		this.OpenFile(filename);
	}

    public void AddBook(String type, int id, String title, String author, String editor, int year, int totalquantity, int avaliablequantity){
        Book book = null;
        
        if(type.equals("Tex")){
            book = new Text(id, title, author, editor, year, totalquantity, avaliablequantity);
        } else {
            book = new General(id, title, author, editor, year, totalquantity, avaliablequantity);
        }

        this.books.add(book);
    }

    public void AddBook(String type, String title, String author, String editor, int year, int totalquantity, int avaliablequantity){
        Book book = null;

        if(type.equals("Tex")){
            book = new Text(this.nextID, title, author, editor, year, totalquantity, avaliablequantity);
        } else {
            book = new General(this.nextID, title, author, editor, year, totalquantity, avaliablequantity);
        }

        this.nextID++;

        this.books.add(book);
    }
    public void RegisterBook () {

        Scanner scan = new Scanner(System.in);

        System.out.print("Type:\t");
        String Type = scan.nextLine();
        System.out.print("Title:\t");
        String Title = scan.nextLine();
        System.out.print("Author:\t");
        String Author = scan.nextLine();
        System.out.print("Editor:\t");
        String Editor = scan.nextLine();
        System.out.print("Year:\t");
        int Year = scan.nextInt();
        System.out.print("Total Quantity:\t");
        int TotalQuantity = scan.nextInt();
        System.out.print("Avaliable Quantity:\t");
        int AvaliableQuantity = scan.nextInt();

        System.out.println("Deseja inserir cadastro do livro?[s/n]");
        String confirm = scan.nextLine();

        this.AddBook(Type, this.nextID, Title, Author, Editor, Year, TotalQuantity, AvaliableQuantity);
        if (confirm.equals("s")) {
            this.AddBook(Type, Title, Author, Editor, Year, TotalQuantity, AvaliableQuantity);
        }
    }

	public void ReadFile(){
		String line;
		String splitSign = ",";

		try {

            if ((line = br.readLine()) != null) {
                System.out.println("IF");
                this.nextID = Integer.parseInt(line);
                br.readLine();
            }

			while ((line = br.readLine()) != null){
                System.out.println("While...");
				String[] readed = line.split(splitSign);
                System.out.println("\tCheck 1");
                String type = readed[0];
                int id = Integer.parseInt(readed[1]);
                String title = readed[2];
                String author = readed[3];
                String editor = readed[4];
                int year = Integer.parseInt(readed[5]);
                int totalquantity = Integer.parseInt(readed[6]);
                int avaliablequantity = Integer.parseInt(readed[7]);
                System.out.println("\tCheck 2");
                this.AddBook(type, id, title, author, editor, year, totalquantity, avaliablequantity);

                System.out.printf("New Book:\nType: %s \nID: %d \nTitle: %s \nAuthor: %s \nEditor: %s \nYear: %d \nTotal: %d \nAvaliable: %d", books.get(0).Type, this.nextID, books.get(0).Title, books.get(0).Author, books.get(0).Editor, books.get(0).Year, books.get(0).TotalQuantity, books.get(0).AvaliableQuantity);
            }
        } catch (IOException e) {
            System.out.println("Erro na leitura do arquivo.");
            e.printStackTrace();
        }

	}

	public void WriteFile() {
        String SEPARATOR = ",";
        String ENDLINE = "\n";
        String HEADER = "Type,ID,Title,Author,Editor,Year,TotalQuantity,AvaliableQuantity";

        try {
            fw.append(Integer.valueOf(this.nextID).toString());
            fw.append(ENDLINE);
            fw.flush();

            fw.append(HEADER);
            fw.append(ENDLINE);
            fw.flush();

            for (Book b : books) {
                fw.append(b.Type);
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.ID).toString());
                fw.append(SEPARATOR);

                fw.append(b.getTitle());
                fw.append(SEPARATOR);

                fw.append(b.getAuthor());
                fw.append(SEPARATOR);

                fw.append(b.getEditor());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getYear()).toString());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getTotalQuantity()).toString());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getAvaliableQuantity()).toString());
                fw.append(ENDLINE);

                fw.flush();
            }
        } catch (IOException e){
            System.out.println("Erro na escrita do arquivo.");
            e.printStackTrace();
        }
    }
}
