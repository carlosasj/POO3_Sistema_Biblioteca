import java.util.Scanner;

abstract class Book {

    public int ID;
    public String Title;
    public String Author;
    public String Editor;
    public int Year;

    private int TotalQuantity;
    private int AvaliableQuantity;

    public void RegisterBook () {

        Scanner scan = new Scanner(System.in);

        System.out.println("Title: ");
        String Title = scan.nextLine();
        System.out.println("Author: ");
        String Author = scan.nextLine();
        System.out.println("Year: ");
        int Year = scan.nextInt();
        System.out.println("Total Quantity: ");
        int TotalQuantity = scan.nextInt();
        System.out.println("Avaliable Quantity: ");
        int AvaliableQuantity = scan.nextInt();

        this.RegisterBook(Title, Author, Year, TotalQuantity, AvaliableQuantity);

    }

    public void RegisterBook (String Title, String Author, int Year, int TotalQuantity, int AvaliableQuantity) {
        this.Title = Title;
        this.Author = Author;
        this.Year = Year;
        this.TotalQuantity = TotalQuantity;
        this.AvaliableQuantity = AvaliableQuantity;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getAuthor() {
        return this.Author;
    }

    public int getYear() {
        return this.Year;
    }

    public int getTotalQuantity() {
        return this.TotalQuantity;
    }

    public int getAvaliableQuantity() {
        return this.AvaliableQuantity;
    }

}
