import java.io.IOException;
import static java.lang.System.out;

public class Program {
	public static void main (String[] args) throws IOException {

		Books b = new Books(null);
		b.OpenFile("books.csv");
		b.ReadFile();
		//b.AddBook("Tex","O fabuloso livro de teste","Jhon Doe","Editora Teste",2015,10,8);
		//b.AddBook("Tex","O fabuloso livro de teste 2","Jhon Doe","Editora Teste",2015,10,8);
		//b.RegisterBook();
		b.Search();

		b.WriteFile();
		b.CloseFile();
	}
}
