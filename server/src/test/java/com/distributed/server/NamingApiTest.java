package com.distributed.server;

import com.distributed.common.NameHasher;
import com.distributed.common.Node;
import com.distributed.node.NamingCom;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Response;
import org.junit.*;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.Assert.*;

public class NamingApiTest {
    private HttpServer httpServer;
    private final String uri = "http://localhost:8080/";
    private NamingCom namingCom;
    private NamingData namingData;

    @Before
    public void setUp() throws Exception{
        Field instance = NamingData.class.getDeclaredField("ourInstance");
        instance.setAccessible(true);
        instance.set(null,null);
        namingData = NamingData.getInstance();
        httpServer = NameServer.StartHttpServer(uri);
        namingCom = new NamingCom(uri);
        namingData.AddNode(new Node("testNode1","192.168.3.1"));
    }

    @After
    public void tearDown(){
        httpServer.shutdownNow();
    }

    @Test
    public void getIp() {
        Optional<String> ip = namingCom.getIpAddress(NameHasher.Hash("testNode1"));
        assertTrue(ip.isPresent());
        Assert.assertEquals("192.168.3.1",ip.get());
    }

    @Test
    public void addNode() {
        namingCom.registerNode(new Node("testNode2","192.168.3.4"));
        assertTrue(namingData.getNodeIp(NameHasher.Hash("testNode2")).isPresent());
        assertEquals("192.168.3.4", namingData.getNodeIp(NameHasher.Hash("testNode2")).get());
    }

    @Test
    public void removeNode() {
        namingCom.RemoveNode(NameHasher.Hash("testNode1"));
        assertTrue(namingData.getNodeIp(NameHasher.Hash("testNode1")).isEmpty());
    }

    @Test
    public void getFileLocation(){
        String fileName = "testFile.txt";
        Integer fileHash = NameHasher.Hash(fileName);
        for (int i = 1; i <= 10; i++) {
            namingData.AddNode(new Node(fileHash + i,"192.168.0." + i));
        }
        namingData.RemoveNode(NameHasher.Hash("testNode1"));
        Optional<Integer> location = namingCom.getFileLocation(fileName);
        assertTrue(location.isPresent());
        assertEquals((Integer)(fileHash + 10), location.get());

        for (int i = 1; i <= 10; i++) {
            namingData.RemoveNode(fileHash + i);
            namingData.AddNode(new Node(fileHash - i, "192.168.0." + i));
        }

        location = namingCom.getFileLocation(fileName);
        assertTrue(location.isPresent());
        assertEquals((Integer)(fileHash - 1), location.get());

    }
}