package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        Button addData = (Button) findViewById(R.id.add_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name","lxw");
                values.put("author","lxw");
                values.put("pages",454);
                values.put("price",0);
                //插入第一条数据
                db.insert("Book",null,values);
                // 开始组装第二条数据
                values.put("name","zxh");
                values.put("author","zxh");
                values.put("pages",455);
                values.put("price",1);
                //插入第二条数据
                db.insert("Book",null,values);
                Toast.makeText(MainActivity.this, "Add data succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[] { "lxw" });
                Toast.makeText(MainActivity.this, "Update data succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[] { "454" });
                Toast.makeText(MainActivity.this, "Delete data succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do{
                        // 遍历Cursor对象，取出数据并打印
                        @SuppressLint("Range")
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range")
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        @SuppressLint("Range")
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        @SuppressLint("Range")
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                Toast.makeText(MainActivity.this, "Query data succeeded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}