package com.njupt.b16070706.todo.Data;

import android.text.Editable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class Items {
    private String todo;

    private String date;

    private long timeStamp;

    private String uuid;
    public Items(){
        uuid = UUID.randomUUID().toString();
        Log.d("Item", uuid);
        timeStamp = System.currentTimeMillis();
        date = DateUtil.getFormattedDate();
        Log.d(TAG,date + "" + DateUtil.getFormattedTime(timeStamp));
    }

    public Items(String aTodo){
        this.todo = aTodo;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Items setTodo(Editable text) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        CharSequence todo = null;
        try {
            todo = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.todo = text.toString();
        return (Items) todo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}