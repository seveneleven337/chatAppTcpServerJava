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
        broadcastMessage(user, message);
    }

    private static void broadcastMessage(String user, String message){
        PrintWriter out = null;
        if(message.contains("<ETF>")){
            String[] split = message.split("<ETF>");
            user = split[0].substring(3);
            for(int i=0; i<DataBase.users.size(); i++){
                if(DataBase.users.get(i).getUser().equals(user)){
                    System.out.println("en etf igual");
                    try {
                        out = new PrintWriter(sockets.get(i).getOutputStream(), true);
                        out.println("private message " + user + " : " + split[1]);
                        System.out.println("test");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            for(int i = 0; i < sockets.size() ;  i++){
                try {
                    out = new PrintWriter(sockets.get(i).getOutputStream(), true);
                    out.println(user + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void remove(Socket clientSocket) {
        for(int i = 0; i < sockets.size() ;  i++){
            if(sockets.get(i) == clientSocket) sockets.remove(i);
        }
    }
}
