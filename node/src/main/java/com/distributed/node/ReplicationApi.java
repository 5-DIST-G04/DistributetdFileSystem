package com.distributed.node;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.InputStream;

@Path("Replication")
public class ReplicationApi {
    private ReplicationData replicationData = ReplicationData.getInstance();

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response uploadFile(
//            @FormDataParam("file") InputStream uploadStream,
//            @FormDataParam("file") FormDataContentDisposition fileDetail){
//        return Response.status(200).build();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ReplicateFile(String fileName){
        System.out.println("Recieved new file for replication: " + fileName);
        FileData fileData = new FileData(fileName);
        fileData.setFileOwner(true);
        replicationData.receiveNewFile(fileData.getEntity());
        return Response.status(204).build();
    }
}
