package Database;

import Time.TimeMachine;
import Book.Book;
import Loan.Loan;
import User.User;

import java.io.IOException;
import java.util.GregorianCalendar;

import static java.lang.System.out;

public class History extends Database {
	private static History hist;
	private static boolean future = false;	// Indica se ha alguma operacao numa data maior que a "atual"
	private static String split = ",";

	public static History getInstance() { return hist; }
	protected static History getInstance(String filename){
		if (hist == null){
			hist = new History(filename);
		}
		return hist;
	}

	private History (String filename) {
		path = "history.csv";
		OpenFile(filename);
		ReadFile();
	}

	// Le o arquivo
	private void ReadFile(){
		OpenReader();

		String line;

		try {
			br.readLine(); // Pula o cabecalho

			GregorianCalendar date_backup = TimeMachine.CurrentDate();

			while ((line = br.readLine()) != null && !future) {
				String[] splited = line.split(split);
				GregorianCalendar date_readed = TimeMachine.strToCalendar(splited[0]);

				if (date_readed.after(date_backup)){
					future = true;
				}
				else {
					TimeMachine.getInstance().setDate(date_readed);
					switch (splited[1]){

						case "Books":
							switch (splited[2]){
								case "add":
									Books.getInstance().Load(splited[3],
											   Integer.parseInt(splited[4]),
																splited[5],
																splited[6],
																splited[7],
											   Integer.parseInt(splited[8]),
											   Integer.parseInt(splited[9]));
									break;
								case "del":
									Books.getInstance()
											.Del(Integer.parseInt(splited[3]));
									break;
								case "inc":
									Books.getInstance()
											.FindByID(Integer.parseInt(splited[3]))
											.increase(Integer.parseInt(splited[4]));
									break;
							}
							break;

						case "Loans":
							switch (splited[2]){
								case "add":
									Loans.getInstance().Load(
											Integer.parseInt(splited[3]),
											Integer.parseInt(splited[4]),
											Integer.parseInt(splited[5]));
									break;
								case "del":
									Loans.getInstance()
											.Del(Integer.parseInt(splited[3]));
									break;
							}
							break;

						case "Users":
							switch (splited[2]){
								case "add":
									Users.getInstance().Load(splited[3],
											   Integer.parseInt(splited[4]),
																splited[5]);
									break;
								case "del":
									Users.getInstance()
											.Del(Integer.parseInt(splited[3]));
									break;
							}
							break;
					}
				}
			}
			TimeMachine.getInstance().setDate(date_backup);
		} catch (IOException e){
			out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}
	}

	// logAdd
	// Adiciona usuario, livro ou emprestimo no log

	public void logAdd (User u){
		String log = "Users" + split
					+"add" + split
					+u.getType() + split
					+u.getID() + split
					+u.getName();
		WriteFile(log);
	}

	public void logAdd (Book b){
		String log = "Books" + split
				+"add" + split
				+b.getType() + split
				+b.getID() + split
				+b.getTitle() + split
				+b.getAuthor() + split
				+b.getEditor() + split
				+b.getYear() + split
				+b.getTotalQuantity();
		WriteFile(log);
	}

	public void logAdd (Loan l){
		String log = "Loans" + split
				+"add" + split
				+l.getID() + split
				+l.getBookID() + split
				+l.getUserID() + split
				+TimeMachine.CalendarToStr(l.getDate()) + split
				+TimeMachine.CalendarToStr(l.getExpirationDate()) + split;
		WriteFile(log);
	}

	// logDel
	// Remove usuario, livro ou emprestimo do log

	public void logDel (User u){
		String log = "Users" + split
				+"del" + split
				+u.getType();
		WriteFile(log);
	}

	public void logDel (Book b){
		String log = "Books" + split
				+"del" + split
				+b.getID();
		WriteFile(log);
	}

	public void logDel (Loan l){
		String log = "Loans" + split
				+"del" + split
				+l.getID();
		WriteFile(log);
	}

	// Incrementa no log quantidade de livro disponivel
	public void logInc (Book b, int inc){
		String log = "Books" + split
				+"inc" + split
				+b.getID() + split
				+inc;
		WriteFile(log);
	}

	// Escreve no log
	private void WriteFile(String log){
		OpenWriter(true);

		try {
			fw.append(TimeMachine.CalendarToStr(TimeMachine.CurrentDate()));
			fw.append(split);
			fw.append(log);
			fw.append("\n");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean getFuture() { return future; }

	public static boolean canChangeData(boolean print){
		if (future && print) out.println("\nVoce esta visitando o sistema numa data antiga, neste caso apenas consultas sao permitidas.");
		return !future;
	}
}
