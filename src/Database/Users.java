package Database;

import Loan.Loan;
import User.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Users {

	private static Users usersDB;
	private List<User> users;
	private int nextID;

	// Singleton
	public static Users getInstance() { return usersDB; }
	protected static Users getInstance(int next){
		if (usersDB == null){
			usersDB = new Users(next);
		}
		return usersDB;
	}

	private Users (int next) {
		nextID = next;
		users = new LinkedList<User>();
	}

	protected int getNextID() { return nextID; }

	// Registra um usuario pela interface
	public void Register () {

		Scanner scan = new Scanner(System.in);

		out.print("Tipo:\t");
		String type = scan.nextLine().toLowerCase();
		while (!type.equals("community") && !type.equals("student") && !type.equals("teacher")){
			out.println("Tipo Invalido!");
			out.print("Tipo <community|student|teacher>:\t");
			type = scan.nextLine().toLowerCase();
		}
		out.println("Nome: ");
		String Name = scan.nextLine();

		out.println("Deseja inserir cadastro do usuario? [s|n]");
		String confirm = scan.nextLine();

		if (confirm.toLowerCase().equals("s") || confirm.equals("\n")) {
			Add(type, nextID, Name);
			nextID++;
			out.println("Usuario cadastrado com sucesso!");
		}
		else {
			out.println("Usuario nao cadastrado.");
		}
	}

	// Repassa os parametros para o Load e escreve no Log
	protected void Add (String type, int ID, String name) {
		User user = Load(type, ID, name);
		History.getInstance().logAdd(user);
	}

	// Cria um usuario na lista
	protected User Load (String type, int ID, String name) {
		User user = null;
		switch (type) {
			case "community":
				user = new Community(ID, name);
				break;

			case "student":
				user = new Student(ID, name);
				break;

			case "teacher":
				user = new Teacher(ID, name);
				break;
		}

		users.add(user);
		return user;
	}

	public User Search(){ return Search(false); }

	public User Search(boolean select){
		Scanner scan = new Scanner(System.in);
		Boolean endSearch = false;
		User result = null;
		String splitSign = "/";

		while (!endSearch) {
			out.println("\nPesquise por um usuario\n(para ajuda, digite 'help'):\t");
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
				out.println(splitSign + "id <id do usuario>");
				out.println(splitSign + "type <student|teacher|community>");
				out.println(splitSign + "name <nome do usuario>");
				out.println("\nExceto pelo campo " + splitSign + "id, os outros campos podem ser encadeados, por exemplo:");
				out.println("\n\t" + splitSign + "type teacher " + splitSign + "name Adenilso " + splitSign + "name Simao\n");
				out.println("Ele vai procurar por um professor que tenha \'Adenilso\' e \'Simao\' no nome");
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
				Stream<User> filtered = users.stream();		// Cria um Stream

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
				List<User> collect = filtered.collect(Collectors.toList());

				if (collect.size() == 1){	// Se soh encontrou 1 resultado...
					out.println("Usuario encontrado:");
					collect.get(0).Print();

					out.print("\nDeseja selecionar esse usuario? [s|n]");
					if (scan.nextLine().toLowerCase().equals("s")){
						result = collect.get(0);
						endSearch = true;
					}
					else {
						out.println("Entao faca uma nova pesquisa.");
					}
				}

				else {		// Se encontrar mais resultados...
					out.println("Usuarios encontrados:");
					out.println("==================================================");
					int subID = 1;

					for (User b : collect){ // Imprime os livros
						out.println("< " + subID + " >");
						b.Print();
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

	// Encontra um usuario com o ID fornecido
	public User FindByID(int id){
		Stream<User> filtered = Filter("id", Integer.valueOf(id).toString(), false);
		return filtered.collect(Collectors.toList()).get(0);
	}

	// Aplica o filtro num stream com todos os Usuarios
	public Stream<User> Filter(String field, String param, Boolean printMsg) {
		Stream<User> filtered = users.stream();
		Filter(field, param, filtered, printMsg);
		return filtered;
	}

	// Aplica o filtro num stream personalizado
	public Stream<User> Filter(String field, String param, Stream<User> filtered, Boolean printMsg) {
		if (printMsg) out.printf("\n\t%s = %s", field, param);

		switch (field){
			case "type":
				switch (param.toLowerCase()) {
					case "student":
						filtered = filtered.filter(u -> u.getType().equals("student"));
						break;
					case "teacher":
						filtered = filtered.filter(u -> u.getType().equals("teacher"));
						break;
					case "community":
						filtered = filtered.filter(u -> u.getType().equals("community"));
					default:
						if (printMsg) out.printf(" (\"%s\" nao eh um parametro valido; Ignorado)", param);
						break;
				}
				break;

			case "name":
				filtered = filtered.filter(u -> u.getName().contains(param));
				break;

			case "id":
				try {
					int id = Integer.parseInt(param);
					filtered = filtered.filter(u -> u.getID() == id);
				} catch (NumberFormatException e){
					if (printMsg) out.printf(" (\"%s\" nao eh um ID valido; Ignorado)", param);
				}
				break;

			default:
				if (printMsg) out.print(" (Comando Invalido; Ignorado)");
				break;
		}
		return filtered;
	}

	// Remove um usuario
	public void Remove () {
		Scanner scan = new Scanner(System.in);
		User u = Search();
		u.Print();

		if (Loans.getInstance().CountLoansUser(u.getID()) > 0){
			out.println("Esse usuario tem livros nao revolvidos, e essa acao vai");
			out.println("excluir todos os emprestimos relacionados a esse usuario,");
			out.println("e vai reduzir a Quantidade Total dos livros que estao com ele.");
		}
		out.println("Tem certeza que deseja remover esse usuario?[s/n]");
		String confirm = scan.nextLine().toLowerCase();
		if (confirm.equals("s")) {
			Del(u.getID());
			History.getInstance().logDel(u);
		}
	}

	// Deleta um usuario
	protected void Del(int id){
		User u = FindByID(id);
		Stream<Loan> stream = Loans.getInstance()
								.Filter("userid", Integer.valueOf(u.getID()).toString(), false);
		for (Loan l : stream.collect(Collectors.toList())){
			Loans.getInstance().Del(l.getID());
		}
		users.remove(id);
	}
}
