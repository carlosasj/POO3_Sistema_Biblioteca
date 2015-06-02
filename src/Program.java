import Database.Source;
import Database.Users;
import Database.Books;
import Database.Loans;
import Database.History;
import Time.TimeMachine;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.out;

public class Program {
	public static void main (String[] args) throws IOException {
		TimeMachine.getInstance();

		Source src;
		try {
			src = Source.getInstance(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			src = Source.getInstance(null);
		}

		out.println("Bem-vindo ao sistema de gerenciamento de bibliotecas.");

		Scanner scan = new Scanner(System.in);
		String cmd;
		boolean endProgram = false;
		String splitSign = "/";

		while (!endProgram) {
			out.print("Digite um comando.\n(para ajuda, digite 'help'):");
			cmd = scan.nextLine();

			if (cmd.toLowerCase().equals("exit")) {
				out.println("Saindo do sistema...");
				endProgram = true;
			} else if (cmd.toLowerCase().equals("help")) {
				out.println("Comandos:");
				if (History.canChangeData()) out.println(splitSign + "add <user|book|loan>     (Adicionar)");
											 out.println(splitSign + "search <user|book|loan>  (Procurar)");
				if (History.canChangeData()) out.println(splitSign + "del <user|book>          (Excluir)");
				if (History.canChangeData()) out.println(splitSign + "return loan              (Retornar um emprestimo)");
				if (History.canChangeData()) out.println(splitSign + "inc book                 (Alterar a quantidade de exemplares de um livro)");
														out.println("help                      (Abrir esse menu de ajuda)");
														out.println("exit                      (Sair do programa)");
			} else if (!cmd.startsWith(splitSign)) {
				out.println("Comando invalido.");
			} else {
				cmd = cmd.substring(1); // Retira a barra do comando

				switch (cmd) {
					case "add user":
						if (History.canChangeData()) Users.getInstance().Register();
						break;
					case "add book":
						if (History.canChangeData()) Books.getInstance().Register();
						break;
					case "add loan":
						if (History.canChangeData()) Loans.getInstance().Register();
						break;
					case "search user":
						Users.getInstance().Search(false);
						break;
					case "search book":
						Books.getInstance().Search(false);
						break;
					case "search loan":
						Loans.getInstance().Search(false);
						break;
					case "del user":
						if (History.canChangeData()) Users.getInstance().Remove();
						break;
					case "del book":
						if (History.canChangeData()) Books.getInstance().Remove();
						break;
					case "return loan":
						if (History.canChangeData()) Loans.getInstance().Remove();
						break;
					case "inc book":
						if (History.canChangeData()) Books.getInstance().Increase();
						break;
					default:
						out.println("Comando \"" + cmd + "\" Invalido");
						break;
				}
			}
		}
		// Salvar e fechar os arquivos
		src.exit();
	}
}
