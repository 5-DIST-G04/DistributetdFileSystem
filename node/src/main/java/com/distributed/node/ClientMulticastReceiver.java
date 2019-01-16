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

        if (!discoveryData.isInitialized()){
            return;
        }


        if (discoveryData.getThisNode().equals(newHashInt)){
            return;
        }

        if(discoveryData.getThisNode().equals(discoveryData.getNextNode()) && discoveryData.getThisNode().equals(discoveryData.getNextNode())){
            setAsPrevAndNextNode(newNode);
            return;
        }

        if(discoveryData.getThisNode() > discoveryData.getNextNode()){
            if(newHashInt < discoveryData.getNextNode()){
                setAsNextNode(newNode);
                return;
            }
        }

        if(discoveryData.getThisNode() > newHashInt && newHashInt > discoveryData.getPrevNode()){
            setAsPrevNode(newNode);
            return;
        }


        if ((discoveryData.getThisNode() < newHashInt) && (newHashInt < discoveryData.getNextNode())) {
            setAsNextNode(newNode);
            return;
        }
    }

    private void setAsNextNode(Node node){
        DiscoveryNodeCom dnc = new DiscoveryNodeCom(comConf.getUri(node.getIpAddress()));
        dnc.setPrevNode(discoveryData.getThisNode());
        dnc.setNextNode(discoveryData.getNextNode());
        discoveryData.setNextNode(node.getHash());
        return;
    }

    private void setAsPrevNode(Node node){
        discoveryData.setPrevNode(node.getHash());
    }

    private void setAsPrevAndNextNode(Node node){
        setAsNextNode(node);
        setAsPrevNode(node);
    }

}
