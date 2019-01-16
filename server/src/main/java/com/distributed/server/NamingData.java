package com.distributed.server;

import com.distributed.common.NameHasher;
import com.distributed.common.Node;

import javax.swing.text.html.Option;
import java.net.InetAddress;
import java.util.*;

public class NamingData {
    private static NamingData ourInstance;
    private Map<Integer,String> nodeList;

    private NamingData(){
        nodeList = new HashMap<>();
    }

    public static NamingData getInstance() {
        if (ourInstance == null){
            ourInstance = new NamingData();
        }
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
        System.out.printf("node with hash: %d and ip: %s recieved and added to list",node.getHash(), node.getIpAddress());
        return 0;
    }

    public synchronized Optional RemoveNode(Integer hash){
        return Optional.ofNullable(nodeList.remove(hash));
    }

    public synchronized Optional<Integer> getFileLocation(String fileName){
        Set<Integer> nodes = this.nodeList.keySet();
        int fileHash = NameHasher.Hash(fileName);
        Object[] hashArray = nodes.toArray();
        Arrays.sort(hashArray);
        int index = hashArray.length -1;
        for (int i = 0; i < hashArray.length; i++) {
            int value = (Integer)hashArray[i];
            if(value > fileHash){
                if(i != 0)
                    index = i - 1;
                break;
            }
        }
        return Optional.ofNullable((Integer)hashArray[index]);
    }

    public synchronized Optional<Integer> getNextNeigbour(Integer nodeHash){
        if(this.nodeList.isEmpty()){
            return Optional.empty();
        }
        Collection<Integer> nodes= this.nodeList.keySet();
        TreeSet<Integer> e = new TreeSet<>(nodes);
        if(nodeHash.equals(e.last())){
            return Optional.ofNullable(e.first());
        }
        Integer nextNode = e.last();
        for (Integer node:
             e) {
            if(node > nodeHash && node < nextNode){
                nextNode = node;
            }
        }
        return Optional.of(nextNode);
    }

    public synchronized Optional<Integer> getPreviousNeighbour(Integer nodeHash){
        if(this.nodeList.isEmpty()){
            return Optional.empty();
        }
        Collection<Integer> nodes= this.nodeList.keySet();
        TreeSet<Integer> e = new TreeSet<>(nodes);
        if(nodeHash.equals(e.first())){
            return Optional.ofNullable(e.last());
        }
        Integer prevNode = e.first();
        for (Integer node:
                e) {
            if(node < nodeHash && node > prevNode){
                prevNode = node;
            }
        }
        return Optional.of(prevNode);
    }
}
