import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Users implements File {
	public BufferedReader br = null;

	public void OpenFile(){
		try {
			br = new BufferedReader(new FileReader("users.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
