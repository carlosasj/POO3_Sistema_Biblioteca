import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileInterface {
	void OpenFile(String filename);
	void ReadFile() throws IOException;
	void WriteFile() throws IOException;
	void CloseFile();
}
