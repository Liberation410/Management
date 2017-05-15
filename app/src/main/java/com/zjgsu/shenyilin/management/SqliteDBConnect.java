package com.zjgsu.shenyilin.management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDBConnect extends SQLiteOpenHelper{
    public SqliteDBConnect(Context context){
        super(context,"NotePad",null,1);
    }
   @Override
    public void onCreate(SQLiteDatabase db){
        System.out.println("Table before Create");
        db.execSQL("create table note(noteId Integer primary key,noteName varchar(20),noteTime verchar(20),noteContent verchar(400)");
        System.out.println("Table afer Create");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }

}
