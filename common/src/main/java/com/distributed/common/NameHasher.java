package com.distributed.common;

public final class NameHasher {
    public static int Hash(String s){
        return Math.abs(s.hashCode()) % 32768;
    }
}
