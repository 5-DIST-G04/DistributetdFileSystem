package com.distributed.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    private ComConf comConf;

    public MulticastPublisher(ComConf comConf){
        this.comConf = comConf;

    }

    public void multicast(String multicastMessage) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName(comConf.getMulticastIp());
        buf = multicastMessage.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, group, comConf.getMulticastPort());
        socket.send(packet);
        socket.close();
    }


}
