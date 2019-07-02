package com.example.priti.smartbin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import  com.example.priti.smartbin.SmartBinDbSchema.SmartBinTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataLab {
    private static DataLab sDataLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DataLab get(Context context) {
        if (sDataLab == null) {
            sDataLab = new DataLab (context);
        }
        return sDataLab;
    }

    private DataLab(Context context) {
        mContext=context.getApplicationContext();
        mDatabase=new SmartBinBaseHelper(mContext).getWritableDatabase();
    }

    public List<Data> getallData(){
        List<Data> dataList = new ArrayList<>();

        SmartBinCursorWrapper cursor=queryCrimes(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                dataList.add(cursor.getData());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return dataList;
    }
    public List<String> getallUUID(){
        List<String> dataList = new ArrayList<>();

        SmartBinCursorWrapper cursor=queryUUID(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                dataList.add(cursor.getBinIdString());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return dataList;
    }

    public Data getData(UUID id) {
        SmartBinCursorWrapper cursor = queryCrimes(SmartBinTable.Cols.UUID + "= ?", new String[]{id.toString()});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getData();
        }
        finally
        {
            cursor.close();
        }
    }
    
    public void addData(Data data){
        ContentValues values=getContentValues(data);
        mDatabase.insert(SmartBinTable.NAME,null,values);
    }

    public void updateCrime(Data data){
        String uuidString=data.getId().toString();
        ContentValues values=getContentValues(data);
        mDatabase.update(SmartBinTable.NAME,values,SmartBinTable.Cols.UUID + "=?",new String[]{uuidString});
    }

    private SmartBinCursorWrapper queryCrimes(String whereClause,String [] whereArgs){
        Cursor cursor =mDatabase.query(SmartBinTable.NAME,null,whereClause,whereArgs,null,null,null);
        return new SmartBinCursorWrapper(cursor);
    }
    private SmartBinCursorWrapper queryUUID(String whereClause,String [] whereArgs){
        Cursor cursor =mDatabase.query(SmartBinTable.NAME,new String[]{SmartBinTable.Cols.BINID},whereClause,whereArgs,null,null,null);
        return new SmartBinCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Data data){
        ContentValues values = new ContentValues();
        values.put(SmartBinTable.Cols.UUID,data.getId().toString());
        values.put(SmartBinTable.Cols.BINID,data.getBinId());
        values.put(SmartBinTable.Cols.BINNAME,data.getBinName());
        values.put(SmartBinTable.Cols.BININFO,data.getBinInfo());
        return values;
    }
}
