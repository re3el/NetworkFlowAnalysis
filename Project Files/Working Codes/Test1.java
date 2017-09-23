import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test1
{
      
public static void main(String args[]) throws IOException
{
	long start = System.currentTimeMillis();

	String srcIP,srcIPN,line;
    String dstIP,dstIPN;
    int srcPort,srcPortN;
    int dstPort,dstPortN;
    String counter1,counter2,counter3,counter4,counter5 ;
    //private String total_records;
    	  
      int ddos_line = 0;
      //map method that performs the tokenizer job and framing the initial key value pairs
    BufferedReader br = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/normal1"));
//	String line= br.readLine();
	while( (line = br.readLine()) !=null)
      {
            
            ddos_line++;
            int pos1=0;
            int lineno=0;

            int[] count = {100000, 100000, 100000, 100000, 100000};
            int[] lineIndex = {0, 0, 0, 0, 0};

            for(int i=0;i<9;i++)
            {
            	pos1 = line.indexOf("|",pos1+1);
            }
               
            srcIP =  new String( line.substring(0,line.indexOf("|")) );
            
            dstIP = new String(line.substring( srcIP.length()+1,line.indexOf("|",srcIP.length()+1)) ) ;
           
            srcPort =  Integer.parseInt(line.substring(pos1+1,line.indexOf("|",pos1+1))) ;
            pos1 = line.indexOf("|",pos1+1);
            dstPort =  Integer.parseInt(line.substring(pos1+1,line.indexOf("|",pos1+1)) );
            

			BufferedReader br2 = new BufferedReader(new FileReader("/home/kishorer747/Desktop/hadoop/inputs/normal_small"));
			
			String line2 = br2.readLine();
	
			lineno++;
			boolean bool = true;
			while (bool) 
			{
   				
   				for(int i=0; i<5;i++)
   				{
   					if(bool==false)
   						break;
   					int pos=0;
	   				int temp;
	   				for(int j=0;j<9;j++)
		            {
		            	pos = line2.indexOf("|",pos+1);
		            }


		   			srcIPN =  new String( line2.substring(0,line2.indexOf("|")) );

		            dstIPN = new String(line2.substring( srcIPN.length()+1,line2.indexOf("|",srcIPN.length()+1)) ) ;
		            
		            srcPortN = Integer.parseInt( line2.substring(pos+1,line2.indexOf("|",pos+1)) );
		            pos = line2.indexOf("|",pos+1);
		            dstPortN = Integer.parseInt( line2.substring(pos+1,line2.indexOf("|",pos+1)) );


	   				if(srcIP.equals(srcIPN) && dstIP.equals(dstIPN))
					{
						int tmp, tmp2;
						
						tmp = srcPort - srcPortN;
						if(tmp<0)
							tmp*=-1;
						
						tmp2 = dstPort - dstPortN;
						if(tmp2<0)						
							tmp2*=-1;

						temp=tmp+tmp2;
						
						
						if(count[4] > temp)
						{
							count[4] = temp;
							lineIndex[4]=lineno;
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
            		/*
            		counter1 = new String("DDoS Line: " +ddos_line + "  Normal Line: " + lineIndex[0] + "    Difference: " + count[0]);
					counter2 = new String("DDoS Line: " +ddos_line + "  Normal Line: " + lineIndex[1] + "    Difference: " + count[1]);
					counter3 = new String("DDoS Line: " +ddos_line + "  Normal Line: " + lineIndex[2] + "    Difference: " + count[2]);
					counter4 = new String("DDoS Line: " +ddos_line + "  Normal Line: " + lineIndex[3] + "    Difference: " + count[3]);
					counter5 = new String("DDoS Line: " +ddos_line + "  Normal Line: " + lineIndex[4] + "    Difference: " + count[4]);

					 System.out.println(counter1);
					 System.out.println(counter2);
					 System.out.println(counter3);
					 System.out.println(counter4);
					 System.out.println(counter5);
					System.out.println("");
					*/
					}

					if ((line2 = br2.readLine()) != null )
					{
						lineno++;
						continue;
					} 
					
					else 
						bool = false;
   				}
   				
  
			}      

			br2.close();

			/*
			System.out.println(counter1);
			System.out.println(counter2);
			System.out.println(counter3);
			System.out.println(counter4);
			System.out.println(counter5);
			System.out.println("");
			*/
		}
		long stop = System.currentTimeMillis();
		System.out.println ("Java Time ( milli seconds): " + (stop-start));
} // main func.
} // class ending
