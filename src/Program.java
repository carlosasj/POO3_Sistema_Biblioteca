import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Program {
	public static void main (String[] args){
		try {
			File file = new File("filename.txt");

			// Create file if it does not exist
			boolean success = file.createNewFile();
			if (success) {
				System.out.println("OK");
			} else {
				System.out.println("Fail");
			}
		} catch (IOException e) {
		}


		Books b = new Books();
		b.OpenFile(null);
		b.ReadFile();
	}

    public String Date;



}
