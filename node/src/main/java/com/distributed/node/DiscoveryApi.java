package com.distributed.node;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("Discovery")
public class DiscoveryApi {
    private DiscoveryData discoveryData = DiscoveryData.getInstance();

    @POST
    @Path("ServerIp")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setServerIp(String serverIp){
        if(serverIp == null | serverIp.equals("")){
            return Response.status(406).entity("this is not allowed").build();
        }
        discoveryData.setServerIp(serverIp);
        return Response.status(204).build();
    }

    @POST
    @Path("NextNode")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setNextNode(Integer nodeHash){
        if(nodeHash == null){
            return Response.status(406).entity("this is not allowed").build();
        }
        discoveryData.setNextNode(nodeHash);
        return Response.status(204).build();
    }

    @POST
    @Path("PrevNode")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setPrevNode(Integer nodeHash){
        if(nodeHash == null){
            return Response.status(406).entity("this is not allowed").build();
        }
        discoveryData.setPrevNode(nodeHash);
        return Response.status(204).build();
    }
}
