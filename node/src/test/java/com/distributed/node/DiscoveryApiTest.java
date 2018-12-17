package com.distributed.node;

import com.distributed.common.DiscoveryNodeCom;
import com.distributed.common.NameHasher;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class DiscoveryApiTest {
    private DiscoveryData discoveryData;
    private DiscoveryNodeCom discoveryNodeCom;
    private HttpServer httpServer;

    @Before
    public void setUp() throws Exception {
        final String nodeUri = "http://localhost:8080/";
        Field instance = DiscoveryData.class.getDeclaredField("ourInstance");
        instance.setAccessible(true);
        instance.set(null,null);
        httpServer = NodeInstance.StartHttpServer(nodeUri);
        discoveryData = DiscoveryData.getInstance();
        discoveryNodeCom = new DiscoveryNodeCom(nodeUri);
    }

    @After
    public void tearDown() {
        httpServer.shutdownNow();
    }

    @Test
    public void setServerUri() {
        final String testUri = "http://testuri.com:8080";
        discoveryNodeCom.setServerUri(testUri);
        assertTrue(discoveryData.isInitialized());
        assertEquals(testUri, discoveryData.getServerUri());
    }

    @Test
    public void setNextNode() {
        final Integer testHash = NameHasher.Hash("nextNode");
        discoveryNodeCom.setNextNode(testHash);
        assertEquals(testHash, discoveryData.getNextNode());
    }

    @Test
    public void setPrevNode() {
        final Integer testHash = NameHasher.Hash(("prevNode"));
        discoveryNodeCom.setPrevNode(testHash);
        assertEquals(testHash, discoveryData.getPrevNode());
    }
}