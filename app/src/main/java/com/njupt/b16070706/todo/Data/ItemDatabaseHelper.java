package com.njupt.b16070706.todo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

public class ItemDatabaseHelper extends SQLiteOpenHelper{

    private static String TAG = "ItemDatabaseHelper";

    public static final String DB_NAME = "todo";

    private static final String CREATE_TODO_DB = "create table todo ("
            + "id integer primary key autoincrement, "
            + "uuid text, "
            + "todo text, "
            + "time integer, "
            + "date date )";

    public ItemDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG,"DatabaseHelp init!");    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    //数据库中添加一条记录
    public void addTodo(Items item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",item.getUuid());
        values.put("todo",item.getTodo());
        values.put("date",item.getDate());
        values.put("time",item.getTimeStamp());
        db.insert(DB_NAME,null,values);
        values.clear();
        Log.d(TAG,item.getUuid()+" added");
    }
    //数据库中删除一条记录
    public void removeTodo(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }
    //编辑数据库中的一条记录
    public void editTodo(String uuid, Items item){
        removeTodo(uuid);
        item.setUuid(uuid);
        addTodo(item);
    }
    //查询数据库中的一条记录
    public LinkedList<Items> readTodo(String dateStr){
        LinkedList<Items> items = new LinkedList<>();
        //新建Cursor承接返回

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from todo where date = ? order by time asc",new String[]{dateStr});

    if(cursor.moveToFirst()){

        do{
            String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            String todo = cursor.getString(cursor.getColumnIndex("todo"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));

            Items item = new Items();
            item.setTodo(todo);
            item.setUuid(uuid);
            item.setDate(date);
            item.setTimeStamp(timeStamp);
            //单条todo加入List中
            items.add(item);

        }while (cursor.moveToNext());
    }
    cursor.close();
    return items;
}
    //按时间查询
    public LinkedList<String> getAvaliableDate(){

        LinkedList<String> dates = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from todo order by date asc",new String[]{});
        if(cursor.moveToFirst()){
            do{
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if(!dates.contains(date)){
                    dates.add(date);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }


}
