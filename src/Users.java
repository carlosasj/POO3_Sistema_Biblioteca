import java.util.LinkedList;
import java.util.List;

public class Users extends Database implements FileInterface {

    public int ID;
    private List<User> users;

    public Users (String filename) {

        this.path = "users.csv";
        this.users = new LinkedList<User>();
        this.OpenFile(filename);
    }

    public void AddUser (String type, int ID, String name) {
        User user;
        switch (type) {
            case "Tea":
                user = new Teacher(ID, name);
                break;
            case "Stu":
                user = new Student(ID, name);
                break;
            case "Com":
                user = new Comunity(ID, name);
                break;
        }
        this.users.add(user);
    }

    public void ReadFile() {

        private String line = null;
        private String splitBy = ",";

        while ((line = br.readLine()) != null) {
            
            String[] userData = line.split(splitBy);
            this.AddUser(userData[0], Integer.parseInt(userData[1], userData[2]));

        }

    }

	public void WriteFile() {}
}
