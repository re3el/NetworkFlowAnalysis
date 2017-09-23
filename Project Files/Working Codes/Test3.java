import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class Test3
{
      
	public static void main(String args[]) throws IOException
	{
		long start = System.currentTimeMillis();
	
		String srcIP,srcIPN,dstIP,dstIPN,srcPort,dstPort,srcPortN,dstPortN;
	    String counter1,counter2,counter3,counter4,counter5,str,str2;
	    BufferedReader br,in;
	    int ddos_line = 0;	
	    int normal_line = 0;
	    
	    ArrayList<String> lines1 = new ArrayList<String>();
	    ArrayList<String> lines2 = new ArrayList<String>();

	    //map method that performs the tokenizer job and framing the initial key value pairs
	    for(int s = 1; s<=3; s++)
	    {
			
			in = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/normal"+s));
			br = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/normal_small"));

	   while((str = in.readLine()) != null){
	 	  lines1.add(str);
	   }
	   in.close();
	   
	   while((str2 = br.readLine()) != null){
	   	  lines2.add(str2);
	     }
	     br.close();

	   String[] linesArray1 = lines1.toArray(new String[lines1.size()]);
	   int z = 0;
	   while(z<linesArray1.length)
	   {
		   ddos_line++;
		   normal_line = 0;
		   
		   int[] count = {100000, 100000, 100000, 100000, 100000};
	       int[] lineIndex = {0, 0, 0, 0, 0};
		   
	       String[] parts = linesArray1[z].split("\\|");
	       srcIP =  parts[0];
	       dstIP = parts[1];
	       srcPort = parts[9];
	       dstPort = parts[10];

	       //System.out.println("srcPort :  "+ srcPort + "     dstPort : " + dstPort);
	       //System.out.println("ddos line 1:  " + linesArray1[0]);
	       
	       String[] linesArray2 = lines2.toArray(new String[lines2.size()]);
	       boolean bool = true;
	       
	       int t1=0;
	       //double sum=0;
	       
	       
	       while (bool) 
			{
				for(int i=0; i<5;i++)
				{
						if(bool==false) break;
		   				//sum = 0;
		   				String[] parts2 = linesArray2[normal_line].split("\\|");
		   				srcIPN =  parts2[0];
		   	            dstIPN = parts2[1];
		   	            srcPortN = parts2[9];
		   	            dstPortN = parts2[10];
		   	            //System.out.println("srcPortN :  "+ srcPortN + "     dstPortN : " + dstPortN);
		   	            //System.out.println("normal line 2:  " + linesArray2[0]);
		   	            if(srcIP.equals(srcIPN) && dstIP.equals(dstIPN))
						{
							/*
		   	            	for(int x=3;x<parts.length;x++)
			   	            {
									t1 = Math.abs(Integer.parseInt(parts[x]) - Integer.parseInt(parts2[x]));
									//System.out.println("t1: "+t1);
			   	            		t1 *= t1;
			   	            		sum += t1;
			   	            }
							
							//System.out.println(sum);
							sum = Math.sqrt(sum);
							//System.out.println("gghdfd: "+sum);
								
							if(count[4] > sum)
							{
								count[4] = (int) sum;
								lineIndex[4]=normal_line;
							} 
								
							*/
							int tmp=0,tmp2=0,temp=0;
							tmp = Math.abs(Integer.parseInt(srcPort) - Integer.parseInt(srcPortN));
							tmp2 = Math.abs (Integer.parseInt(dstPort) - Integer.parseInt(dstPortN));

							temp=tmp+tmp2;
							//System.out.println("temp"+temp);
							if(count[4] > temp)
							{
								//count[4] = (int) sum;
								count[4] = temp;
								lineIndex[4]=normal_line;
							} 
							
							for(int k=0;k<5;k++)
							{
								for(int j=0;j<4;j++)
								{	
									if(count[j] > count[j+1]) 
									{
										int temp2 = count[j+1];
										count[j+1] = count[j];
										count[j] = temp2;
		
										int temp3 = lineIndex[j+1];
										lineIndex[j+1] = lineIndex[j];
										lineIndex[j] = temp3;
									}
								 }
							 }
	
		
						}
		   				//System.out.println(ddos_line + "   " + normal_line);
						if (normal_line + 1 < linesArray2.length)
						{
							normal_line++;
							continue;
						} 
						
						else bool = false;
					}
					
					for(int i=0;i<5;i++)
					{
						lineIndex[i] += 1;
					}

				counter1 = ddos_line + " : " +lineIndex[0]+ " : " + count[0];
				counter2 = ddos_line + " : " +lineIndex[1]+ " : " + count[1];
				counter3 = ddos_line + " : " +lineIndex[2]+ " : " + count[2];
				counter4 = ddos_line + " : " +lineIndex[3]+ " : " + count[3];
				counter5 = ddos_line + " : " +lineIndex[4]+ " : " + count[4];
				/*
				for(int i=0;i<5;i++)
				{
					if((count[i]) != 100000)  System.out.println(ddos_line + ":" + lineIndex[i] + ":" + count[i]);
				}
				*/
			} // inner while end
	       
		   z++;
	   }
	   	long stop = System.currentTimeMillis();



	   	try{
    		String data = "\nnormal"+s + "  :  " + (stop-start);
    		File file =new File("javatime.txt");
 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(data);
    	        bufferWritter.close();
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}

    	
		
		}
	    
	} // main func.
			
} // class ending

//8349990547
