import Book.Book;
import Database.Source;
import Database.Users;
import Database.Books;
import Database.Loans;
import Time.TimeMachine;


import java.io.IOException;
import java.util.Scanner;

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

        Scanner scan = new Scanner(System.in);
        String cmd;
        boolean endProgram = false;
        String splitSign = "/";

        while (!endProgram) {
            out.println("Digite um comando: \n(para ajuda, digite 'help'):");
            cmd = scan.nextLine();

            if (cmd.toLowerCase().equals("exit")) {
                endProgram = true;
            } else if (cmd.toLowerCase().equals("help")) {
                out.println("Comandos:");
                out.println(splitSign + "add user");
                out.println(splitSign + "add book");
                out.println(splitSign + "add loan");
                out.println(splitSign + "search user");
                out.println(splitSign + "search book");
                out.println(splitSign + "search loan");
                out.println(splitSign + "del user");
                out.println(splitSign + "del book");
                out.println(splitSign + "return loan");
                out.println("exit");
            } else if (!cmd.startsWith(splitSign)) {
                out.println("Comando invalido.");
            } else {
                cmd = cmd.substring(1);
                switch (cmd) {
                    case "add user":
                        Users.getInstance().RegisterUser();
                        break;
                    case "add book":
                        Books.getInstance().RegisterBook();
                        break;
                    case "add loan":
                        Loans.getInstance().RegisterLoan();
                        break;
                    case "search user":
                        Users.getInstance().Search();
                        break;
                    case "search book":
                        Books.getInstance().Search();
                        break;
                    case "search loan":
                        Loans.getInstance().Search();
                        break;
                    case "remove user":
                        break;
                    case "remove book":
                        break;
                    case "return loan":
                        break;
                }
            }

            // Salvar e fechar os arquivos
            src.backup();
            src.CloseFile();
        }
    }
}
