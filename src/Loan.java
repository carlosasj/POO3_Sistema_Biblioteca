import java.util.Scanner;

import static java.lang.System.out;

public class Loan {

    private int ID;
    private int BookID;
    private int UserID;

    private String Date;
    private String ReturnDate;
    private String ExpirationDate;

    public Loan (int id, int bookid, int userid, String date, String expirationdate) {
        this.ID = id;
        this.BookID = bookid;
        this.UserID = userid;
        this.Date = date;
        this.ReturnDate = date; // -1
        this.ExpirationDate = expirationdate;
    }

    public Loan (int id) {
        Scanner scan = new Scanner(System.in);
        String inputStr;
        int inputInt;

        this.ID = id;

        out.println("Primeiro, pesquise o usuário");
/*
        this.BookID = bookid;
        this.UserID = userid;
        this.Date = date;
        this.ReturnDate = date; // -1
        this.ExpirationDate = expirationdate;*/
    }

    /*
    public void RegisterLoan () {

        Scanner scan = new Scanner(System.in);

        System.out.println("BookID: ");
        int BookID = scan.nextLine();
        System.out.println("UserID: ");
        int UserID = scan.nextLine();
        System.out.println("Date: ");
        String Date = scan.nextInt();
        System.out.println("Expiration Date: ");
        String ExpirationDate = scan.nextInt();

        this.RegisterLoan(BookID, UserID, Date, ExpirationDate);

    }
    */

    public void RegisterLoan (int BookID, int UserID, String Date, String ExpirationDate) {
        this.BookID = BookID;
        this.UserID = UserID;
        this.Date = Date;
        this.ExpirationDate = ExpirationDate;
    }

    public int getID () { return this.ID; }

    public int getBookID () { return this.BookID; }

    public int getUserID () { return this.UserID; }

    public String getDate () { return this.Date; }

    public String getExpirationDate () { return this.ExpirationDate; }

}
