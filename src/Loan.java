import java.util.Scanner;

public class Loan {

    private int BookID;
    private int UserID;

    private String Date;
    private String ExpirationDate;

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
        this.Date = UserID;
        this.ExpirationDate = ExpirationDate;
    }

    public int getBookID () { return this.BookID; }

    public int getUserID () { return this.UserID; }

    public String getDate () { return this.Date; }

    public String getExpirationDate () { return this.ExpirationDate; }

}
