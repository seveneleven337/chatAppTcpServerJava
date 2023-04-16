package com.chatApp;

import java.io.*;
import java.net.*;

class Server {
    public static void main(String[] args)
    {
        ServerSocket server = null;

        try {
            server = new ServerSocket(6000);
            server.setReuseAddress(true);
            while (true) {
                Socket client = server.accept();
                //System.out.println("New client connected" + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client);
                Broadcast.sockets.add(client);
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
