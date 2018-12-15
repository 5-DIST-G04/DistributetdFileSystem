package com.distributed.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class NameServer {
    private NamingData namingData;
    private HttpServer httpServer;
    public final String ServerUri;

    public NameServer(String serverUri){
        ServerUri = serverUri;
        httpServer = StartHttpServer(ServerUri);
    }

    public void Start(){

    }

    public void Stop(){

    }

    public static HttpServer StartHttpServer(String uri){
        final ResourceConfig rc = new ResourceConfig().packages("com.distributed.server");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
    }
}
