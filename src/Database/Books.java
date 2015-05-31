package Database;

import Book.Book;
import Book.General;
import Book.Text;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Books extends Database {

    private static Books booksDB;
    private List<Book> books;

    // Singleton
    public static Books getInstance() { return booksDB; }
    protected static Books getInstance(String filename){
        if (booksDB == null){
            booksDB = new Books(filename);
        }
        return booksDB;
    }

	private Books (String filename){
        this.nextID = 0;
		this.path = "books.csv";
		this.books = new LinkedList<Book>();
		this.OpenFile(filename);
        this.ReadFile();
	}

    protected void AddBook(String type, int id, String title, String author, String editor, int year, int totalquantity, int avaliablequantity){
        Book book;
        
        if(type.equals("Tex")){
            book = new Text(id, title, author, editor, year, totalquantity, avaliablequantity);
        } else {
            book = new General(id, title, author, editor, year, totalquantity, avaliablequantity);
        }

        this.books.add(book);
    }

    public void RegisterBook () {

        Scanner scan = new Scanner(System.in);

        out.println("Cadastro de livro:");
        out.print("ID:\t\t" + this.nextID);

        out.print("Tipo:\t");
        String type = scan.nextLine().toLowerCase();
        while (!type.equals("text") && !type.equals("general")){
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

        if (confirm.toLowerCase().equals("s") || confirm.equals("\n")) {
            switch (type){
                case "text":
                    type = "Tex";
                    break;
                case "general":
                    type = "Gen";
            }
            this.AddBook(type, this.nextID, Title, Author, Editor, Year, TotalQuantity, TotalQuantity);
            this.nextID++;
            out.println("Registro cadastrado com sucesso!");
        }
        else {
            out.println("Registro nao cadastrado.");
        }
    }

	protected void ReadFile(){
        this.OpenReader();

		String line;
		String splitSign = ",";

		try {
            if ((line = br.readLine()) != null) {
                this.nextID = Integer.parseInt(line);
                br.readLine();
            }

			while ((line = br.readLine()) != null){
				String[] readed = line.split(splitSign);
                String type = readed[0];
                int id = Integer.parseInt(readed[1]);
                String title = readed[2];
                String author = readed[3];
                String editor = readed[4];
                int year = Integer.parseInt(readed[5]);
                int totalquantity = Integer.parseInt(readed[6]);
                int avaliablequantity = Integer.parseInt(readed[7]);

                this.AddBook(type, id, title, author, editor, year, totalquantity, avaliablequantity);
            }
        } catch (IOException e) {
            out.println("Erro na leitura do arquivo.");
            e.printStackTrace();
        }
	}

    public Book Search(){
        Scanner scan = new Scanner(System.in);
        Boolean endSearch = false;
        Book result = null;
        String splitSign = "/";

        while (!endSearch) {
            out.print("\nPesquise por um livro\n(para ajuda, digite 'help'):\t");
            String input = scan.nextLine();

            // ----- Saida -----
            if (input.toLowerCase().equals("exit") || input.toLowerCase().equals("\'exit\'")){  // Nunca confie na inteligencia do usuario
                out.println("Encerrando a busca.");
                result = null;
                endSearch = true;
            }
            // ----- Ajuda -----
            else if (input.toLowerCase().equals("help") || input.toLowerCase().equals("\'help\'")) {  // Nunca confie na inteligencia do usuario
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
                input = input.substring(1);                 // Retira a primeira barra da String
                String[] splited = input.split(splitSign);  // Separa os comandos
                Stream<Book> filtered = books.stream();     // Cria um Stream

                out.print("Filtrando por:");

                for (String cmd : splited){                 // Para cada comando...
                    try {
                        String[] command = cmd.split(" ", 2);   // Separa o comando do parametro
                        command[1] = command[1].trim();         // Retira espa�os antes e depois
                        filtered = this.Filter(command[0], command[1], filtered, true);    // Filtra
                    } catch (ArrayIndexOutOfBoundsException e){
                        out.printf("\n\t! (Comando \"%s\" faltando argumentos; Ignorado)\n", cmd);
                    }
                }

                // Transforma em uma lista
                List<Book> collect = filtered.collect(Collectors.toList());

                if (collect.size() == 1){   // Se soh encontrou 1 resultado...
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

                else {      // Se encontrar mais resultados...
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

    public Book FindByID(int id){
        Stream<Book> filtered = this.Filter("id", Integer.valueOf(id).toString(), false);
        return filtered.collect(Collectors.toList()).get(0);
    }

    public Stream<Book> Filter(String field, String param, Boolean printMsg) {    // Aplica o filtro num stream com todos os livros
        Stream<Book> filtered = books.stream();
        this.Filter(field, param, filtered, printMsg);
        return filtered;
    }

    public Stream<Book> Filter(String field, String param, Stream<Book> filtered, Boolean printMsg) {  // Aplica o filtro num stream personalizado
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

	protected void WriteFile() {
        OpenWriter();
        final String SEPARATOR = ",";
        final String ENDLINE = "\n";
        final String HEADER = "Type,ID,Title,Author,Editor,Year,TotalQuantity,AvaliableQuantity";

        try {
            fw.append(Integer.valueOf(this.nextID).toString());
            fw.append(ENDLINE);
            fw.flush();

            fw.append(HEADER);
            fw.append(ENDLINE);
            fw.flush();

            for (Book b : books) {
                fw.append(b.getType());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getID()).toString());
                fw.append(SEPARATOR);

                fw.append(b.getTitle());
                fw.append(SEPARATOR);

                fw.append(b.getAuthor());
                fw.append(SEPARATOR);

                fw.append(b.getEditor());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getYear()).toString());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getTotalQuantity()).toString());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(b.getAvaliableQuantity()).toString());
                fw.append(ENDLINE);

                fw.flush();
            }
        } catch (IOException e){
            out.println("Erro na escrita do arquivo.");
            e.printStackTrace();
        }
    }
}