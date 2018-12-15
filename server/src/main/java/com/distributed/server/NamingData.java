package com.distributed.server;

import com.distributed.common.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NamingData {
    private static NamingData ourInstance = new NamingData();
    private Map<Integer,String> nodeList;

    private NamingData(){
        nodeList = new HashMap<>();
    }

    public static NamingData getInstance() {
        return ourInstance;
    }

    public synchronized Optional<String> getNodeIp(Integer hash){
        return Optional.ofNullable(nodeList.get(hash));
    }

    public synchronized int AddNode(Node node){
        if (nodeList.containsKey(node.getHash())){
            return -1;
        }
        nodeList.put(node.getHash(), node.getIpAddress());
        return 0;
    }

    public synchronized Optional RemoveNode(Integer hash){
        return Optional.ofNullable(nodeList.remove(hash));
    }
}
