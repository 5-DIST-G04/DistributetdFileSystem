package com.distributed.common;


import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class MulticastReceiver extends Thread {
    private MulticastSocket socket = null;
    private byte[] buf = new byte[256];
    protected ComConf comConf;


    public MulticastReceiver(ComConf comConf){
        this.comConf = comConf;

    }

    public void run() {
        try {
            socket = new MulticastSocket(comConf.getMulticastPort());
            socket.setInterface(InetAddress.getByName(comConf.getHostIpAddress()));
            InetAddress group = InetAddress.getByName(comConf.getMulticastIp());
            socket.joinGroup(group);
            while (true) {
                //System.out.println("debug");
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                System.out.println("received: " + received);
                processIncomingData(received);
                if ("end".equals(received)) {
                    break;
                }
            }


            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public abstract void processIncomingData(String data);

    }

