
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import net.sourceforge.jpcap.util.Timeval;

public class anotherPgm
{
    private static final int INFINITE = -1;
    private static final int PACKET_COUNT = 1000;

    
    // BPF filter for capturing any packet
    public static final String FILTER = "";

    public static PacketCapture m_pcap;
    public static String m_device;

    public static int reset_Count = 0;
    public static int total_packet_Count = 0;
    public static int syn_Count = 0;
    public static long seq_no = 0;

    public static void main(String[] args)
    {
    	
        try
        {
        	// Step 1:  Instantiate Capturing Engine
            m_pcap = new PacketCapture();

            // Step 2:  Check for devices
            m_device = m_pcap.findDevice();
            
            /*
            //m_device = "\\Device\\NPF_{38C664F3-0D62-4536-9A51-9B1E213FD057}";

            // Step 3:  Open Device for Capturing (requires root)
            m_pcap.open(m_device, true);

            // Step 4:  Add a BPF Filter (see tcpdump documentation)
            m_pcap.setFilter(FILTER, true);

            // Step 5:  Register a Listener for Raw Packets
            m_pcap.addRawPacketListener(new RawPacketHandler());

            // Step 6:  Capture Data (max. PACKET_COUNT packets)
            m_pcap.capture(-1);
            */
        	int i = m_device.indexOf(" ");
        	//m_device = m_device.substring(0,i-1);
        	m_device = "\\Device\\NPF_{83F71149-7C4C-4322-8A78-A99B3E977425}"; // my laptop wi-fi interface
        	 PacketCapture pcap = new PacketCapture();
             System.out.println("Using device '" + m_device + "'");
             pcap.open(m_device, 65535, true, 1000);
            // pcap.setFilter("ip and tcp", true);
         //    pcap.setFilter("port 4444", true);
             pcap.addPacketListener(new PacketHandler2());

             System.out.println("Capturing packets...");
             
             pcap.capture(250); // -1 is infinite capturing
             System.out.println("Syn Count : " + syn_Count);
             System.out.println("Reset Count : " + reset_Count);
             System.out.println("Total Packet Count : " + total_packet_Count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

class PacketHandler2 implements PacketListener {
    BufferedOutputStream stream;
    
    public PacketHandler2() throws IOException {
    	//System.out.println("Reset Count from Pkt Handler: " + reset_Count);
    	
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
               
                
                anotherPgm.total_packet_Count++;
             
                String src_IP = tcpPacket.getSourceAddress();
             //  System.out.println("Src IP : " + src_IP);
                boolean syn,ack,rst,fin,psh,urg;
                syn = tcpPacket.isSyn();
                ack = tcpPacket.isAck();
                rst = tcpPacket.isRst();
                fin = tcpPacket.isFin();
                psh = tcpPacket.isPsh();
                urg = tcpPacket.isUrg();
                
                
                int service = tcpPacket.getTypeOfService();
                Timeval time_arrived = tcpPacket.getTimeval();
                int protocol = tcpPacket.getProtocol();
                
                System.out.println("Service: "+ service+ " Time Arrived: "+time_arrived+" Protocol: "+ protocol);
                //System.out.println("Syn: "+syn+"  Ack: "+ack+"   Rst: "+rst+"   Fin: "+fin+"   Psh: "+psh+"   Urg: "+urg );
                
                String dst_IP = tcpPacket.getDestinationAddress();  // dst_ip in string
                System.out.println("Dst IP is " + dst_IP);
                InetAddress local = InetAddress.getLocalHost();
                String local_ip = String.valueOf(local);
                int pos_Backslash = local_ip.indexOf("/");

                local_ip = local_ip.substring(pos_Backslash+1);   // local_ip in string
                
               
                
                if(src_IP.equals(local_ip))
                {
                	//int protocol = tcpPacket.getProtocol();
                    int payloadLength = tcpPacket.getPayloadDataLength();
                    int headerLength = tcpPacket.getHeaderLength();
                    if(syn) anotherPgm.syn_Count++;
                    //System.out.println("Payload Data Length: " +payloadLength+ "   Protocol: "+ protocol);
                    
                    if(syn)
                    {
                    	anotherPgm.seq_no = tcpPacket.getSequenceNumber();
                    }
                    
                }
                
                
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
                
                if(tcpPacket.isRst())
                {
                	anotherPgm.reset_Count++;
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
