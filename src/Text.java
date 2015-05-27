public class Text extends Book{

    public Text(int id, String title, String author, String editor, int year, int totalquantity, int avaliablequantity) {

        this.Type = "Tex";
        this.ID = id;
        this.Title = title;
        this.Author = author;
        this.Editor = editor;
        this.Year = year;
        this.TotalQuantity = totalquantity;
        this.AvaliableQuantity = avaliablequantity;
    }

}
