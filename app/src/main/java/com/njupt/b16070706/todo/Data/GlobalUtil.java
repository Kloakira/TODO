package com.njupt.b16070706.todo.Data;

import android.content.Context;
import android.util.Log;

public class GlobalUtil {
    //设置全局资源提供,单例模式，节省资源
    private static final String TAG = "GlobalUtil";

    private static GlobalUtil instance;

    public ItemDatabaseHelper databaseHelper;

    public Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        databaseHelper = new ItemDatabaseHelper(context,ItemDatabaseHelper.DB_NAME,null,1);
    }

    public static GlobalUtil getInstance() {

        if(instance == null){
            instance = new GlobalUtil();
        }

        return instance;
    }


    public GlobalUtil(){
        Log.d(TAG,"globalUtil initialized");
        //new ItemDatabaseHelper()
    }
}
