package com.distributed.node;

import java.io.*;
import java.net.Socket;

public class FileReceiver extends Thread {
    private String fileName;
    private String ipAddress;
    private BufferedReader in;
    private PrintWriter out;



    public FileReceiver(String fileName, String ipAddress){
        this.fileName = fileName;
        this.ipAddress = ipAddress;
    }

    @Override
    public void run() {

        FileOutputStream fop = null;
        File file;
        String content = "This is the text content";


        try {
            Socket socket = new Socket("52.15.62.13", 16165);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);



            file = new File("c:/newfile.txt");
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
