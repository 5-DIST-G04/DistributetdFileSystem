package com.distributed.common;


import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    int port;
    String ip;


    public MulticastReceiver(String ip, int port){
        this.port = port;
        this.ip = ip;

    }

    public void run() {
        try {
            socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(ip);
            socket.joinGroup(group);
            while (true) {
                System.out.println("debug");
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                System.out.println("received: " + received);
                proccessIncomingData(received);
                if ("end".equals(received)) {
                    break;
                }
            }


            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {

        }
    }


    public abstract void proccessIncomingData(String data);

    }

