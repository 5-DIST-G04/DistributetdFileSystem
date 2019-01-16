package com.distributed.node;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DiscoveryCom {
    private WebTarget target;

    public DiscoveryCom(String serverUri){
        Client c = ClientBuilder.newClient();
        target = c.target(serverUri).path("Discovery");
    }

    public void NodeFail(Integer nodeHash){
        Response response = target.path("nodeFail/" + nodeHash.toString()).request(MediaType.APPLICATION_JSON).delete();
        if (response.getStatus() == 409){
            System.out.println("The failed node didn't exist");
        }
    }


}
