package com.distributed.server;

import com.distributed.common.MulticastReceiver;
import com.distributed.common.Node;

public class ServerMulticastReceiver extends MulticastReceiver {
    NamingData namingData = NamingData.getInstance();

    public ServerMulticastReceiver(String ip, int port){
        super(ip,port);
    }

    @Override
    public void proccessIncomingData(String data) {
        String name =  data.substring(1,data.length()-1);    //accolades wegdoen
        String[] splits = name.split(",");                        //nu hebben we hash nieuuwe node en IP nieuwe node
        int newHashInt = Integer.parseInt(splits[0]);
        String newIP = splits[1];
        Node newNode = new Node(newHashInt, newIP);
        namingData.AddNode(newNode); //todo update node about server
    }
}
