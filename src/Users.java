import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Users extends Database implements FileInterface {

    private List<User> users;

    public Users (String filename) {

        this.path = "users.csv";
        this.users = new LinkedList<User>();
        this.OpenFile(filename);
    }

    public void RegisterUser () {

        Scanner scan = new Scanner(System.in);

        System.out.println("Type: ");
        String Type = scan.nextLine();
        System.out.println("Name: ");
        String Name = scan.nextLine();

        this.AddUser(Type, this.nextID, Name);
        this.nextID++;
    }

    public void AddUser (String type, int ID, String name) {
        User user = null;
        if (type.equals("Tea")) {
            user = new Teacher(ID, name);

        } else if (type.equals("Stu")) {
            user = new Student(ID, name);

        } else if (type.equals("Com")) {
            user = new Comunity(ID, name);

        }

        this.users.add(user);
    }

    public void ReadFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(this.path));
        String line = null;
        String splitBy = ",";

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
    }

	public void WriteFile() throws IOException {

        for(int i = 0; i < this.users.size(); i++) {

            BufferedWriter bw = new BufferedWriter(new FileWriter(this.path));
            bw.write(this.users.get(i).Type);
            bw.write(",");
            bw.write(this.users.get(i).ID);
            bw.write(",");
            bw.write(this.users.get(i).getName());
            bw.write(",");
            bw.write(this.users.get(i).MaxLoans);
            bw.write("\n");
        }
    }
}
