package com.distributed.server;

import com.distributed.common.CommonClass;

public class Main {
    public static void main(String[] Args){
        System.out.println("message from server");
        CommonClass cc = new CommonClass();
        cc.Print();
    }
}
