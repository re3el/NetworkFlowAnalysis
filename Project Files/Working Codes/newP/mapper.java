import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class mapper extends Mapper<LongWritable, Text, Text, IntWritable> 
{

     private final static IntWritable five = new IntWritable(5);
     private final static IntWritable six = new IntWritable(6);
     private final static IntWritable seven = new IntWritable(7);
     private final static IntWritable eight = new IntWritable(8);
     private final static IntWritable nine= new IntWritable(9);
     
     private Text counter1,counter2,counter3,counter4,counter5 ;
     
     ArrayList<String> lines = new ArrayList<String>();
     String str;
     BufferedReader br,in;
     int ddos_line = 0;	
     int normal_line = 0,total_ddos_records=1000;

      @Override
      protected void setup(Context context) throws IOException, InterruptedException 
      { 
    	  BufferedReader in = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/n100kb"));
    	  //  /home/kishorer747/Desktop/hadoop/inputs/n100mb /home/kishorer747/Desktop/out
    	  //BufferedReader in = new BufferedReader(new FileReader("./training"));
          while((str = in.readLine()) != null)
          {
        	  lines.add(str);
          }
          in.close();
      }
      
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
	{
		
		String line1 = value.toString();
        ddos_line++;
        normal_line = 0;

        double[] count = {-1, -1, -1, -1, -1};
        int[] lineIndex = {0, 0, 0, 0, 0};

        String[] parts = line1.split("\\|");
        String[] linesArray = lines.toArray(new String[lines.size()]);	

		boolean bool = true;
		int t1=0;
		double sum=0;
		while (bool) 
		{
			for(int i=0; i<5;i++)
			{
					if(bool==false) break;
	   				sum = 0;
	   				String[] parts2 = linesArray[normal_line].split("\\|");

	   				for(int x=0;x<parts.length;x++)
		   	            {
								if(parts[x].equals(parts2[x]))
								{
									t1 = 1;
								}
								else t1 = 0;
		   	            		sum += t1;
		   	            }
						
	   	            	sum = Math.sqrt(sum);
							
						if(count[4] <= sum)
						{
							count[4] = sum;
							lineIndex[4]=normal_line;
						} 
	   	            	
							
						
						for(int k=0;k<5;k++)
						{
							for(int j=0;j<4;j++)
							{	
								if(count[j] < count[j+1]) 
								{
									double temp2 = count[j+1];
									count[j+1] = count[j];
									count[j] = temp2;
	
									int temp3 = lineIndex[j+1];
									lineIndex[j+1] = lineIndex[j];
									lineIndex[j] = temp3;
								}
							 }
						 }

	   				//System.out.println(ddos_line + "   " + normal_line);
					if (normal_line + 1 < linesArray.length)
					{
						normal_line++;
						continue;
					} 
					
					else bool = false;
				}
				
			
		} // while end
        
		
		for(int i=0;i<5;i++)
		{
			lineIndex[i] += 1;
		}
		char[] t = {'d','d','d','d','d'};
		for(int i=0;i<5;i++)
		{
			if(lineIndex[i] <= total_ddos_records/2 ) t[i] = 'n'; 
		}
		counter1 = new Text(ddos_line + " : " +lineIndex[0] + " : " + t[0] + " : " + count[0]);
		counter2 = new Text(ddos_line + " : " +lineIndex[1] + " : " + t[1] + " : " + count[1]);
		counter3 = new Text(ddos_line + " : " +lineIndex[2] + " : " + t[2] + " : " + count[2]);
		counter4 = new Text(ddos_line + " : " +lineIndex[3] + " : " + t[3] + " : " + count[3]);
		counter5 = new Text(ddos_line + " : " +lineIndex[4] + " : " + t[4] + " : " + count[4]);
		

		context.write(counter1, five);
		context.write(counter2, six);
		context.write(counter3, seven);
		context.write(counter4, eight);
		context.write(counter5, nine);
        
   }
	public void run(Context context) throws IOException, InterruptedException 
	{
		setup(context);
		while (context.nextKeyValue()) {
			map(context.getCurrentKey(), context.getCurrentValue(), context);
		}
		cleanup(context);
	}

}