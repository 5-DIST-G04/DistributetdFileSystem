package com.distributed.node;

import com.distributed.common.ComConf;
import com.distributed.common.NameHasher;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class DiscoveryData {
    private static DiscoveryData ourInstance;
    private String serverIp;
    private boolean initialized = false;
    private Integer prevNode;
    private Integer nextNode;
    private String nodeName;
    private ComConf comConf;

    public static synchronized DiscoveryData getInstance() {
        if (ourInstance == null)
            ourInstance = new DiscoveryData();
        return ourInstance;
    }



    public synchronized void Init(ComConf comConf){
        this.nodeName = comConf.getHostIpAddress();
        this.nextNode = this.getThisNode();
        this.prevNode = this.getThisNode();
        this.comConf = comConf;
    }

    public synchronized String getServerIp() {
        return serverIp;
    }

    public synchronized void setServerIp(String serverIp) {
        this.serverIp = serverIp;
        initialized = true;
        System.out.printf("recieved ip of server: %s\n",serverIp);
    }

    public synchronized boolean isInitialized() {
        return initialized;
    }

    public synchronized Integer getPrevNode() {
        return prevNode;
    }

    public synchronized void setPrevNode(Integer prevNode) {
        this.prevNode = prevNode;
        System.out.println("prev node is: " + prevNode);
    }

    public synchronized Integer getNextNode() {
        return nextNode;
    }

    public synchronized void setNextNode(Integer nextNode) {
        this.nextNode = nextNode;
        System.out.println("next node is: " + nextNode);
    }

    public synchronized Integer getThisNode(){
        return NameHasher.Hash(this.nodeName);
    }

    public synchronized String getNodeURI(){
        return comConf.getUri(getServerIp());
    }
}
