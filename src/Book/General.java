package Book;

public class General extends Book {

	public General(int id, String title, String author, String editor, int year, int totalquantity) {
		this.Type = "Gen";
		this.ID = id;
		this.Title = title;
		this.Author = author;
		this.Editor = editor;
		this.Year = year;
		this.TotalQuantity = totalquantity;
		this.AvaliableQuantity = totalquantity;
	}
}
