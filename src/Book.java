import java.util.Scanner;

abstract class Book {

    public String Type;
    public int ID;
    public String Title;
    public String Author;
    public String Editor;
    public int Year;

    public int TotalQuantity;
    public int AvaliableQuantity;


    public String getTitle() {
        return this.Title;
    }

    public String getAuthor() {
        return this.Author;
    }

    public String getEditor() { return  this.Editor; }

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
