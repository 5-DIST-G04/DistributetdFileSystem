package com.distributed.node;

import com.distributed.common.Node;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class ReplicationCom {

    private WebTarget target;

    public ReplicationCom(String uri){
        Client c = ClientBuilder.newClient();
        target = c.target(uri).path("Replication");
    }
    public void replicate(FileData fileData) {
        System.out.println("The file: " + fileData.getName() + " is being replicated to: " + target.getUri());
        //GenericEntity<FileData> genericEntity = new GenericEntity<FileData>(fileData){};
        //Node testnode = new Node();
        //ServerSocket listener;

        try {
            //listener = new ServerSocket(9898);
            Response response = target.request().post(Entity.entity(fileData.getName(), MediaType.APPLICATION_JSON));
            //new FileSender(listener.accept(), fileData.getName()).start();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
