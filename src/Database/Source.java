package Database;

import Time.TimeMachine;

import java.io.IOException;
import java.util.GregorianCalendar;

import static java.lang.System.out;

public class Source extends Database{

	private History history;
	private static Source sourceDB;
	private GregorianCalendar maxDate;

	// Singleton
	public static Source getInstance(String filename){
		if (sourceDB == null){
			sourceDB = new Source(filename);
		}
		return sourceDB;
	}

	private Source (String filename){
		TimeMachine.getInstance(); // Cria|Inicializa os valores da TimeMachine
		path = "source.csv";
		OpenFile(filename);
		ReadFile();
	}

	private void ReadFile(){
		OpenReader();

		String line;
		String splitSign = ",";

		try {
			br.readLine();
			if ((line = br.readLine()) != null) {
				String[] splited = line.split(splitSign);
				Books.getInstance(Integer.parseInt(splited[0]));
				Loans.getInstance(Integer.parseInt(splited[1]));
				Users.getInstance(Integer.parseInt(splited[2]));
				history = History.getInstance(splited[3]);
			}
			else {
				String[] splited = path.split(".csv");
				Books.getInstance(0);
				Loans.getInstance(0);
				Users.getInstance(0);
				history = History.getInstance(splited[0] + "_log.csv");
			}
		} catch (IOException e) {
			out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}
	}

	// Escreve no arquivo
	public void WriteFile(){
		if (History.getFuture()) return;	// Se existir operacoes no fututo, entao o Source nao pode ser alterado
		OpenWriter(false);
		final String SEPARATOR = ",";
		final String ENDLINE = "\n";
		final String HEADER = "nextIDbook,nextIDloan,nextIDuser,log_path";

		try {
			fw.append(HEADER);
			fw.append(ENDLINE);
			fw.flush();

			fw.append(Integer.valueOf(Books.getInstance().getNextID()).toString());
			fw.append(SEPARATOR);
			fw.append(Integer.valueOf(Loans.getInstance().getNextID()).toString());
			fw.append(SEPARATOR);
			fw.append(Integer.valueOf(Users.getInstance().getNextID()).toString());
			fw.append(SEPARATOR);
			fw.append(history.path);
			fw.flush();

		} catch (IOException e){
			out.println("Erro na escrita do arquivo.");
			e.printStackTrace();
		}
	}

	public void exit(){
		WriteFile();
		history.CloseFile();
		CloseFile();
	}
}
