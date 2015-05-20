import java.util.Scanner;

abstract class Book {

    private String Title;
    private String Author;
    private int Year;

    private int TotalQuantity;
    private int AvaliableQuantity;

    public Book() {

        RegisterBook();
    }

    public void RegisterBook () {

        Scanner scan = new Scanner(System.in);

        System.out.println("Title: ");
        this.Title = scan.nextLine();
        System.out.println("Author: ");
        this.Author = scan.nextLine();
        System.out.println("Year: ");
        this.Year = scan.nextInt();
        System.out.println("Total Quantity: ");
        this.TotalQuantity = scan.nextInt();
        System.out.println("Avaliable Quantity: ");
        this.AvaliableQuantity = scan.nextInt();

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
