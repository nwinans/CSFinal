import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.EOFException;

public class Leaderboard {

	private ArrayList<User> records;
	
	public Leaderboard() {
	
		records = new ArrayList<User>();
		try {
      	ObjectInputStream in = new ObjectInputStream(new FileInputStream("leaderboard.dat"));
			
			
			
			boolean check = true;
			while (check) {
				try {
         		User user = (User) in.readObject();
					records.add(user);
				} catch (EOFException ex) {
					check = false;
				}
			}
         in.close();
      } catch (Exception e) {
        	e.printStackTrace();
      }
	}
	
	public int addUser(User u) {
		for (int i = 0; i < records.size(); i++) {
			User user = records.get(i);
			if (user.getScore() <= u.getScore()) {
				records.add(i, u);
				if (records.size() > 10) {
					records.remove(10);
				}
				save();
				return i + 1;
			}
		}
		if (records.size() < 10) {
			records.add(u);
			save();
		}
		
		//the User u's score isn't higher than any previously acheived scores, so we say they weren't good enough (return 0)
		return 0;
	}
	
	public ArrayList<User> getLeaderboard() {
		return records;
	}
	
	public void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
			for (User user : records) {
   			out.writeObject(user);
			}
   		out.flush();
   		out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}