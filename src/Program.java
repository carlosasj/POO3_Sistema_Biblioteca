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
		Source src = Source.getInstance();


		src.backup();
		src.CloseFile();
	}
}
