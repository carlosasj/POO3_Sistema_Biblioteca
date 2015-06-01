package Loan;

import Database.Books;
import Database.History;
import Database.Users;

import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.System.out;

public class Loan {

	private int ID;
	private int BookID;
	private int UserID;

	public GregorianCalendar Date;
	public GregorianCalendar ReturnDate;
	public GregorianCalendar ExpirationDate;

	public Loan (int id, int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {
		ID = id;
		BookID = bookid;
		UserID = userid;
		Date = date;
		ExpirationDate = expirationdate;
		History.getInstance().logAdd(this);
	}

	public int getID () { return ID; }

	public int getBookID () { return BookID; }

	public int getUserID () { return UserID; }

	public GregorianCalendar getDate () { return Date; }

	public GregorianCalendar getExpirationDate () { return ExpirationDate; }

	public void Print () {
        out.printf("ID:\t\t\t%d\nUsuario:\t\t\t%s\nLivro:\t\t\t%s\nData:\t\t\t%s\nDevolucao:\t\t\t%s",
				ID,
				Users.getInstance().FindByID(this.UserID).getName(),
				Books.getInstance().FindByID(this.BookID).getTitle(),
				Date.toString(),
				ExpirationDate.toString());
    }

}
