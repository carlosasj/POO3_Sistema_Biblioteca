abstract class Book {

    public String Type;
    public int ID;
    public String Title;
    public String Author;
    public String Editor;
    public int Year;

    public int TotalQuantity;
    public int AvaliableQuantity;

    public void Print(){
        System.out.printf(
            "Type:\t\t%s\nID:\t\t\t%d\nTitle:\t\t%s\nAuthor:\t\t%s\nEditor:\t\t%s\nYear:\t\t%d\nTotal:\t\t%d\nAvaliable:\t%d\n",
             Type,        ID,          Title,        Author,        Editor,        Year,       TotalQuantity, AvaliableQuantity);
    }

    public String getTitle() { return this.Title; }

    public String getAuthor() { return this.Author; }

    public String getEditor() { return this.Editor; }

    public int getYear() { return this.Year; }

    public int getTotalQuantity() { return this.TotalQuantity; }

    public int getAvaliableQuantity() { return this.AvaliableQuantity; }

}
