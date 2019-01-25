package com.distributed.node;

import com.distributed.common.ComConf;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.graalvm.compiler.core.phases.CommunityCompilerConfiguration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AgentCom extends Thread implements Serializable {

    private HashMap<Integer, Map<String, Bool>> Files;
    DiscoveryData data = DiscoveryData.getInstance();

    private WebTarget target;
    public AgentCom() {
        //todo FIleagent moet ergens aangeroepen worden
        if(data.getNextNode() == null){
            Files = new HashMap<>();
            addLocalFiles();
        }
    }
    public AgentCom(HashMap<Integer, Map<String, Bool>> Files){
        this.Files = Files;
        initTarget();

    }

    private  void initTarget(){
        NamingCom nc = new NamingCom(data.getNodeURI());
        Client c = ClientBuilder.newClient();
        target = c.target(nc.getIpAddress(data.getNextNode().hashCode()).get()).path("passFile");

    }
    @Override
    public void run() {
        addLocalFiles();
    }

    private void addLocalFiles(){
        //Todo
        //if has local files add to FIles

        //loop through local files

        //if exist dont change

        //if owned file is not locked and received file = locked lock local file

    }

    private void passToNextNode(){
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(Files,
                MediaType.APPLICATION_JSON));
        if (response.getStatus() == 409){
            throw new Error("A node with this node already exists on the network.");
        }
        if (response.getStatus() != 201){
            System.out.println(response.getStatusInfo());
            System.out.println(response.getStatus());
            //stop thread if passed succesfully
            try {
                this.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
