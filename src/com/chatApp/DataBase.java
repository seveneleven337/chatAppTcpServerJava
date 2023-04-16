package com.chatApp;

import java.util.ArrayList;
import java.util.Observable;

public class DataBase extends Observable {

    static ArrayList<String> messages = new ArrayList<String>();

    static ArrayList<User> users = new ArrayList<User>();

}
