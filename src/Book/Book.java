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

	public String getType() { return this.Type; }
	public int getID() { return this.ID; }
	public String getTitle() { return this.Title; }
	public String getAuthor() { return this.Author; }
	public String getEditor() { return this.Editor; }
	public int getYear() { return this.Year; }
	public int getTotalQuantity() { return this.TotalQuantity; }
	public int getAvaliableQuantity() { return this.AvaliableQuantity; }

	public void goLoan() { this.AvaliableQuantity--; }
	public void backLoan() { this.AvaliableQuantity++; }

	public void Print(){
		out.printf(
				"Tipo:\t\t%s\nID:\t\t\t%d\nTitulo:\t\t%s\nAutor:\t\t%s\nEditora:\t\t%s\nAno:\t\t%d\nTotal:\t\t%d\nDisponivel:\t%d\n",
				 Type,        ID,          Title,         Author,       Editor,         Year,       TotalQuantity,AvaliableQuantity);
	}

	public boolean canLoan() {
		return (AvaliableQuantity > 0);
	}

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
