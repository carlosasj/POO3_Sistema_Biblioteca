package Book;

import static java.lang.System.out;

abstract public class Book {

	protected String Type;
	protected int ID;
	protected String Title;
	protected String Author;
	protected String Editor;
	protected int Year;

	protected int TotalQuantity;
	protected int AvaliableQuantity;

	public String getType() { return Type; }
	public int getID() { return ID; }
	public String getTitle() { return Title; }
	public String getAuthor() { return Author; }
	public String getEditor() { return Editor; }
	public int getYear() { return Year; }
	public int getTotalQuantity() { return TotalQuantity; }
	public int getAvaliableQuantity() { return AvaliableQuantity; }

	// Faz um emprestimo, diminui a quantia de disponivel do livro
	public void goLoan() { AvaliableQuantity--; }
	// Devolve um emprestimo, aumenta a quantia disponivel do livro
	public void backLoan() { AvaliableQuantity++; }

	public void Print(){
		out.printf(
				"Tipo:\t\t%s\nID:\t\t\t%d\nTitulo:\t\t%s\nAutor:\t\t%s\nEditora:\t\t%s\nAno:\t\t%d\nTotal:\t\t%d\nDisponivel:\t%d\n",
				 Type,        ID,          Title,         Author,       Editor,         Year,       TotalQuantity,AvaliableQuantity);
	}

    // Verifica se o livro pode ser emprestado
	public boolean canLoan() {
		return (AvaliableQuantity > 0);
	}

    // Modifica a quantia de livros disponiveis
	public boolean increase (int n, boolean validate){
		boolean result = (TotalQuantity+n >= 0) && (AvaliableQuantity+n >= 0);
		if (validate){
			if (result) increase(n);
		}
		else {
			increase(n);
		}

		return result;
	}

	public void increase (int n){
		TotalQuantity += n;
		AvaliableQuantity += n;
	}
}
