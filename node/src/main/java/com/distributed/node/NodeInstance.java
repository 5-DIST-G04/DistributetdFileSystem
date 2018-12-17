package com.distributed.node;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class NodeInstance {
    private HttpServer httpServer;
    public final String ServerUri;

    public NodeInstance(String serverUri){
        ServerUri = serverUri;
        httpServer = StartHttpServer(ServerUri);
    }

    public void Start(){

    }

    public void Stop(){

    }

    public static HttpServer StartHttpServer(String uri){
        final ResourceConfig rc = new ResourceConfig().packages("com.distributed.node");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
    }
}
