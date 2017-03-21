package javat.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.simple.util.Constants;

public class NetworkInterfaceTest {
    private final static Logger logger = LoggerFactory.getLogger(NetworkInterfaceTest.class);

    @Test
    public void testGetHardwareAddress() throws Exception {

	Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
	while (en.hasMoreElements()) {
	    NetworkInterface ni = en.nextElement();
	    Enumeration<InetAddress> ia = ni.getInetAddresses();
	    while (ia.hasMoreElements()) {
		InetAddress addr = ia.nextElement();
		String ip = addr.getHostAddress();
		if (!addr.isLoopbackAddress() && Pattern.compile(Constants.IP_ADDRESS_PATTERN).matcher(ip).matches()) {
		    logger.info("ip address: {}", String.format("%s", ip));
		}
	    }

	    byte[] b = ni.getHardwareAddress();
	    if (b == null) {
		continue;
	    }
	    StringBuffer buffer = new StringBuffer();
	    for (byte c : b) {
		buffer.append(String.format("%02X", c)).append('-');
	    }
	    int length = buffer.length();
	    if (length > 1) {
		buffer.deleteCharAt(length - 1);
		logger.info("mac address: {}", buffer.toString());
	    }
	}

	// InetAddress[] addrs = InetAddress.getAllByName(null);
	// InetAddress.getAllByName(null); // 127.0.0.1
	// InetAddress.getLoopbackAddress(); // 127.0.0.1
	// InetAddress.getLocalHost(); // 192.168.98.102
	// InetAddress.getByName(null); 127.0.0.1

	// for (InetAddress addr : addrs) {
	// logger.info("ip address: {}",addr.getHostAddress());
	// NetworkInterface nif = NetworkInterface.getByInetAddress(addr);
	// byte[] b = nif.getHardwareAddress();
	// StringBuffer buffer = new StringBuffer();
	// for (byte c : b) {
	// buffer.append(String.format("%02X", c)).append('-');
	// }
	// if(buffer.length() > 1){
	// buffer.deleteCharAt(buffer.length() - 1);
	// }
	// logger.info("mac address: {}",buffer.toString());
	// }
	//
	//
	// InetAddress addr = InetAddress.getLocalHost();
	// logger.info("ip address: {}",addr.getHostAddress());
	// NetworkInterface nif = NetworkInterface.getByInetAddress(addr);
	// byte[] b = nif.getHardwareAddress();
	// StringBuffer buffer = new StringBuffer();
	// for (byte c : b) {
	// buffer.append(String.format("%02X", c)).append('-');
	// }
	// if(buffer.length() > 1){
	// buffer.deleteCharAt(buffer.length() - 1);
	// }
	// logger.info("mac address: {}",buffer.toString());

    }

}
