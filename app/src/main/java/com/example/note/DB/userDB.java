package com.example.note.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Haoran Peng on 2020/3/24.
 */
public class userDB {

    Context context;
    userDBHelper dbHelper;
    public userDB(Context context){
        this.context = context;
        dbHelper = new userDBHelper(context,"user.db",null,1);
    }
    public void insertUser(String username,String password){
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        String sql="insert into user(username,password)values(?,?)";
        sqlDB.execSQL(sql,new String[] {username,password});
    }
    public Cursor query(String username, String password){
        SQLiteDatabase sqlDB= dbHelper.getReadableDatabase();
        String sql="select*from user where username=?and password=?";
        return sqlDB.rawQuery(sql,new String[]{username,password});
    }


}

class userDBHelper extends SQLiteOpenHelper {
    public userDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql= "create table if not exists user(id integer primary key autoincrement,username varchar(11),password varchar(18))";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
