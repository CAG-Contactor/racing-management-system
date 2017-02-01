package se.cag.labs.pisensor.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NetworkUtils {
    public static String getIpAddress() throws SocketException {
        List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        return networkInterfaces.stream()
                .filter(n -> n.getName().startsWith("en") || n.getName().startsWith("eth") || n.getName().startsWith("wlan"))
                .filter(n -> {
                    try {
                        return !n.isLoopback() && n.isUp();
                    } catch (SocketException e1) {
                        return false;
                    }
                })
                .map(n -> {
                    // Try to get the first IPv4 address
                    Optional<InetAddress> inetAddress = Collections.list(n.getInetAddresses())
                            .stream()
                            .filter(a -> a instanceof Inet4Address)
                            .findFirst();

                    // Otherwise take the first IPv6 address
                    if (!inetAddress.isPresent()) {
                        inetAddress = Collections.list(n.getInetAddresses())
                                .stream()
                                .findFirst();
                    }

                    return inetAddress.get().getHostAddress();
                })
                .filter(a -> a != null)
                .findFirst()
                .orElse("127.0.0.1");
    }
}
