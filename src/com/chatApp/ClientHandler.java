package com.chatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket)
    {
        this.clientSocket = socket;
    }

    public void run()
    {
        PrintWriter out = null;
        BufferedReader in = null;
        User newClient = new User("null");

        boolean firstMessage = true;
        try {
            newClient.setIP(clientSocket.getInetAddress().getHostAddress());
            System.out.println("New client connected " + newClient.getIP());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if(firstMessage) {
                    newClient.setUser(line);
                    firstMessage = false;
                } else {
                    System.out.println(newClient.getUser() + " :" + line);
                    Broadcast.update(newClient.getUser() , line);
                    //out.println(newClient.getUser() + " :" + line);
                }
            }
            out.println();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    Broadcast.remove(clientSocket);
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}