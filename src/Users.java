import java.util.LinkedList;
import java.util.List;

public class Users extends Database implements FileInterface {
    public int ID;
	private List<User> users;

    public Users () {
		this.path = "users.csv";
        users = new LinkedList<User>();
    }

    public void ReadFile() {

        private String line = null;
        private String splitBy = ",";

        while ((line = br.readLine()) != null) {
            String[] userData = line.split(splitBy);

            User user;
            switch (userData[0]) {
                case "Tea":
                    user = new Teacher(Integer.parseInt(userData[1]), userData[2]);
                    break;
                case "Stu":
                    user = new Student(Integer.parseInt(userData[1]), userData[2]);
                    break;
                case "Com":
                    user = new Comunity(Integer.parseInt(userData[1]), userData[2]);
                    break;
            }
            this.users.add(user);
        }

    }

	public void WriteFile() {}
}
