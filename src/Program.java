public class Program {
	public static void main (String[] args){

		Books b = new Books(null);
		b.OpenFile("books.csv");
		b.ReadFile();
		b.AddBook("Tex",20,"O fabuloso livro de teste","Jhon Doe","Editora Teste",2015,10,8);
		b.AddBook("Tex",25,"O fabuloso livro de teste","Jhon Doe","Editora Teste",2015,10,8);
		b.RegisterBook();
		b.WriteFile();
		b.CloseFile();
	}
}
