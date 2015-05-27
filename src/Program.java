public class Program {
	public static void main (String[] args){

		Books b = new Books(null);
		b.OpenFile(null);
		b.ReadFile();
		b.AddBook("Tex",10,"O fabuloso livro de teste","Jhon Doe","Editora Teste",2015,10,8);
	}
}
