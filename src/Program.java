import Database.Source;
import Time.TimeMachine;

import java.io.IOException;

import static java.lang.System.out;


public class Program {
	public static void main (String[] args) throws IOException {
		// Abrir os arquivos
		TimeMachine curTime = TimeMachine.getInstance();

		Source src;
		try {
			src = Source.getInstance(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			src = Source.getInstance(null);
		}

		out.println("Bem-vindo ao sistema de gerenciamento de bibliotecas.");

		// Salvar e fechar os arquivos
		src.backup();
		src.CloseFile();
	}
}
