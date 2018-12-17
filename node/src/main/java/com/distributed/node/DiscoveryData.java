package com.distributed.node;

public class DiscoveryData {
    private static DiscoveryData ourInstance = new DiscoveryData();
    private String serverUri;
    private boolean initialized = false;
    private Integer prevNode;
    private Integer nextNode;

    public static synchronized DiscoveryData getInstance() {
        if (ourInstance == null)
            ourInstance = new DiscoveryData();
        return ourInstance;
    }

    private DiscoveryData() {

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
}
