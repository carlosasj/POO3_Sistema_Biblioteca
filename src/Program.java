import Database.Source;
import Time.TimeMachine;
import java.io.IOException;

import static java.lang.System.out;


public class Program {
	public static void main (String[] args) throws IOException {

		for (String s : args){
			out.println("- " + s);
		}

		TimeMachine curTime = new TimeMachine();

		Source src = null;
		try {
			src = Source.getInstance(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			src = Source.getInstance(null);
		}

		src.backup();
		src.CloseFile();
	}
}
