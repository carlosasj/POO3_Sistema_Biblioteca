import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Users implements Database {
	public BufferedReader br = null;
    public int ID;

    public Users () {

        List<User> users = new LinkedList<User>();
    }

	public void OpenFile(String path){
		try {
			br = new BufferedReader(new FileReader("users.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

    public void ReadFile() {

        private String line = "";
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
}
