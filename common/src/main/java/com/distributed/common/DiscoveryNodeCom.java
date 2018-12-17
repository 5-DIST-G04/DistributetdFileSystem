package com.distributed.common;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DiscoveryNodeCom {
    private WebTarget target;

    public DiscoveryNodeCom(String uri){
        Client c = ClientBuilder.newClient();
        target = c.target(uri).path("Discovery");
    }

    public void setServerUri(String serverUri){
        Response response = target.path("ServerUri").request(MediaType.APPLICATION_JSON).post(Entity.entity(serverUri,MediaType.APPLICATION_JSON));
        if (response.getStatus() != 204){
            System.out.println("an error occured when trying to set the serverUri of: " + target.getUri());
        }
    }

    public void setNextNode(Integer nodeHash){
        Response response = target.path("NextNode").request(MediaType.APPLICATION_JSON).post(Entity.entity(nodeHash, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 204){
            System.out.println("an error ocured when setting the nextnode parameter of: " + target.getUri());
        }
    }

    public void setPrevNode(Integer nodeHash){
        Response response = target.path("PrevNode").request(MediaType.APPLICATION_JSON).post(Entity.entity(nodeHash, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 204){
            System.out.println("an error ocured when setting the prevnode parameter of: " + target.getUri());
        }
    }
}
