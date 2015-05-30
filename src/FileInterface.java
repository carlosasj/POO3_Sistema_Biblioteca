import java.io.IOException;
import java.util.stream.Stream;

public interface FileInterface {
	void OpenFile(String filename);
	void ReadFile();
	void WriteFile();
	void CloseFile();
}
