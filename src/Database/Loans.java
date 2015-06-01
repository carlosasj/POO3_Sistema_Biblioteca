package Database;

import Book.Book;
import Loan.Loan;
import Time.TimeMachine;
import User.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Loans {

	private static Loans loansDB;
	private List<Loan> loans;
	private int nextID;

	// Singleton
	public static Loans getInstance() { return loansDB; }
	protected static Loans getInstance(int next){
		if (loansDB == null){
			loansDB = new Loans(next);
		}
		return loansDB;
	}

	private Loans (int next) {
		nextID = next;	// Inicializa variavel do proximo ID
		loans = new LinkedList<Loan>();
	}

	protected int getNextID() { return nextID; }

	// Registra um novo emprestimo
	public void Register(){
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

		Add(nextID, book.getID(), user.getID());
		nextID++;
	}

	// Adiciona emprestimo

	// Utilizado no ReadFile
	protected void Add(int loanid, int bookid, int userid) {
		Loan l =  Load(loanid, bookid, userid);
		History.getInstance().logAdd(l);
	}

	protected Loan Load(int loanid, int bookid, int userid) {
		User u = Users.getInstance().FindByID(userid);
		GregorianCalendar cal_date = TimeMachine.CurrentDate();
		GregorianCalendar cal_expiration = TimeMachine.CurrentDate();
		cal_expiration.add(Calendar.DAY_OF_MONTH, u.getMaxDays());

		Loan l = new Loan(loanid, bookid, userid, cal_date, cal_expiration);
		Books.getInstance().FindByID(bookid).goLoan();
		loans.add(l);
		return l;
	}

	// Retorna numero de emprestimos de um usuario
	public long CountLoansUser(int userId) {

		Stream<Loan> filter = loans.stream();

		//Busca todos os emprestimos de um usuario
		filter.filter(l -> l.getUserID() == userId);

		return filter.count();
	}

	public void Remove() {
		Loan l = Search();
		Del(l.getID(), true);
		History.getInstance().logDel(l);
	}

	// Deletar Usuario ou Livro
	protected void Del(int id){
		Del(id, false);
		Loan l = FindByID(id);
		Books.getInstance().FindByID(l.getBookID()).increase(-1);
	}

	protected void Del(int id, boolean verify){
		Loan l = FindByID(id);
		if (verify && l.getExpirationDate().compareTo(TimeMachine.CurrentDate()) < 0) {
			User u = Users.getInstance().FindByID(l.getUserID());
			u.setAllowedAt(l.getExpirationDate().compareTo(TimeMachine.CurrentDate()));
			out.println("Usuario bloqueado por atraso");
		}
		Books.getInstance().FindByID(l.getBookID()).backLoan();
		loans.remove(l);
	}

	// Busca emprestimo com interface com o usuário
	public Loan Search() { return Search(false); }
	public Loan Search(boolean select){
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
				out.println(splitSign + "bookid <id do livro>");
				out.println(splitSign + "userid <id do usuario>");
				out.println(splitSign + "date <data do emprestimo no formato DD/MM/AAAA>");
				out.println(splitSign + "expiration <data maxima de devolucao no formato DD/MM/AAAA>");
				out.println(splitSign + "loaned (todos os emprestimos nao devolvidos)");
				out.println(splitSign + "booksearch (abre a pesquisa por livros, com seus próprios comandos)");
				out.println(splitSign + "usersearch (abre a pesquisa por usuarios, com seus próprios comandos)");
				out.println("\nExceto pelo campo " + splitSign + "id, todos os outros podem ser encadeados, por exemplo:");
				out.println("\n\t" + splitSign + "loaned " + splitSign + "booksearch\n");
				out.println("Ele vai abrir a pesquisa por livros, para você encontrar exatamente o livro,");
				out.println("em seguida ele vai procurar todos os emprestimos daquele livro");
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
					String[] command = cmd.split(" ", 2);		// Separa o comando do parametro
					try {
						command[1] = command[1].trim();			// Retira espacos antes e depois
						filtered = Filter(command[0], command[1], filtered, true);	// Filtra
					} catch (ArrayIndexOutOfBoundsException e){
						filtered = Filter(command[0], filtered, true);	// Filtra
					}
				}

				// Transforma em uma lista
				List<Loan> collect = filtered.collect(Collectors.toList());

				if (collect.size() == 1){	// Se encontrou apenas 1 resultado...
					out.println("Emprestimo encontrado:");
					collect.get(0).Print();

					out.print("\nDeseja usar esse emprestimo? [s|n]");
					if (scan.nextLine().toLowerCase().equals("s")){
						result = collect.get(0);
						endSearch = true;
					}
					else {
						out.println("Entao faca uma nova pesquisa.");
					}
				}

				else {	// Se encontrar mais resultados...
					out.println("Emprestimos encontrados:");
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

	// Busca emprestimo pelo ID fornecido
	public Loan FindByID(int id){
		Stream<Loan> filtered = Filter("id", Integer.valueOf(id).toString(), false);
		return filtered.collect(Collectors.toList()).get(0);
	}

	// Aplica o filtro num stream com todos os emprestimos
	public Stream<Loan> Filter(String field, String param, Boolean printMsg) {
		Stream<Loan> filtered = loans.stream();
		Filter(field, param, filtered, printMsg);
		return filtered;
	}

	// Aplica o filtro num stream personalizado
	public Stream<Loan> Filter(String field, String param, Stream<Loan> filtered, Boolean printMsg) {
		if (printMsg) out.printf("\n\t%s = %s", field, param);

		switch (field){
			case "id":
				filtered.anyMatch(l -> l.getID() == Integer.parseInt(param));
				break;
			case "bookid":
				filtered.filter(l -> l.getBookID() == Integer.parseInt(param));
				break;
			case "userid":
				filtered = filtered.filter(l -> l.getUserID() == Integer.parseInt(param));
				break;
			case "date":
				GregorianCalendar date = TimeMachine.strToCalendar(param);
				filtered.filter(l -> l.getDate().equals(date));
				break;
			case "expiration":
				GregorianCalendar expdate = TimeMachine.strToCalendar(param);
				filtered.filter(l -> l.getExpirationDate().equals(expdate));
				break;
			default:
				if (printMsg) out.print(" (Comando Invalido; Ignorado)");
				break;
		}
		return filtered;
	}

	public Stream<Loan> Filter(String field, Stream<Loan> filtered, Boolean printMsg) {
		switch (field) {
			case "loaned today":
				filtered.filter(l -> l.getDate().equals(TimeMachine.CurrentDate()));
				break;
			case "booksearch":
				Book b = Books.getInstance().Search();
				if (b != null) filtered = Filter("bookid", Integer.valueOf(b.getID()).toString(), filtered, true);
				break;
			case "usersearch":
				User u = Users.getInstance().Search();
				if (u != null) filtered = Filter("userid", Integer.valueOf(u.getID()).toString(), filtered, true);
				break;
			default:
				if (printMsg) out.print(" (Comando Invalido; Ignorado)");
				break;
		}
		return filtered;
	}
}

