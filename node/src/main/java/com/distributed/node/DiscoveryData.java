package com.distributed.node;

import com.distributed.common.NameHasher;

public class DiscoveryData {
    private static DiscoveryData ourInstance = new DiscoveryData();
    private String serverUri;
    private boolean initialized = false;
    private Integer prevNode;
    private Integer nextNode;
    private String nodeName;

    public static synchronized DiscoveryData getInstance() {
        if (ourInstance == null)
            ourInstance = new DiscoveryData();
        return ourInstance;
    }

    private DiscoveryData() {
    }

    public synchronized void Init(String nodeName){
        this.nodeName = nodeName;
        this.nextNode = this.getThisNode();
        this.prevNode = this.getThisNode();
    }

    public synchronized String getServerUri() {
        return serverUri;
    }

    public synchronized void setServerUri(String serverUri) {
        this.serverUri = serverUri;
        initialized = true;
    }

    public synchronized boolean isInitialized() {
        return initialized;
    }

    public synchronized Integer getPrevNode() {
        return prevNode;
    }

    public synchronized void setPrevNode(Integer prevNode) {
        this.prevNode = prevNode;
    }

    public synchronized Integer getNextNode() {
        return nextNode;
    }

    public synchronized void setNextNode(Integer nextNode) {
        this.nextNode = nextNode;
    }

    public synchronized Integer getThisNode(){
        return NameHasher.Hash(this.nodeName);
    }
}
