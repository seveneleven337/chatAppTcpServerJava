package com.chatApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Broadcast {

    static ArrayList<Socket> sockets = new ArrayList<Socket>();

    public static void update(String user, String msg){
        String message = " : " + msg;
        if(msg.contains("<EOF>")){
            message = " disconnected";
        }
        PrintWriter out = null;
        for(int i = 0; i < sockets.size() ;  i++){
            try {
                out = new PrintWriter(sockets.get(i).getOutputStream(), true);
                out.println(user + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void remove(Socket clientSocket) {
        for(int i = 0; i < sockets.size() ;  i++){
            if(sockets.get(i) == clientSocket) sockets.remove(i);
        }
    }
}
