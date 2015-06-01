package Database;

import Book.Book;
import Loan.Loan;
import Time.TimeMachine;
import User.User;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Loans extends Database {

	private static Loans loansDB;
	private List<Loan> loans;


	// Singleton
	public static Loans getInstance() { return loansDB; }
	protected static Loans getInstance(String filename){
		if (loansDB == null){
			loansDB = new Loans(filename);
		}
		return loansDB;
	}

	private Loans (String filename) {
		this.nextID = 0;
		this.path = "loans.csv";
		this.loans = new LinkedList<Loan>();
		this.OpenFile(filename);
		this.ReadFile();
	}

	public void RegisterLoan(){
		out.println("--- Novo Emprestimo ---");
		out.println("Primeiro, selecione o usuario.");
		User user = Users.getInstance().Search();

		// Verifica se o usuario nao esta bloqueado para emprestimo
		if (user == null || !user.canLoan()) {
			return;
		}

		out.println("Agora selecione o livro.");
		Book book = Books.getInstance().Search();

		// Verifica se o livro pode ser emprestado
		if (book == null || !book.canLoan()) {
			return;
		}

		GregorianCalendar date = TimeMachine.CurrentDate();
		GregorianCalendar expirationdate = TimeMachine.CurrentDate();
		expirationdate.add(Calendar.DAY_OF_MONTH, user.getMaxDays());

		this.AddLoan(this.nextID, book.getID(), user.getID(), date, expirationdate);
		this.nextID++;
	}

	// Utilizado no ReadFile
	private void AddLoan(int loanid, int bookid, int userid, String date, String expirationdate) {
		GregorianCalendar cal_date = TimeMachine.strToCalendar(date);
		GregorianCalendar cal_expiration = TimeMachine.strToCalendar(expirationdate);
		this.AddLoan(loanid, bookid, userid, cal_date, cal_expiration);
	}

	private void AddLoan(int loanid, int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {
		Loan l = new Loan(loanid, bookid, userid, date, expirationdate);
		this.loans.add(l);
		Books.getInstance().FindByID(bookid).goLoan();
	}

	public long CountLoansUser(int userId) {

		Stream<Loan> filter = loans.stream();

		//Busca todos os emprestimos de um usuário
		filter.filter(l -> l.getUserID() == userId);

		return filter.count();
	}

	public void ReturnLoan() {
        Loan l = this.Search();
        if (l.getExpirationDate().compareTo(TimeMachine.CurrentDate()) < 0) {
            User u = Users.getInstance().FindByID(l.getUserID());
            u.setAllowedAt(l.getExpirationDate().compareTo(TimeMachine.CurrentDate()));
            out.println("Usuario bloqueado por atraso");
        }

        loans.remove(l);
	}

	public void ReadFile(){

		this.OpenReader();

		String line;
		String splitBy = ",";

		try {
			if ((line = br.readLine()) != null) {
				this.nextID = Integer.parseInt(line);
				br.readLine();
			}

			while ((line = br.readLine()) != null) {

				String[] loanData = line.split(splitBy);
				int id = Integer.parseInt(loanData[0]);
				int bookid = Integer.parseInt(loanData[1]);
				int userid = Integer.parseInt(loanData[2]);
				String date = loanData[3];
				String expirationdate = loanData[4];

				this.AddLoan(id, bookid, userid, date, expirationdate);
			}
		} catch (IOException e){
			out.println("Erro na leitura do arquivo.");
			e.printStackTrace();
		}
	}

	public void WriteFile(){
		OpenWriter();
		String SEPARATOR = ",";
		String ENDLINE = "\n";
		String HEADER = "ID,BookID,UserID,Date,ExpirarionDate";

		try {
			fw.append(Integer.valueOf(this.nextID).toString());
			fw.append(ENDLINE);
			fw.flush();

			fw.append(HEADER);
			fw.append(ENDLINE);
			fw.flush();

			for (Loan l : loans) {
				fw.append(Integer.valueOf(l.getID()).toString());
				fw.append(SEPARATOR);

				fw.append(Integer.valueOf(l.getBookID()).toString());
				fw.append(SEPARATOR);

				fw.append(Integer.valueOf(l.getUserID()).toString());
				fw.append(SEPARATOR);

				fw.append(l.getDate().toString());
				fw.append(SEPARATOR);

				fw.append(l.getExpirationDate().toString());
				fw.append(ENDLINE);
				fw.flush();
			}
		} catch (IOException e){
			out.println("Erro na escrita do arquivo.");
			e.printStackTrace();
		}
	}

	public Loan Search(){
		Scanner scan = new Scanner(System.in);
		Boolean endSearch = false;
		Loan result = null;
		String splitSign = "/";

		while (!endSearch) {
			out.print("\nPesquise por um emprestimo\n(para ajuda, digite 'help'):\t");
			String input = scan.nextLine();

			// ----- Saida -----
			if (input.toLowerCase().equals("exit") || input.toLowerCase().equals("\'exit\'")){	// Nunca confie na inteligencia do usuario
				out.println("Encerrando a busca.");
				result = null;
				endSearch = true;
			}
			// ----- Ajuda -----
			else if (input.toLowerCase().equals("help") || input.toLowerCase().equals("\'help\'")) {	// Nunca confie na inteligencia do usuario
				out.println("Para pesquisar voce pode usar alguns comandos:");
				out.println(splitSign + "id <id do emprestimo>");
				out.println(splitSign + "book <type|> <id do emprestimo>");
				out.println(splitSign + "userid <id do emprestimo>");
				out.println(splitSign + "date <data do emprestimo no formato DD/MM/AAAA>");
				out.println(splitSign + "expiration <data maxima de devolucao no formato DD/MM/AAAA>");
				out.println(splitSign + "loaned today");
				out.println(splitSign + "author <nome do autor>");
				out.println(splitSign + "year <ano de publicacao>");
				out.println("\nExceto pelo campo " + splitSign + "id, todos os outros podem ser encadeados, por exemplo:");
				out.println("\n\t" + splitSign + "type text " + splitSign + "title Aprenda " + splitSign + "title Programar " + splitSign + "author Deitel\n");
				out.println("Ele vai procurar por um livro-texto que tenha \'Aprenda\' e \'Programar\'");
				out.println("no titulo, e tem \'Deitel\' como autor.");
				out.println("\nUse 'help' para ver este texto de ajuda.");
				out.println("Use 'exit' para encerrar a busca sem retornar nada.\n");
			}
			// ----- Comando Invalido -----
			else if (!input.startsWith(splitSign)){
				out.println("Comando invalido.");
			}
			// ----- Comando Valido -----
			else {
				input = input.substring(1);					// Retira a primeira barra da String
				String[] splited = input.split(splitSign);	// Separa os comandos
				Stream<Loan> filtered = loans.stream();		// Cria um Stream

				out.print("Filtrando por:");

				for (String cmd : splited){						// Para cada comando...
					try {
						String[] command = cmd.split(" ", 2);	// Separa o comando do parametro
						command[1] = command[1].trim();			// Retira espa�os antes e depois
						filtered = this.Filter(command[0], command[1], filtered, true);	// Filtra
					} catch (ArrayIndexOutOfBoundsException e){
						out.printf("\n\t! (Comando \"%s\" faltando argumentos; Ignorado)\n", cmd);
					}
				}

				// Transforma em uma lista
				List<Loan> collect = filtered.collect(Collectors.toList());

				if (collect.size() == 1){	// Se soh encontrou 1 resultado...
					out.println("Livro encontrado:");
					collect.get(0).Print();

					out.print("\nDeseja usar esse livro? [s|n]");
					if (scan.nextLine().toLowerCase().equals("s")){
						result = collect.get(0);
						endSearch = true;
					}
					else {
						out.println("Entao faca uma nova pesquisa.");
					}
				}

				else {	// Se encontrar mais resultados...
					out.println("Livros encontrados:");
					out.println("==================================================");
					int subID = 1;

					for (Loan l : collect){ // Imprime os livros
						out.println("< " + subID + " >");
						l.Print();
						out.println("==================================================");
						subID++;
					}
					subID--; // Porque ele termina o For valendo (collect.size()+1)

					out.print("Selecione o resultado pelo indice\nou digite 0 para uma nova busca: ");
					int index = Integer.parseInt(scan.nextLine());
					while (index > subID || index < 0){
						out.print("Opcao invalida.\nDigite o indice ou 0 para uma nova busca: ");
						index = Integer.parseInt(scan.nextLine());
					}

					if (index != 0) {
						result = collect.get(index-1);
						endSearch = true;
					}
				}
			}
		}

		return result;

	}

	public Loan FindByID(int id){
		Stream<Loan> filtered = this.Filter("id", Integer.valueOf(id).toString(), false);
		return filtered.collect(Collectors.toList()).get(0);
	}

	public Stream<Loan> Filter(String field, String param, Boolean printMsg) {	// Aplica o filtro num stream com todos os livros
		Stream<Loan> filtered = loans.stream();
		this.Filter(field, param, filtered, printMsg);
		return filtered;
	}

	public Stream<Loan> Filter(String field, String param, Stream<Loan> filtered, Boolean printMsg) {	// Aplica o filtro num stream personalizado
		if (printMsg) out.printf("\n\t%s = %s", field, param);

		switch (field){
			case "id":
				filtered.anyMatch(l -> l.getID() == Integer.parseInt(param));
				break;
			case "book":
				/*switch (param.toLowerCase()) {
					case "text":
						filtered = filtered.filter((b -> Books.getInstance().Filter("type", "text", false)))
				}*/
				filtered.filter(l -> l.getBookID() == Integer.parseInt(param));
				break;
			case "userid":
				filtered = filtered.filter(l -> l.getUserID() == Integer.parseInt(param));
				break;
			case "data":
				GregorianCalendar date = TimeMachine.strToCalendar(param);
				filtered.filter(l -> l.getDate().equals(date));
				break;
			case "expiration":
				GregorianCalendar expdate = TimeMachine.strToCalendar(param);
				filtered.filter(l -> l.getExpirationDate().equals(expdate));
				break;
			case "loaned today":
				filtered.filter(l -> l.getDate().equals(TimeMachine.CurrentDate()));
				break;
			default:
				if (printMsg) out.print(" (Comando Invalido; Ignorado)");
				break;
		}
		return filtered;
	}


}

