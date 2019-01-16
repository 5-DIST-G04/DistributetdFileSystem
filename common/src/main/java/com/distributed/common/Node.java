package com.distributed.common;

public class Node {
    private Integer Hash;
    private String IpAddress;

    public Node () {
    }

    public Node(String Name, String ip){
        Hash = NameHasher.Hash(Name);
        this.IpAddress = ip;
    }

    public Node(Integer hash, String ip){
        this.Hash = hash;
        this.IpAddress = ip;
    }


    public Integer getHash() {
        return Hash;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setHash(Integer hash) {
        Hash = hash;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }
}
