package com.distributed.node;

import com.distributed.common.ComConf;
import com.distributed.common.DiscoveryNodeCom;
import com.distributed.common.MulticastPublisher;
import com.distributed.common.MulticastReceiver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Optional;

public class NodeInstance {
    private DiscoveryData discoveryData;
    private ReplicationData replicationData;
    private MulticastReceiver clientMulticastReceiver;
    private HttpServer httpServer;
    private final ComConf comConf;

    public NodeInstance(ComConf comConf){
        this.comConf = comConf;
        replicationData = ReplicationData.getInstance();
        clientMulticastReceiver = new ClientMulticastReceiver(comConf);
        discoveryData = DiscoveryData.getInstance();
    }

    public void Start(){

        discoveryData.Init(comConf.getHostIpAddress()); //TODO: this name should be generated in some way
        clientMulticastReceiver.start();
        httpServer = StartHttpServer(comConf.getHostUri());
        sendInitMulticast(comConf.getHostIpAddress());

        replicationData.Init(comConf);
    }

    public void Stop(){
        if(discoveryData.isInitialized()){
            sendShutdown();
        }
        httpServer.shutdown();
        clientMulticastReceiver.interrupt();
        try {
            clientMulticastReceiver.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        replicationData.shutDown();
    }

    public static HttpServer StartHttpServer(String uri){
        final ResourceConfig rc = new ResourceConfig().packages("com.distributed.node");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
    }

    private void sendShutdown(){
        NamingCom nc = new NamingCom(comConf.getUri(discoveryData.getServerIp()));
        Optional<String> ip = nc.getIpAddress(discoveryData.getNextNode());
        if (ip.isPresent()){
            DiscoveryNodeCom dc = new DiscoveryNodeCom(comConf.getUri(ip.get()));
            dc.setPrevNode(discoveryData.getPrevNode());
        }
        ip = nc.getIpAddress(discoveryData.getPrevNode());
        if(ip.isPresent()){
            DiscoveryNodeCom dc = new DiscoveryNodeCom(comConf.getUri(ip.get()));
            dc.setNextNode(discoveryData.getNextNode());
        }
        nc.RemoveNode(discoveryData.getThisNode());
    }

    private void sendInitMulticast(String ipAddress){
        MulticastPublisher publisher = new MulticastPublisher(comConf);
        try {
            publisher.multicast( discoveryData.getThisNode() + "," + ipAddress);
            System.out.println("multicast send");
        } catch (IOException e) {
            System.out.println("The multicast failed");
            e.printStackTrace();
        }
    }
}
