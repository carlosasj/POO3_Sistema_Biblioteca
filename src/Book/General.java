package Book;

import Database.History;

public class General extends Book {

	public General(int id, String title, String author, String editor, int year, int totalquantity) {
		Type = "Gen";
		ID = id;
		Title = title;
		Author = author;
		Editor = editor;
		Year = year;
		TotalQuantity = totalquantity;
		AvaliableQuantity = totalquantity;
	}
}
