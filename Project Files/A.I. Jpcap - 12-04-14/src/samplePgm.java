
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;

public class samplePgm
{
    private static final int INFINITE = -1;
    private static final int PACKET_COUNT = 1000;

    
    // BPF filter for capturing any packet
    private static final String FILTER = "";

    private PacketCapture m_pcap;
    private String m_device;

    public samplePgm() throws Exception
    {
    	
        // Step 1:  Instantiate Capturing Engine
        m_pcap = new PacketCapture();

        // Step 2:  Check for devices
        // m_device = m_pcap.findDevice();
        
        /*
        //m_device = "\\Device\\NPF_{38C664F3-0D62-4536-9A51-9B1E213FD057}";

        // Step 3:  Open Device for Capturing (requires root)
        m_pcap.open(m_device, true);

        

        // Step 5:  Register a Listener for Raw Packets
        m_pcap.addRawPacketListener(new RawPacketHandler());

        
        */
    	// int i = m_device.indexOf(" ");
    	// m_device = m_device.substring(0,i-1);
    	 m_device = "\\Device\\NPF_{83F71149-7C4C-4322-8A78-A99B3E977425}"; // my laptop wifi 
    	 PacketCapture pcap = new PacketCapture();
         System.out.println("Using device '" + m_device + "'");
         pcap.open(m_device, 65535, true, 1000);
         pcap.setFilter("ip and tcp", true);
         pcap.setFilter("port 4444", true);
         pcap.addPacketListener(new PacketHandler());

         System.out.println("Capturing packets...");
         
         pcap.capture(100000); // -1 is infinite capturing
    }
    

    public static void main(String[] args)
    {
    	try
        {
        	samplePgm example = new samplePgm();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

class PacketHandler implements PacketListener {
    BufferedOutputStream stream;

    public PacketHandler() throws IOException {
        Path path = Paths.get("out.txt");
        stream = new BufferedOutputStream(
                Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND));
    }

    @Override
	public void packetArrived(Packet packet) {
        try {
            // only handle TCP packets

            if(packet instanceof TCPPacket) {
                TCPPacket tcpPacket = (TCPPacket)packet;
                // byte[] data;
                //data = tcpPacket.getTCPData();
                //data = tcpPacket.toString().getBytes();
                //int src_IP_Pos = tcpPacket.IP_SRC_POS;
              String src_IP = tcpPacket.getSourceAddress();
             //  System.out.println("Src IP : " + src_IP);
                boolean syn,ack,rst,fin,psh,urg;
                syn = tcpPacket.isSyn();
                ack = tcpPacket.isAck();
                rst = tcpPacket.isRst();
                fin = tcpPacket.isFin();
                psh = tcpPacket.isPsh();
                urg = tcpPacket.isUrg();
                
                System.out.println("Syn: "+syn+"  Ack: "+ack+"   Rst: "+rst+"   Fin: "+fin+"   Psh: "+psh+"   Urg: "+urg );
                
                String dst_IP = tcpPacket.getDestinationAddress();  // dst_ip in string
                System.out.println("Dst IP is " + dst_IP);
                InetAddress local = InetAddress.getLocalHost();
                String local_ip = String.valueOf(local);
                int pos_Backslash = local_ip.indexOf("/");

                local_ip = local_ip.substring(pos_Backslash+1);   // local_ip in string
                
                /*
                if(dst_IP.equals(local_ip))   // if the packet is for this pc....
                {
                    int protocol = tcpPacket.getProtocol();
                    int totalLength = tcpPacket.getLength();
                    int headerLength = tcpPacket.getHeaderLength();
                    int payloadLength = totalLength - headerLength;
                   
                    System.out.println("Payload Data Length: " +payloadLength+ "   Protocol: "+ protocol);
                    //System.out.println("Source IP: "+ src_IP + "     Destination IP: " + dst_IP);
                    
                }
                */
                
                if(src_IP.equals(local_ip))
                {
                	int protocol = tcpPacket.getProtocol();
                    int headerLength = tcpPacket.getHeaderLength();
                    int payloadLength = tcpPacket.getHeaderLength();
                    if(tcpPacket.isRst())
                    {
                    	//reset_Count += 1;
                    }
                    System.out.println("Payload Data Length: " +payloadLength+ "   Protocol: "+ protocol);
                }
               
                /*
                stream.write(data);
                stream.write("\r\n----------\r\n".getBytes());
                stream.flush();
                */
            }
        } catch( Exception e ) {
            e.printStackTrace(System.out);
        }
    }
}
