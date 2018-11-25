package com.njupt.b16070706.todo.listview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Items {
    private String todo;

    public Items(){
    }

    public Items(String aTodo){
        this.todo = aTodo;
    }

    public String getTodo() {
        return todo;
    }

    public Items setTodo() {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        CharSequence todo = null;
        try {
            todo = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.todo = todo.toString();
        return (Items) todo;
    }

}
