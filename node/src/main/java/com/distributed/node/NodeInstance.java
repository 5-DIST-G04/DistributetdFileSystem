package com.distributed.node;

import com.distributed.common.ComConf;
import com.distributed.common.MulticastPublisher;
import com.distributed.common.MulticastReceiver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;

public class NodeInstance {
    private DiscoveryData discoveryData;
    private MulticastReceiver clientMulticastReceiver;
    private HttpServer httpServer;
    private final ComConf comConf;

    public NodeInstance(ComConf comConf){
        this.comConf = comConf;
        clientMulticastReceiver = new ClientMulticastReceiver(comConf);
        discoveryData = DiscoveryData.getInstance();
    }

    public void Start(){

        discoveryData.Init(comConf.getHostIpAddress()); //TODO: this name should be generated in some way
        clientMulticastReceiver.start();
        httpServer = StartHttpServer(comConf.getHostUri());
        sendInitMulticast(comConf.getHostIpAddress());

    }

    public void Stop(){
        httpServer.shutdownNow();
        clientMulticastReceiver.stop();
    }

    public static HttpServer StartHttpServer(String uri){
        final ResourceConfig rc = new ResourceConfig().packages("com.distributed.node");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
    }

    private void sendInitMulticast(String ipAddress){
        MulticastPublisher publisher = new MulticastPublisher(comConf);
        try {
            publisher.multicast( discoveryData.getThisNode() + "," + ipAddress);
        } catch (IOException e) {
            System.out.println("The multicast failed");
            e.printStackTrace();
        }
    }
}
