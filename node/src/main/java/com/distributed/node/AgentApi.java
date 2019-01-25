package com.distributed.node;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class AgentApi  {

    @POST
    @Path("passFile")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response passFile(HashMap<Integer, Map<String, Bool>> Files){
        if(Files == null){
            return Response.status(406).entity("this is not allowed").build();
        }
        AgentCom agent = new AgentCom(Files);
        agent.start();
        return Response.status(204).build();
    }
}
