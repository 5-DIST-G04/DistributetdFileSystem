package com.distributed.node;

import java.io.IOException;
import java.nio.file.*;

public class FileChangeListener extends Thread {
    WatchService watchSrvc;
    Path path;

    private DiscoveryData discoveryData = DiscoveryData.getInstance();
    private fileListener fileListener;

    public FileChangeListener(fileListener fileChannel){
        this.fileListener = fileChannel;
    }

    @Override
    public void run() {
        try {
            watchSrvc = FileSystems.getDefault().newWatchService();
            path = Paths.get("/home/pi/files");
            WatchKey watchKey = path.register(
                    watchSrvc, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchSrvc.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + ".");
                    handleChange(event.kind().toString(),event.context().toString());
                }
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void handleChange(String kind, String context){
        switch (kind){
            case "ENTRY_CREATE":{
                //do something when file is added
                fileListener.update(context);
            }break;
            case "ENTRY_DELETE":{
                //do something when file is deleted

            }break;
            case "ENTRY_MODIFY":{
                //do something when file is modified

            }break;
        }
    }

}
