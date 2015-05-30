package Book;

abstract public class Book {

    protected String Type;
    protected int ID;
    protected String Title;
    protected String Author;
    protected String Editor;
    protected int Year;

    protected int TotalQuantity;
    protected int AvaliableQuantity;

    public String getType() { return this.Type; }

    public int getID() { return this.ID; }

    public String getTitle() { return this.Title; }

    public String getAuthor() { return this.Author; }

    public String getEditor() { return this.Editor; }

    public int getYear() { return this.Year; }

    public int getTotalQuantity() { return this.TotalQuantity; }

    public int getAvaliableQuantity() { return this.AvaliableQuantity; }

    public void Print(){
        System.out.printf(
                "Type:\t\t%s\nID:\t\t\t%d\nTitle:\t\t%s\nAuthor:\t\t%s\nEditor:\t\t%s\nYear:\t\t%d\nTotal:\t\t%d\nAvaliable:\t%d\n",
                Type,        ID,          Title,        Author,        Editor,        Year,       TotalQuantity, AvaliableQuantity);
    }

}
