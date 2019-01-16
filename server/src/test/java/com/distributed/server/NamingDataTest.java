package com.distributed.server;

import com.distributed.common.NameHasher;
import com.distributed.common.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class NamingDataTest {
    private NamingData namingData;

    @Before
    public void setUp() throws Exception {
        Field instance = NamingData.class.getDeclaredField("ourInstance");
        instance.setAccessible(true);
        instance.set(null,null);
        namingData = NamingData.getInstance();
        namingData.AddNode(new Node(123,"192.168.0.1"));
    }

    @After
    public void tearDown(){
        namingData.RemoveNode(123);
    }

    @Test
    public void getNodeIp() {
        Optional<String> ip = namingData.getNodeIp(123);
        assertTrue(ip.isPresent());
        assertEquals(ip.get(), "192.168.0.1");
    }

    @Test
    public void removeNode() {
        namingData.RemoveNode(123);
        assertTrue(namingData.getNodeIp(123).isEmpty());
    }

    @Test
    public void getFileLocation() {
        String fileName = "testFile.txt";
        Integer fileHash = NameHasher.Hash(fileName);
        for (int i = 1; i <= 10; i++) {
            namingData.AddNode(new Node(fileHash + i,"192.168.0." + i));
        }
        namingData.RemoveNode(123);
        Optional<Integer> location = namingData.getFileLocation(fileName);
        assertTrue(location.isPresent());
        assertEquals((Integer)(fileHash + 10), location.get());

        for (int i = 1; i <= 10; i++) {
            namingData.RemoveNode(fileHash + i);
            namingData.AddNode(new Node(fileHash - i, "192.168.0." + i));
        }

        location = namingData.getFileLocation(fileName);
        assertTrue(location.isPresent());
        assertEquals((Integer)(fileHash - 1), location.get());

    }

    @Test
    public void getNeighbours() {
        for (int i = 0; i < 10; i++) {
            namingData.AddNode(new Node(i,"192.168.0." + i));
        }
        namingData.RemoveNode(123);
        Optional<Integer> nextNeigbour = namingData.getNextNeigbour(5);
        assertTrue(nextNeigbour.isPresent());
        assertEquals((Integer)6,nextNeigbour.get());
        Optional<Integer> prevNeigbour = namingData.getPreviousNeighbour(5);
        assertTrue(prevNeigbour.isPresent());
        assertEquals((Integer)4,prevNeigbour.get());
    }
}