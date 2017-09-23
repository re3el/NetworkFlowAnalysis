import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureDeviceNotFoundException;
import net.sourceforge.jpcap.capture.PacketCapture;
public class JpcapTrial {
	public static PacketCapture m_pcap;
	public static String m_device;
	
	public static void main(String args[]) throws CaptureDeviceLookupException{
		
		try {
	           PacketCapture capture = new PacketCapture();
	           
	           m_pcap = new PacketCapture();
	           m_device = m_pcap.findDevice();
	           
	           String[] interfaces = PacketCapture.lookupDevices(); 
	           
	           for(int i=0;i< interfaces.length; i++)
	           {
	        	   System.out.println("Interface "+ i + " :  "+interfaces[i]);
	           }
	        } catch (CaptureDeviceNotFoundException e) {
	           // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
}