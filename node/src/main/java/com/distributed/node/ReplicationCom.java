package com.distributed.node;

import com.distributed.common.Node;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ReplicationCom {

    WebTarget target;

    private ReplicationCom() {}
    public ReplicationCom(String uri){
        Client c = ClientBuilder.newClient();
        target = c.target(uri).path("Discovery");
    }
    public void replicate(FileData fileData) {

    }
}
