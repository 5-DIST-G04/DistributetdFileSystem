package com.distributed.node;

import com.distributed.common.ComConf;
import com.distributed.common.DiscoveryNodeCom;
import com.distributed.common.MulticastReceiver;
import com.distributed.common.Node;

public class ClientMulticastReceiver extends MulticastReceiver {
    private DiscoveryData discoveryData = DiscoveryData.getInstance();

    public ClientMulticastReceiver(ComConf comConf) {
        super(comConf);
    }

    @Override
    public void processIncomingData(String data) {

        informNewNode(data);
    }


    public void informNewNode(String message) {


        //String name =  message.substring(1,message.length()-1);    //accolades wegdoen
        String[] splits = message.split(",");                        //nu hebben we hash nieuwe node en IP nieuwe node
        int newHashInt = Integer.parseInt(splits[0]);
        String newIP = splits[1];
        Node newNode = new Node(newHashInt, newIP);

        if ((discoveryData.getThisNode() < newHashInt) && (newHashInt < discoveryData.getNextNode())) {
            DiscoveryNodeCom discoveryNodeCom = new DiscoveryNodeCom(comConf.getUri(newNode.getIpAddress()));
            discoveryNodeCom.setPrevNode(discoveryData.getThisNode());
            discoveryNodeCom.setNextNode(discoveryData.getNextNode());

        } else if ((discoveryData.getPrevNode() < newNode.getHash()) && (newNode.getHash() < discoveryData.getThisNode())) {
            discoveryData.setPrevNode(newNode.getHash());
        }
    }

}
