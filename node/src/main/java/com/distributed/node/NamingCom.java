package com.distributed.node;

import com.distributed.common.Node;
import org.glassfish.jersey.internal.Errors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

public class NamingCom {
    private WebTarget target;

    public NamingCom(String serverUri){
        Client c = ClientBuilder.newClient();
        target = c.target(serverUri).path("Naming");
    }

    public void registerNode(Node node){
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(node,
                MediaType.APPLICATION_JSON));
        if (response.getStatus() == 409){
            throw new Error("A node with this node already exists on the network.");
        }
        if (response.getStatus() != 201){
            System.out.println(response.getStatusInfo());
            System.out.println(response.getStatus());
        }


    }

    public Optional<String> getIpAddress(Integer nodeHash){
        Response response = target.path(nodeHash.toString()).request(MediaType.APPLICATION_JSON).get();
        try {
            return Optional.ofNullable(response.readEntity(String.class));
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void RemoveNode(Integer nodeHash){
        Response response = target.path(nodeHash.toString()).request(MediaType.APPLICATION_JSON).delete();
        if (response.getStatus() == 409){
            System.out.println("The removed node didn't exist");
        }
    }

    public Optional<Integer> getFileLocation(String fileName){
        Response response = target.path("FileLocation/" + fileName).request(MediaType.APPLICATION_JSON).get();
        if(response.getStatus() != 200){
            System.out.println("the server did not return a node this means that something went wrong in discovery");
        }

        return Optional.ofNullable(response.readEntity(Integer.class));
    }
}
