package Database;

import User.Comunity;
import User.Student;
import User.Teacher;
import User.User;
import Time.TimeMachine;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Users extends Database {

    private static Users usersDB;
    private List<User> users;

    // Singleton
    public static Users getInstance() { return usersDB; }
    protected static Users getInstance(String filename){
        if (usersDB == null){
            usersDB = new Users(filename);
        }
        return usersDB;
    }

    public Users (String filename) {
        this.nextID = 0;
        this.path = "users.csv";
        this.users = new LinkedList<User>();
        this.OpenFile(filename);
        this.ReadFile();
    }

    public void RegisterUser () {

        Scanner scan = new Scanner(System.in);

        System.out.println("Type: ");
        String Type = scan.nextLine();
        System.out.println("Name: ");
        String Name = scan.nextLine();

        this.AddUser(Type, nextID, Name);
        this.nextID++;
    }

    protected void AddUser (String type, int ID, String name) {
        User user = null;
        switch (type) {
            case "Tea":
                user = new Teacher(ID, name, TimeMachine.CurrentDate());
                break;

            case "Stu":
                user = new Student(ID, name, TimeMachine.CurrentDate());
                break;

            case "Com":
                user = new Comunity(ID, name, TimeMachine.CurrentDate());
                break;
        }

        this.users.add(user);
    }

    protected void ReadFile() {

        this.OpenReader();

        String line;
        String splitBy = ",";

        try {
            if ((line = br.readLine()) != null) {
                this.nextID = Integer.parseInt(line);
                br.readLine();
            }

            while ((line = br.readLine()) != null) {

                String[] userData = line.split(splitBy);
                String type = userData[0];
                int id = Integer.parseInt(userData[1]);
                String name = userData[2];

                this.AddUser(type, id, name);
            }
        }catch (IOException e){
            out.println("Erro na leitura do arquivo.");
            e.printStackTrace();
        }
    }

    public User Search(){
        Scanner scan = new Scanner(System.in);
        Boolean endSearch = false;
        User result = null;
        String splitSign = "/";

        while (!endSearch) {
            out.println("\nPesquise por um usuario\n(para ajuda, digite 'help'):\t");
            String input = scan.nextLine();

            // ----- Saida -----
            if (input.toLowerCase().equals("exit") || input.toLowerCase().equals("\'exit\'")){  // Nunca confie na intelig�ncia do usu�rio
                out.println("Encerrando a busca.");
                result = null;
                endSearch = true;
            }
            // ----- Ajuda -----
            else if (input.toLowerCase().equals("help") || input.toLowerCase().equals("\'help\'")) {  // Nunca confie na intelig�ncia do usu�rio
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
                input = input.substring(1);                 // Retira a primeira barra da String
                String[] splited = input.split(splitSign);  // Separa os comandos
                Stream<User> filtered = users.stream();     // Cria um Stream

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
                List<User> collect = filtered.collect(Collectors.toList());

                if (collect.size() == 1){   // Se soh encontrou 1 resultado...
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

                else {      // Se encontrar mais resultados...
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

    public User FindByID(int id){
        Stream<User> filtered = this.Filter("id", Integer.valueOf(id).toString(), false);
        return filtered.collect(Collectors.toList()).get(0);
    }

    public Stream<User> Filter(String field, String param, Boolean printMsg) {    // Aplica o filtro num stream com todos os Usuarios
        Stream<User> filtered = users.stream();
        this.Filter(field, param, filtered, printMsg);
        return filtered;
    }

    public Stream<User> Filter(String field, String param, Stream<User> filtered, Boolean printMsg) {  // Aplica o filtro num stream personalizado
        if (printMsg) out.printf("\n\t%s = %s", field, param);

        switch (field){
            case "type":
                switch (param.toLowerCase()) {
                    case "student":
                        filtered = filtered.filter(u -> u.getType().equals("Stu"));
                        break;
                    case "teacher":
                        filtered = filtered.filter(u -> u.getType().equals("Tea"));
                        break;
                    case "community":
                        filtered = filtered.filter(u -> u.getType().equals("Com"));
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

	protected void WriteFile() {
        OpenWriter();

        final String SEPARATOR = ",";
        final String ENDLINE = "\n";
        final String HEADER = "Type,ID,Name";

        try {
            fw.append(Integer.valueOf(this.nextID).toString());
            fw.append(ENDLINE);
            fw.flush();

            fw.append(HEADER);
            fw.append(ENDLINE);
            fw.flush();

            for (User u : users) {
                fw.append(u.getType());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(u.getID()).toString());
                fw.append(SEPARATOR);

                fw.append(u.getName());
                fw.append(ENDLINE);

                fw.flush();
            }
        } catch (IOException e){
            out.println("Erro na escrita do arquivo.");
            e.printStackTrace();
        }
    }
}
