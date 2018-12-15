package com.distributed.server;

import com.distributed.common.NameHasher;
import com.distributed.common.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class NamingDataTest {
    private NamingData namingData;

    @Before
    public void setUp() throws Exception {
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
}