package com.distributed.node;

import java.nio.file.Files;
import java.util.HashMap;

import com.distributed.common.*;

public class FileData {
    private String name;
    private boolean isFileOwner;
    private boolean shutdown;
    private boolean downloaded;
    private HashMap<String,String> fileLog;

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }




    public HashMap<String, String> getFileLog() {
        return fileLog;
    }

    public void setFileLog(HashMap<String, String> fileLog) {
        this.fileLog = fileLog;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public boolean isFileOwner() {
        return isFileOwner;
    }

    public void setFileOwner(boolean fileOwner) {
        isFileOwner = fileOwner;
    }

    public FileData(String name) {
        this.name=name;
    }

    public FileData(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHash() {
        return NameHasher.Hash(name);
    }

}
