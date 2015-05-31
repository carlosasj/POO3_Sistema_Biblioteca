package Database;

import java.io.IOException;

import static java.lang.System.out;

public class Source extends Database{

	private Books booksDB;
	private Loans loansDB;
	private Users usersDB;
	private static Source sourceDB;

	// Singleton
	public static Source getInstance() { return sourceDB; }
	public static Source getInstance(String filename){
		if (sourceDB == null){
			sourceDB = new Source(filename);
		}
		return sourceDB;
	}

	private Source (String filename){
		this.nextID = 0;
		this.path = "source.csv";
		this.OpenFile(filename);
		this.ReadFile();
	}

	private void ReadFile(){
		this.OpenReader();

		String line;
		String splitSign = ",";

		try {
			br.readLine();
			if ((line = br.readLine()) != null) {
				String[] splited = line.split(splitSign);
				booksDB = Books.getInstance(splited[0]);
				loansDB = Loans.getInstance(splited[1]);
				usersDB = Users.getInstance(splited[2]);
			}
			else {
				String[] splited = path.split(".csv");
				booksDB = Books.getInstance(splited[0] + "_books.csv");
				loansDB = Loans.getInstance(splited[0] + "_loans.csv");
				usersDB = Users.getInstance(splited[0] + "_users.csv");
			}
		} catch (IOException e) {
			out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}
	}

	private void WriteFile(){
		OpenWriter();
		final String SEPARATOR = ",";
		final String ENDLINE = "\n";
		final String HEADER = "BooksDB_path,LoansDB_path,UsersDB_path";

		try {
			fw.append(HEADER);
			fw.append(ENDLINE);
			fw.flush();

			fw.append(booksDB.path);
			fw.append(SEPARATOR);
			fw.append(loansDB.path);
			fw.append(SEPARATOR);
			fw.append(usersDB.path);
			fw.flush();

		} catch (IOException e){
			out.println("Erro na escrita do arquivo.");
			e.printStackTrace();
		}
	}

	public void backup(){
		booksDB.WriteFile();
		loansDB.WriteFile();
		usersDB.WriteFile();
		this.WriteFile();
	}

	public void CloseFile(){
		booksDB.CloseFile();
		loansDB.CloseFile();
		usersDB.CloseFile();
		try {
			fw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
