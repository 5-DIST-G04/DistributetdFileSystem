package com.distributed.server;

import com.distributed.common.Node;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("Discovery")
public class DiscoveryApi {
    private NamingData namingData = NamingData.getInstance();

    @DELETE
    @Path("/{hash}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response NodeFialiure(@PathParam("hash") Integer hash){
        if(namingData.RemoveNode(hash).isEmpty()) {
            return Response.status(409).entity("the node did not exist").build();
        } else {

        }
        return Response.status(204).build();
    }
}
