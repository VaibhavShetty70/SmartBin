package com.example.priti.smartbin;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.priti.smartbin.SmartBinDbSchema.SmartBinTable;


public class SmartBinBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="smartBinBase.db";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+SmartBinTable.NAME;

    public SmartBinBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ SmartBinTable.NAME+"("+"_id integer primary key autoincrement,"+ SmartBinTable.Cols.UUID+", "+ SmartBinTable.Cols.BINID + ", "+ SmartBinTable.Cols.BINNAME + ", " + SmartBinTable.Cols.BININFO +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}