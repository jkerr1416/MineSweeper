package first;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class HighScores implements Serializable{
	
	private String[] names;
	private int [] times;
	
	public HighScores() {
		
		names = new String[3];
		Arrays.fill(names, "");
		times = new int[3];
		Arrays.fill(times, 999);
	}
	
	public String[] getNames(){
		return names;
	}
	
	public int[] getTimes() {
		return times;
	}

	public boolean newScore(String x, int y) {		//returns true if its a new high score
		
		boolean temp = false;
		
		if(y < times[0]) {
			times[2] = times[1];
			times[1] = times[0];
			times[0] = y;
			names[2] = names[1];
			names[1] = names[0];
			names[0] = x;
			temp = true;
		}
		else if(y < times[1]) {
			
			times[2] = times[1];
			times[1] = y;
			names[2] = names[1];
			names[1] = x;
			temp = true;
		}
		else if(y < times[2]) {
			times[2] = y;
			names[2] = x;
			temp = true;
		}
		if(temp == true) {	//serialize save
			
			saveScores();
			
		}
		return temp;
	}
	
	
	public void saveScores() {
		
//		private String[] names;
//		private int [] times;
		
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut = null;
		
		try {
			
			fileOut = new FileOutputStream("HighScores.ser");
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(this);
			objOut.close();
			fileOut.close();
			
		}
		
		catch(IOException i){
			
			i.printStackTrace();
			
		}
		
	}
	
	
	public static HighScores loadData()
	{	
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		HighScores HS=null;
			
		try
		{
			fileIn = new FileInputStream("HighScores.ser");
			objIn = new ObjectInputStream(fileIn);
			HS = (HighScores) objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		catch(IOException i)
		{
			i.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}  
		return HS;
	}	 
	
	
}
