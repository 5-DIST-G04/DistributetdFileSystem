package com.distributed.common;

public class ComConf {
    private final int httpPort;
    private final String HostIpAddress;
    private final String MulticastIp;
    private final int MulticastPort;

    public ComConf(int httpPort,String hostIpAddress, String multicastIp, int multicastPort){
        this.httpPort = httpPort;
        this.HostIpAddress = hostIpAddress;
        this.MulticastIp = multicastIp;
        this.MulticastPort = multicastPort;
    }

    public String getUri(String ip){
        return "http://" + ip + ":" + httpPort + "/";
    }

    public String getHostUri(){
        return getUri(HostIpAddress);
    }

    public String getHostIpAddress(){
        return HostIpAddress;
    }

    public String getMulticastIp() {
        return MulticastIp;
    }

    public int getMulticastPort() {
        return MulticastPort;
    }
}
