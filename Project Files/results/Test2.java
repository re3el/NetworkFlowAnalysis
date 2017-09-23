import java.io.*;
import java.util.*;

/**
* This example code shows you how to read file in Java
*
* IN MY CASE RAILWAY IS MY TEXT FILE WHICH I WANT TO DISPLAY YOU CHANGE WITH YOUR   OWN     */

 public class Test2 
 {
    public static void main(String[] args) 
    {
        
       long start = System.currentTimeMillis();
    //Name of the file
    String fileName="/media/Delta/DDoS_Dataset/results/test";

    Set<String> src_ips = new HashSet<String>();
    Set<String> dst_ips = new HashSet<String>();
    Set<String> src_ports = new HashSet<String>();
    Set<String> dst_ports = new HashSet<String>();
    try{

    //Create object of FileReader
    FileReader inputFile = new FileReader(fileName);

    //Instantiate the BufferedReader Class
    BufferedReader bufferReader = new BufferedReader(inputFile);

    //Variable to hold the one line data
    String line,srcIP,dstIP,srcPort,dstPort;

    

    // Read file line by line and print on the console
    while ((line = bufferReader.readLine()) != null)   
    {
           
            int pos=0;

            for(int i=0;i<9;i++)
            {
                pos = line.indexOf("|",pos+1);
            }

            srcIP =  new String( line.substring(0,line.indexOf("|")) );
            src_ips.add(srcIP);

            dstIP = new String(line.substring( srcIP.length()+1,line.indexOf("|",srcIP.length()+1)) ) ;
            dst_ips.add(dstIP);

            srcPort = new String( line.substring(pos+1,line.indexOf("|",pos+1)) );
            src_ports.add(srcPort);

            pos = line.indexOf("|",pos+1);
            dstPort = new String( line.substring(pos+1,line.indexOf("|",pos+1)) );
            dst_ports.add(dstPort);

    }
    long stop = System.currentTimeMillis();
    System.out.println ("Distinct Src IP\'s " + src_ips.size());
    System.out.println ("Distinct dst IP\'s " + dst_ips.size());
    System.out.println ("Distinct Src Port\'s " + src_ports.size());
    System.out.println ("Distinct dst Port\'s " + dst_ports.size());
	System.out.println ("Java Time: " + (stop-start));
    
    //Close the buffer reader
    bufferReader.close();
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }

    }
  }