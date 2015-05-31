import Database.Books;
import Database.Source;
import Time.TimeMachine;

import java.io.IOException;


public class Program {
	public static void main (String[] args) throws IOException {

        TimeMachine curTime = new TimeMachine();


		//Books b = new Books();
		//b.OpenFile("books.csv");
		//b.ReadFile();

		Source src = new Source(null);
		Books b = Books.getInstance();

		//b.AddBook("Tex","O fabuloso livro de teste","Jhon Doe","Editora Teste",2015,10,8);
		//b.AddBook("Tex","O fabuloso livro de teste 2","Jhon Doe","Editora Teste",2015,10,8);
		//b.RegisterBook();
		b.Search();
		src.WriteFile();
	}
}
