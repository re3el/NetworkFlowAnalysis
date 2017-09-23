import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class timeInt
{
 
	public static void main(String args[]) throws IOException
	{
		String line;
		int i=0;
		long timeValue=0, check=2119999998, pL=0, prot =0, cTCP=0, cUDP=0, tpc=0;			 		// to check the limit of the interval
		long avgNum=0, avgLen=0, avgTCP=0, avgUDP=0, avgICMP=0, avgRest=0;
		//keeps the count of packets for second intervals of time(1000 intervals);

		long[] counterC = new long[100000]; 					// Packet Count
		long[] counterPL = new long[100000];					// Packet length
		long[] counterTCP = new long[100000];					// TCP count; Protocol-6
		long[] counterUDP = new long[100000];					// UDP count; Protocol-17
		long[] counterICMP = new long[100000];					// ICMP count; Protocol-1
		long[] counterRest = new long[100000];					// Count for all other protocols	
		double[] perTCP = new double[100000];					// percentage TCP count; Protocol-6
		double[] perUDP = new double[100000];					// percentage UDP count; Protocol-17
		double[] perICMP = new double[100000];					// percentage ICMP count; Protocol-1
		double[] perRest = new double[100000];					// percentage Count for all other protocols				

		BufferedReader br = new BufferedReader(new FileReader("/home/yogi/Desktop/normal1.txt"));
		while((line = br.readLine()) !=null)
		{
			String[] part = line.split("\\|");

			if(part.length>=5)
			{
				timeValue = Long.parseLong(part[0].substring(0,part[0].indexOf(".")));
				counterC[i]++;
				tpc++;

				if(part[3].indexOf(",")!=-1)
					pL = Integer.parseInt(part[3].substring(0,part[3].indexOf(",")));
				else
					pL = Integer.parseInt(part[3]);
				counterPL[i]+=pL;
				
				if(part[4].indexOf(",")!=-1)
					prot = Integer.parseInt(part[4].substring(0,part[4].indexOf(",")));
				else
					prot = Integer.parseInt(part[4]);

				if(prot==6)
					counterTCP[i]++;
				else if(prot==17)
					counterUDP[i]++;
				else if(prot==1)
					counterICMP[i]++;
				else
					counterRest[i]++;


				if(check+1==timeValue)
				{
					perTCP[i]=counterTCP[i]/counterC[i];
					perUDP[i]=counterUDP[i]/counterC[i];
					perICMP[i]=counterICMP[i]/counterC[i];
					perRest[i]=counterRest[i]/counterC[i];
					i++;		
				}
				check = timeValue;
				

				
			}
		}

		int j=0;
		
		while(j!=i)
		{
			System.out.println("num: "+counterC[j]+", len: "+counterPL[j]+", TCP: "+counterTCP[j]+", UDP: "+counterUDP[j]+", ICMP: "+counterICMP[j]+", Rest: "+counterRest[j]);			
				avgNum+=counterC[j];
				avgLen+=counterPL[j];
				avgTCP+=counterTCP[j];
				avgUDP+=counterUDP[j];
				avgICMP+=counterICMP[j];
				avgRest+=counterRest[j];		
			j++;
		}

		System.out.println("# of Int: "+i);
		System.out.println("Total Pkts: "+avgNum+", Tot Pkt Len: "+avgLen+", Tot TCP: "+avgTCP+", Tot UDP: "+avgUDP+", Tot ICMP: "+avgICMP+", Tot Rest: "+avgRest);
		System.out.println("Avg Pkts: "+avgNum/i+", Avg Pkt Len: "+avgLen/avgNum+", Avg TCP: "+avgTCP/avgNum+", Avg UDP: "+avgUDP/avgNum+", Avg ICMP: "+avgICMP/avgNum+", Avg Rest: "+avgRest/avgNum);		
	}
}