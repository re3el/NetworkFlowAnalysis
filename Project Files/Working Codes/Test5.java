import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Test5
{
      
	public static void main(String args[]) throws IOException
	{
		long start = System.currentTimeMillis();
	
		String srcIP,srcIPN;
	    String dstIP,dstIPN;
	    String counter1,counter2,counter3,counter4,counter5,str,str2;
	    BufferedReader br,in;
	    int ddos_line = 0, normal_line = 0;	
	    int count10 = 0, count20=0, count30 = 0, count40=0, count50=0;
	    
	    ArrayList<String> lines1 = new ArrayList<String>();
	    ArrayList<String> lines2 = new ArrayList<String>();

	    //map method that performs the tokenizer job and framing the initial key value pairs
	    in = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/f1"));
	    br = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/f2"));

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
	       
	       
	       String[] linesArray2 = lines2.toArray(new String[lines2.size()]);
	       boolean bool = true;
	       
	       int t1=0;
	       double sum=0;
	       
	       
	       while (bool) 
			{
				for(int i=0; i<5;i++)
				{
						if(bool==false) break;
		   				sum = 0;
		   				String[] parts2 = linesArray2[normal_line].split("\\|");
		   				srcIPN =  parts2[0];
		   	            dstIPN = parts2[1];
		   	            
		   	            if(srcIP.equals(srcIPN) && dstIP.equals(dstIPN))
						{
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


				
				
				if((ddos_line %10) == 0 && count10==0) 
				{
					count10++;
					System.out.println(ddos_line);
					long stop = System.currentTimeMillis();
					System.out.println ("Java Time ( milli seconds): " + (stop-start));
				}
				
				else if((ddos_line %20) == 0 && count20==0) 
				{
					count20++;
					System.out.println(ddos_line);
					long stop = System.currentTimeMillis();
					System.out.println ("Java Time ( milli seconds): " + (stop-start));
				}

				else if((ddos_line %30) == 0 && count30==0) 
				{
					count30++;
					System.out.println(ddos_line);
					long stop = System.currentTimeMillis();
					System.out.println ("Java Time ( milli seconds): " + (stop-start));
				}

				else if((ddos_line %40) == 0 && count40==0) 
				{
					count40++;
					System.out.println(ddos_line);
					long stop = System.currentTimeMillis();
					System.out.println ("Java Time ( milli seconds): " + (stop-start));
				}

				else if((ddos_line %50) == 0 && count50==0) 
				{
					count50++;
					System.out.println(ddos_line);
					long stop = System.currentTimeMillis();
					System.out.println ("Java Time ( milli seconds): " + (stop-start));
				}
					
				
			} // inner while end
	       
		   z++;
	   }
	   	long stop = System.currentTimeMillis();
		System.out.println ("Java Time ( milli seconds): " + (stop-start));
	} // main func.
			
} // class ending


