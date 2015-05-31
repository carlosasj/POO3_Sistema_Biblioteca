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

    public Loans (String filename) {
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
        // Verifica se o usuario tem menos emprestimos do que maximo permitido

        out.println("Agora selecione o livro.");
        Book book = Books.getInstance().Search();
        // Verifica se existe o livro para ser emprestado

        GregorianCalendar date = (GregorianCalendar) TimeMachine.CurrentDate().clone();
        GregorianCalendar expirationdate = (GregorianCalendar) TimeMachine.CurrentDate().clone();
        expirationdate.add(Calendar.DAY_OF_MONTH, user.getMaxDays());

        this.AddLoan(book.getID(), user.getID(), date, expirationdate);
    }

    // Utilizado no ReadFile
    private void AddLoan(int loanid, int bookid, int userid, String date, String expirationdate) {
        String[] split_date = date.split("/");
        String[] split_expiration = expirationdate.split("/");

        GregorianCalendar cal_date =
                new GregorianCalendar(Integer.parseInt(split_date[2]),
                        Integer.parseInt(split_date[1]),
                        Integer.parseInt(split_date[0]));

        GregorianCalendar cal_expiration =
                new GregorianCalendar(Integer.parseInt(split_expiration[2]),
                        Integer.parseInt(split_expiration[1]),
                        Integer.parseInt(split_expiration[0]));

        this.AddLoan(loanid, bookid, userid, cal_date, cal_expiration);
    }

    private void AddLoan(int loanid, int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {
        Loan l = new Loan(loanid, bookid, userid, date, expirationdate);
        this.loans.add(l);
        Books.getInstance().FindByID(bookid).goLoan();
    }

    protected void AddLoan(int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {
        this.AddLoan(this.nextID, bookid, userid, date, expirationdate);
        this.nextID++;
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

    public Book Search(){
        Scanner scan = new Scanner(System.in);
        Boolean endSearch = false;
        Book result = null;
        String splitSign = "/";

        while (!endSearch) {
            out.print("\nPesquise por um emprestimo\n(para ajuda, digite 'help'):\t");
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
                input = input.substring(1);                 // Retira a primeira barra da String
                String[] splited = input.split(splitSign);  // Separa os comandos
                Stream<Book> filtered = books.stream();     // Cria um Stream

                out.print("Filtrando por:");

                for (String cmd : splited){                 // Para cada comando...
                    try {
                        String[] command = cmd.split(" ", 2);   // Separa o comando do parametro
                        command[1] = command[1].trim();         // Retira espaços antes e depois
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



}
