package com.example.priti.smartbin;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;
import  com.example.priti.smartbin.SmartBinDbSchema.SmartBinTable;

public class SmartBinCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public SmartBinCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Data getData(){
        String uuidString=getString(getColumnIndex(SmartBinTable.Cols.UUID));
        String binId=getString(getColumnIndex(SmartBinTable.Cols.BINID));
        String binName=getString(getColumnIndex(SmartBinTable.Cols.BINNAME));
        String binInfo=getString(getColumnIndex(SmartBinTable.Cols.BININFO));
  ;

        Data data = new Data (UUID.fromString(uuidString));
        data.setBinId(binId);
        data.setBinName(binName);
        data.setBinInfo(binInfo);

        return data;
    }
    public String getBinIdString(){

        String binId=getString(getColumnIndex(SmartBinTable.Cols.BINID));

        return binId;
    }
}
