package com.distributed.server;

import com.distributed.common.ComConf;
import com.distributed.common.DiscoveryNodeCom;
import com.distributed.common.Node;

import javax.swing.text.html.Option;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("Discovery")
public class DiscoveryApi {
    private NamingData namingData = NamingData.getInstance();

    @DELETE
    @Path("/nodeFail/{hash}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response NodeFialiure(@PathParam("hash") Integer hash){
        if(namingData.getNodeIp(hash).isEmpty()) {
            return Response.status(409).entity("the node did not exist").build();
        } else {
            namingData.nodeFail(hash);
        }
        return Response.status(204).build();
    }
}
