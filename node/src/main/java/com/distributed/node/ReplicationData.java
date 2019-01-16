package com.distributed.node;

public class ReplicationData {
    private static ReplicationData ourInstance;

    public static ReplicationData getInstance(){
        if(ourInstance == null){
            ourInstance = new ReplicationData();
        }
        return ourInstance;
    }

    private ReplicationData(){

    }



}
