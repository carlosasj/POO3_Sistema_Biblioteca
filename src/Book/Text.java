package Book;

import Database.History;

public class Text extends Book{

	public Text(int id, String title, String author, String editor, int year, int totalquantity) {
		Type = "Tex";
		ID = id;
		Title = title;
		Author = author;
		Editor = editor;
		Year = year;
		TotalQuantity = totalquantity;
		AvaliableQuantity = totalquantity;

		History.getInstance().logAdd(this);
	}

}
