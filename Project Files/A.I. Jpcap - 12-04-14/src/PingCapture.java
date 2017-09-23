
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.ICMPMessages;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.Packet;
 
public class PingCapture {
    public static void main(String[] args) throws CaptureDeviceLookupException {
        Capture cap = new Capture();
        cap.doCapture();
    }
}
 
class PingListener implements PacketListener {
    @Override
    public void packetArrived(Packet packet) {
        try {
            // only ICMP packages
            if (packet instanceof ICMPPacket) {
                ICMPPacket tcpPacket = (ICMPPacket) packet;
                int data = tcpPacket.getMessageCode();
                // only echo request packages
                if (data == ICMPMessages.ECHO) {
                    // print source and destination.
                    String srcHost = tcpPacket.getSourceAddress();
                    String dstHost = tcpPacket.getDestinationAddress();
                    System.out.println("Ping from: " + srcHost + " to " + dstHost);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
class Capture {
    public void doCapture() {
        // create capture instance
        PacketCapture capture = new PacketCapture();
        // add listener that handles incoming and outgoing packages
        PingListener pingListener = new PingListener();
        capture.addPacketListener(pingListener);
        try {
            capture.open("\\Device\\NPF_{C0C30404-41ED-47B4-9D69-BEDA837F5AFB}", true); // Replace the device ID with your own
            while (true) {
                capture.capture(1); // capture one packet, since this is in a loop, it will keep on capturing more packets
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            capture.removePacketListener(pingListener);
            capture.endCapture();
            capture.close();
        }
    }
}