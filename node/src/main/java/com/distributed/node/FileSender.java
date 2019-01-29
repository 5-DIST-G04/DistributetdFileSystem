package com.distributed.node;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class FileSender extends Thread {
    private Socket socket;
    private String fileName;

    public FileSender(Socket socket, String fileName) {
        this.socket = socket;
        //log("New connection with client# " + clientNumber + " at " + socket);
    }

    @Override
    public void run() {

        try {
            OutputStream out = socket.getOutputStream();

            File file = new File("files/" + fileName);

            byte[] bytes = Files.readAllBytes(file.toPath());

            out.write(bytes);
            socket.close();


        } catch (IOException e) {
            //log("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }
            //log("Connection with client# " + clientNumber + " closed");
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
