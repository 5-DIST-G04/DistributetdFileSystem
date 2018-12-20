package com.distributed.server;


import com.distributed.common.ComConf;
import com.distributed.common.NameHasher;

import java.io.IOException;

public class Main {
    public static void main(String[] Args) throws IOException {
        NameServer nameServer = new NameServer(new ComConf(8080,Args[0],"224.0.0.251", 3000));
        nameServer.Start();
        System.out.println("server running press enter to stop");
        System.in.read();
        //nameServer.Stop();
    }
}
