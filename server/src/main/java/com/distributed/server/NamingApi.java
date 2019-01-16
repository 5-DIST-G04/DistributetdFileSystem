package com.distributed.server;

import com.distributed.common.Node;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("Naming")
public class NamingApi {
    private NamingData namingData = NamingData.getInstance();

    @GET
    @Path("/{hash}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIp(@PathParam("hash") Integer hash) {
        Optional<String> nodeIp = namingData.getNodeIp(hash);
        if(!nodeIp.isPresent())
            return Response.status(404).build();
        return Response.status(200).entity(nodeIp.get()).build();
    }

    @GET
    @Path("/FileLocation/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFileLocation(@PathParam("filename") String fileName){
        Optional<Integer> fileLocation = namingData.getFileLocation(fileName);
        if (!fileLocation.isPresent()){
            return Response.status(404).entity("There was no node found this could mean there is no node's in the network").build();
        }
        return Response.status(200).entity(fileLocation.get()).build();
    }

//    @GET
//    @Path("/{hash}/neighbours")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getNeigbours(@PathParam("hash") Integer hash){
//        NodeNameDatabase db = NodeNameDatabase.getInstance();
//        Node node =  db.getNodeByName(name);
//        db.calcNeigbours(node);
//        return Response.status(200).entity(node).build();   //TODO: check whether node actually exists.
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNode(Node node){
        if(node.getIpAddress() == null | node.getHash() == null | node.getIpAddress().equals(""))
            return Response.status(406).entity("failed the received node object was incomplete").build();
        if(namingData.AddNode(node) == -1)
            return Response.status(409).entity("this node already exists").build();
        return Response.status(201).entity("the node has been added to the list of known nodes").build();
    }


    @DELETE
    @Path("/{hash}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeNode(@PathParam("hash") Integer hash){
        if(namingData.RemoveNode(hash).isEmpty()){
            return Response.status(409).entity("the node did not exist").build();
        }
        System.out.println("node removed: " + hash);
        return Response.status(204).build();
    }
}
