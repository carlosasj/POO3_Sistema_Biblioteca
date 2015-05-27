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

        System.out.println("Type: ");
        String Type = scan.nextLine();
        System.out.println("Title: ");
        String Title = scan.nextLine();
        System.out.println("Author: ");
        String Author = scan.nextLine();
        System.out.println("Editor: ");
        String Editor = scan.nextLine();
        System.out.println("Year: ");
        int Year = scan.nextInt();
        System.out.println("Total Quantity: ");
        int TotalQuantity = scan.nextInt();
        System.out.println("Avaliable Quantity: ");
        int AvaliableQuantity = scan.nextInt();

        System.out.println("Deseja inserir cadastro do livro?[s/n]");
        String confirm = scan.nextLine();

        if (confirm.equals("s")) {
            this.AddBook(Type, Title, Author, Editor, Year, TotalQuantity, AvaliableQuantity);
        }
    }

	public void ReadFile(){
		String line = null;
		String splitSign = ",";

		try {
            if ((line = br.readLine()) != null) {
                this.nextID = Integer.parseInt(line);
                br.readLine();
            }

			while ((line = br.readLine()) != null){
				String[] readed = line.split(splitSign);
                String type = readed[0];
                int id = Integer.parseInt(readed[1]);
                String title = readed[2];
                String author = readed[3];
                String editor = readed[4];
                int year = Integer.parseInt(readed[5]);
                int totalquantity = Integer.parseInt(readed[6]);
                int avaliablequantity = Integer.parseInt(readed[7]);
                this.AddBook(type, id, title, author, editor, year, totalquantity, avaliablequantity);
			}
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}

        //System.out.printf("Type: %s \nID: %d \nTitle: %s \nAuthor: %s \nEditor: %s \nYear: %d \nTotal: %d \nAvaliable: %d", books.get(0).Type, this.nextID, books.get(0).Title, books.get(0).Author, books.get(0).Editor, books.get(0).Year, books.get(0).TotalQuantity, books.get(0).AvaliableQuantity);
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
