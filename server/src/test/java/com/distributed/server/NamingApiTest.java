package com.distributed.server;

import com.distributed.common.NameHasher;
import com.distributed.common.Node;
import com.distributed.node.NamingCom;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Response;
import org.junit.*;

import java.util.Optional;

import static org.junit.Assert.*;

public class NamingApiTest {
    private static HttpServer httpServer;
    private static final String uri = "http://localhost:8080/";
    private static NamingCom namingCom;
    private static NamingData namingData = NamingData.getInstance();

    @BeforeClass
    public static void setUp(){
        httpServer = NameServer.StartHttpServer(uri);
        namingCom = new NamingCom(uri);
        namingData.AddNode(new Node("testNode","192.168.3.1"));
    }

    @AfterClass
    public static void tearDown(){
        httpServer.shutdownNow();
    }

    @Test
    public void getIp() {
        Optional<String> ip = namingCom.getIpAddress(NameHasher.Hash("testNode"));
        assertTrue(ip.isPresent());
        Assert.assertEquals(ip.get(),"192.168.3.1");
    }

    @Test
    public void addNode() {
        namingCom.registerNode(new Node("testNode2","192.168.3.4"));
        assertTrue(namingData.getNodeIp(NameHasher.Hash("testNode2")).isPresent());
        assertEquals(namingData.getNodeIp(NameHasher.Hash("testNode2")).get(),"192.168.3.4");
    }

    @Test
    public void removeNode() {
        namingCom.RemoveNode(NameHasher.Hash("testNode"));
        assertTrue(namingData.getNodeIp(NameHasher.Hash("testNode")).isEmpty());
    }
}