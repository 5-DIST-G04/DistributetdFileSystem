package com.distributed.server;

import com.distributed.common.ComConf;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class NameServer {
    private NamingData namingData;
    private HttpServer httpServer;
    private ServerMulticastReceiver serverMulticastReceiver;
    private ComConf comConf;

    public NameServer(ComConf comConf){
        this.comConf = comConf;
        namingData = NamingData.getInstance();
    }

    public void Start(){
        serverMulticastReceiver = new ServerMulticastReceiver(comConf);
        serverMulticastReceiver.start();
        httpServer = StartHttpServer(comConf.getHostUri());
    }

    public void Stop(){
        serverMulticastReceiver.interrupt();
        try {
            serverMulticastReceiver.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        httpServer.shutdownNow();
        serverMulticastReceiver.stop();
    }

    public static HttpServer StartHttpServer(String uri){
        final ResourceConfig rc = new ResourceConfig().packages("com.distributed.server");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
    }
}
