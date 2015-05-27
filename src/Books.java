import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Books implements Database {
	BufferedReader br = null;

	public void OpenFile(String path){
		if (path == null) {
			path = "/books.csv";
		}

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void ReadFile(){
		String line = null;
		String splitSign = ",";

		try {
			while ((line = br.readLine()) != null){
				String[] readed = line.split(splitSign);

				for (String s : readed){
					System.out.println(s);
				}
				System.out.println("-- End\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
