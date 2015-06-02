package Database;

import Book.Book;
import Book.General;
import Book.Text;
import Loan.Loan;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Books {

	private static Books booksDB;
	private List<Book> books;
	private int nextID;

	// Singleton
	public static Books getInstance() { return booksDB; }
	protected static Books getInstance(int next){
		if (booksDB == null){
			booksDB = new Books(next);
		}
		return booksDB;
	}

	private Books (int next){
		nextID = next;
		books = new LinkedList<Book>();
	}

	protected int getNextID() { return nextID; }

	// Repassa os parametros para o Load e escreve no Log
	protected void Add(String type, int id, String title, String author, String editor, int year, int totalquantity){
		Book book = Load(type, id, title, author, editor, year, totalquantity);
		History.getInstance().logAdd(book);
	}

	// Cria um livro na lista
	protected Book Load(String type, int id, String title, String author, String editor, int year, int totalquantity){
		Book book = null;

		switch(type)
		{
			case "Tex":
				book = new Text(id, title, author, editor, year, totalquantity);
				break;

			case "Gen":    // Se nao for "Tex", sempre sera "Gen" (verificacao feita anteriormente)
				book = new General(id, title, author, editor, year, totalquantity);
				break;
		}

		books.add(book);
		return book;
	}

	// Registra um livro pela interface
	public void Register () {

		Scanner scan = new Scanner(System.in);

		out.println("--- Novo Livro ---");
		out.print("ID:\t\t" + nextID);

		out.print("Tipo:\t");
		String type = scan.nextLine().toLowerCase();
		while (type.equals("text") == type.equals("general")){	// While (false == false)
			out.println("Tipo Invalido!");
			out.print("Tipo <text|general>:\t");
			type = scan.nextLine().toLowerCase();
		}

		out.print("Titulo:\t");
		String Title = scan.nextLine();

		out.print("Autor:\t");
		String Author = scan.nextLine();

		out.print("Editora:\t");
		String Editor = scan.nextLine();

		out.print("Ano:\t");
		int Year = -1;
		while (Year == -1) {
			try {
				Year = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e){
				out.println("Ano Invalido!");
				Year = -1;
			}
		}

		out.print("Quantidade Total:\t");
		int TotalQuantity = -1;
		while (TotalQuantity == -1) {
			try {
				TotalQuantity = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e){
				out.println("Quantidade Invalida!");
				Year = -1;
			}
		}

		out.println("Deseja inserir cadastro do livro? [s|n]");
		String confirm = scan.nextLine();

		if (confirm.toLowerCase().equals("s") || confirm.equals("")) {
			switch (type){
				case "text":
					type = "Tex";
					break;
				case "general":
					type = "Gen";
					break;
			}
			Add(type, nextID, Title, Author, Editor, Year, TotalQuantity);
			nextID++;
			out.println("Registro cadastrado com sucesso!");
		}
		else {
			out.println("Registro nao cadastrado.");
		}
	}

	// Modifica a quantia disponivel de livros
	public void Increase(){
		Book b = Search();
		Scanner scan = new Scanner(System.in);
		int number;

		out.println("Voce selecionou este livro:");
		b.Print();

		out.println("\nQuantos exemplares voce deseja adicionar?");
		out.println("(digite um numero negativo para retirar exemplares)");
		String input = scan.nextLine();
		try {
			number = Integer.parseInt(input);
		}catch (NumberFormatException e){
			out.println("Numero Invalido, digite outro:");
			input = scan.nextLine();
			number = Integer.parseInt(input);
		}

		b.increase(number, true);
		History.getInstance().logInc(b, number);
	}

	public Book Search(){ return Search(true); }

	public Book Search(boolean select){
		Scanner scan = new Scanner(System.in);
		Boolean endSearch = false;
		Book result = null;
		String splitSign = "/";

		while (!endSearch) {
			out.print("\nPesquise por um livro\n(para ajuda, digite 'help'):\t");
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
				out.println(splitSign + "id <id do livro>");
				out.println(splitSign + "type <text|general>");
				out.println(splitSign + "title <titulo do livro>");
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
				Stream<Book> filtered = books.stream();		// Cria um Stream

				out.print("Filtrando por:");

				for (String cmd : splited){					// Para cada comando...
					try {
						String[] command = cmd.split(" ", 2);	// Separa o comando do parametro
						command[1] = command[1].trim();			// Retira espacos antes e depois
						filtered = Filter(command[0], command[1], filtered, true);	// Filtra
					} catch (ArrayIndexOutOfBoundsException e){
						out.printf("\n\t! (Comando \"%s\" faltando argumentos; Ignorado)\n", cmd);
					}
				}

				// Transforma em uma lista
				List<Book> collect = filtered.collect(Collectors.toList());

				if (collect.size() == 0){
					out.println("\nNenhum resultado encontrado :(\n");
				}
				else if (collect.size() == 1){	// Se soh encontrou 1 resultado...
					out.println("Livro encontrado:");
					collect.get(0).Print();

					if (select) {
						out.print("\nDeseja usar esse livro? [s|n]");
						if (scan.nextLine().toLowerCase().equals("s")) {
							result = collect.get(0);
							endSearch = true;
						} else {
							out.println("Entao faca uma nova pesquisa.");
						}
					}
				}

				else {	// Se encontrar mais resultados...
					out.println("Livros encontrados:");
					out.println("==================================================");
					int subID = 1;

					for (Book b : collect){ // Imprime os livros
						out.println("< " + subID + " >");
						b.Print();
						out.println("==================================================");
						subID++;
					}
					subID--; // Porque ele termina o For valendo (collect.size()+1)

					if (select) {
						out.print("Selecione o resultado pelo indice\nou digite 0 para uma nova busca: ");
						int index = Integer.parseInt(scan.nextLine());
						while (index > subID || index < 0) {
							out.print("Opcao invalida.\nDigite o indice ou 0 para uma nova busca: ");
							index = Integer.parseInt(scan.nextLine());
						}

						if (index != 0) {
							result = collect.get(index - 1);
							endSearch = true;
						}
					}
				}
			}
		}

		return result;
	}

	// Encontra o livro pelo ID
	public Book FindByID(int id){
		Stream<Book> filtered = Filter("id", Integer.valueOf(id).toString(), false);
		return filtered.collect(Collectors.toList()).get(0);
	}

	// Aplica o filtro num stream com todos os livros
	public Stream<Book> Filter(String field, String param, Boolean printMsg) {
		Stream<Book> filtered = books.stream();
		Filter(field, param, filtered, printMsg);
		return filtered;
	}

	// Aplica o filtro num stream personalizado
	public Stream<Book> Filter(String field, String param, Stream<Book> filtered, Boolean printMsg) {
		if (printMsg) out.printf("\n\t%s = %s", field, param);

		switch (field){
			case "type":
				switch (param.toLowerCase()) {
					case "text":
						filtered = filtered.filter(b -> b.getType().equals("Tex"));
						break;
					case "general":
						filtered = filtered.filter(b -> b.getType().equals("Gen"));
						break;
					default:
						if (printMsg) out.printf(" (\"%s\" nao eh um parametro valido; Ignorado)", param);
						break;
				}
				break;

			case "title":
				filtered = filtered.filter(b -> b.getTitle().contains(param));
				break;

			case "author":
				filtered = filtered.filter(b -> b.getAuthor().contains(param));
				break;

			case "year":
				try {
					int year = Integer.parseInt(param);
					filtered = filtered.filter(b -> b.getYear() == year);
				} catch (NumberFormatException e){
					if (printMsg) out.printf(" (\"%s\" nao eh um ano valido; Ignorado)", param);
				}
				break;

			case "id":
				try {
					int id = Integer.parseInt(param);
					filtered = filtered.filter(b -> b.getID() == id);
				} catch (NumberFormatException e){
					if (printMsg) out.printf(" (\"%s\" nao eh um ano valido; Ignorado)", param);
				}
				break;

			default:
				if (printMsg) out.print(" (Comando Invalido; Ignorado)");
				break;
		}
		return filtered;
	}

	// Remove um livro
	public void Remove () {
		Scanner scan = new Scanner(System.in);
		out.println("Procure o livro que deseja remover: ");
		Book b = Search();

		b.Print();

		if (b.getAvaliableQuantity() < b.getTotalQuantity()) {
			out.println("Ha exemplares emprestados desse livro, e essa acao vai");
			out.println("excluir todos os emprestimos relacionados a esse livro.");
		}
		out.println("Tem certeza que deseja remover esse livro?[s/n]");
		String confirm = scan.nextLine().toLowerCase();
		if (confirm.equals("s")) {
			Del(b.getID());
			History.getInstance().logDel(b);

		}
	}

	// Deleta um livro
	protected void Del (int id){
		Book b = FindByID(id);
		Stream<Loan> stream = Loans.getInstance()
								.Filter("bookid", Integer.valueOf(b.getID()).toString(), false);

		for (Loan l : stream.collect(Collectors.toList())){
			Loans.getInstance().Del(l.getID());
		}
		books.remove(b);
	}
}
