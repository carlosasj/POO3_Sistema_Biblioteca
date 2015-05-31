package Loan;

import Database.Books;
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
		this.ID = id;
		this.BookID = bookid;
		this.UserID = userid;
		this.Date = date;
		this.ExpirationDate = expirationdate;
	}

	public Loan (int id) {
		Scanner scan = new Scanner(System.in);
		String inputStr;
		int inputInt;

		this.ID = id;

		out.println("Primeiro, pesquise o usuario");
/*
		this.BookID = bookid;
		this.UserID = userid;
		this.Date = date;
		this.ReturnDate = date; // -1
		this.ExpirationDate = expirationdate;*/
	}

	public int getID () { return this.ID; }

	public int getBookID () { return this.BookID; }

	public int getUserID () { return this.UserID; }

	public GregorianCalendar getDate () { return this.Date; }

	public GregorianCalendar getExpirationDate () { return this.ExpirationDate; }

	public void Print () {
        out.printf("ID:\t\t\t%d\nUsuário:\t\t\t%s\nLivro:\t\t\t%s\nData:\t\t\t$s\nDevolução:\t\t\t$s", this.ID, Users.getInstance().FindByID(this.UserID).getName(), Books.getInstance().FindByID(this.BookID).getTitle(), this.Date.toString(), this.ExpirationDate.toString());
    }

}
