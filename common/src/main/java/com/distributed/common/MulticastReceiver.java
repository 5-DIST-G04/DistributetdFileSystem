package com.distributed.common;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public abstract class MulticastReceiver extends Thread {
    private MulticastSocket socket = null;
    InetAddress group;
    private byte[] buf = new byte[256];
    protected ComConf comConf;
    private boolean end = false;


    public MulticastReceiver(ComConf comConf){
        this.comConf = comConf;

    }

    public void run() {
        try {
            socket = new MulticastSocket(comConf.getMulticastPort());
            socket.setInterface(InetAddress.getByName(comConf.getHostIpAddress()));
            group = InetAddress.getByName(comConf.getMulticastIp());
            socket.joinGroup(group);
            while (!end) {
                //System.out.println("debug");
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                System.out.println("received: " + received);
                processIncomingData(received);
                if (end) {
                    return;
                }
            }



        } catch (SocketException e) {
            System.out.println("Socket stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public abstract void processIncomingData(String data);

    @Override
    public void interrupt(){
        try {
            socket.leaveGroup(group);
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        end = true;
    }
}


