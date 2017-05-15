package com.zjgsu.shenyilin.management;

import android.app.Activity;
import android.app.ActivityManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends  AppCompatActivity {

    private ListView lv;
    private SqliteDBConnect sd;
    private static int page_size=6;
    private static int page_no=1,page_count=0,count=0;
    private Button btnAdd,btnFirst,btnEnd;
    private Button btnNext,btnPre;
    private SimpleAdapter sa;
    private ProgressBar m_ProgressBar;
    private ActivityManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setProgressBarVisibility(true);
        setContentView(R.layout.activity_main);
        am=ActivityManager.getInstance();
        am.addActivity(this);

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnFirst=(Button)findViewById(R.id.button_firstpage);
        btnPre=(Button)findViewById(R.id.button_previouspage);
        btnNext=(Button)findViewById(R.id.button_nextpage);
        btnEnd=(Button)findViewById(R.id.button_lastpage);

        m_ProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        lv=(ListView)findViewById(R.id.listview_note);
        sd=new SqliteDBConnect(MainActivity.this);
    }

    public void fenye(){
        SQLiteDatabase sdb=sd.getReadableDatabase();
        count=0;

        Cursor c1=sdb.query("note",new String[]{"noteId","noteName","noteTime"},null,null,null,null,"noteId asc");
        while (c1.moveToNext()){
            int noteid=c1.getInt(c1.getColumnIndex("noteId"));
            if(noteid>count)
                count=noteid;
        }
        c1.close();
        page_count=count%page_size==0?count/page_size:count/page_size+1;
        if (page_no<1)
            page_no=1;
        if (page_no>page_count)
            page_no=page_count;
        Cursor c=sdb.rawQuery("select noteId,noteName,noteTime from note limit ?,?",new String[]{(page_no -1)*page_size+"",page_size+""});
        List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();

        while (c.moveToNext()){
            Map<String,Object>map=new HashMap<String,Object>();
            String strName=c.getString(c.getColumnIndex("noteName"));
            if(strName.length()>20) {
                map.put("noteName", strName.substring(0, 20) + "…");
            }else{
                map.put("noteName",strName);
            }
            map.put("noteTime",c.getString(c.getColumnIndex("noteTime")));
            map.put("noteId",c.getInt(c.getColumnIndex("noteId")));
            list.add(map);
        }
        c.close();
        sdb.close();
        if(count>0){
            sa=new SimpleAdapter(MainActivity.this,list,R.layout.items,new String[]{"noteName","noteTime"},new int[]{R.id.text_notename,R.id.noteTime});
        }
    }

}

