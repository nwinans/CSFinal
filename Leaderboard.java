import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.EOFException;

public class Leaderboard {

	//array list of top scores
	private ArrayList<User> records;
	
	public Leaderboard() {
		//initialize the arraylist
		records = new ArrayList<User>();
		
		try {
			//create the input stream to load the leaderboards into the arraylist
      	ObjectInputStream in = new ObjectInputStream(new FileInputStream("leaderboard.dat"));
			//flag to see when to stop loading users
			boolean check = true;
			//run the while loop until we reach the end of the file
			while (check) {
				try {	
					//get the next user from the file 
         		User user = (User) in.readObject();
					//add the user to the leaderboard arraylist
					records.add(user);
				} catch (EOFException ex) {
					//stop running the while loop when we reach the end of the users
					check = false;
				}
			}
			//close the object stream
         in.close();
      } catch (Exception e) {
        	e.printStackTrace();
      }
	}
	//try to add a user to the leaderboards - returns the place where they were inserted
	public int addUser(User u) {
		for (int i = 0; i < records.size(); i++) {
			//get the user in the position we watn to check against
			User user = records.get(i);
			//if the new user's score is higher than the old one, insert the new one before the old one and return the position
			if (user.getScore() <= u.getScore()) {
				records.add(i, u);
				//we only want a max of 10 entries
				if (records.size() > 10) {
					records.remove(10);
				}
				//save the new leaderboard back into the file - this only runs when the leaderboard has changed
				save();
				return i + 1;
			}
		}
		//if there aren't 10 records, the user is automatically on the list
		if (records.size() < 10) {
			records.add(u);
			save();
			return records.size();
		}
		
		//the User u's score isn't higher than any previously acheived scores, so we say they weren't good enough (return 0)
		return 0;
	}
	
	//return the arraylist of the users - used when displaying the leaderboard
	public ArrayList<User> getLeaderboard() {
		return records;
	}
	
	//save the leaderboard back into the file
	public void save() {
		try {
			//create an output stream that will be used to save the records to the leaderboard.dat file
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
			//loop through every user saving it to the file
			for (User user : records) {
   			out.writeObject(user);
			}
			//close the output stream
   		out.flush();
   		out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}