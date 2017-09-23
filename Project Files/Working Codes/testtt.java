import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class testtt {

	public static void main(String args[]) throws IOException
	{
		 BufferedReader in = new BufferedReader(new FileReader("/home/kishorer747/Desktop/normal_small"));
		    String str=null;
		    ArrayList<String> lines = new ArrayList<String>();
		    while((str = in.readLine()) != null){
		        lines.add(str);
		    }
		    in.close();
		    String[] linesArray = lines.toArray(new String[lines.size()]);
		    
		    for(int i=0; i< linesArray.length;i++)
		    {
		    	System.out.println(linesArray[i]);
		    }
	}
}
