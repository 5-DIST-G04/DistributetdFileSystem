package com.distributed.node;

import com.distributed.common.ComConf;
import com.distributed.common.CommonClass;

import java.io.IOException;

public class Main {
    public static void main(String[] Args) throws IOException {
        NodeInstance nodeInstance = new NodeInstance(new ComConf(8080,Args[0],"224.0.0.251",3000));
        nodeInstance.Start();
        System.in.read();
        //nodeInstance.Stop();

    }
}
