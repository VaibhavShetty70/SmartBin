package com.example.priti.smartbin;

import java.util.UUID;

public class Data {

    private UUID mId;
private String mBinId;
private String mBinName;
private String mBinInfo;

    public Data() {
        this(UUID.randomUUID());
    }

    public Data (UUID id){
        mId=id;
    }

    public UUID getId() {
        return mId;
    }

    public String getBinId() {
        return mBinId;
    }

    public void setBinId(String binId) {
        mBinId = binId;
    }

    public String getBinName() {
        return mBinName;
    }

    public void setBinName(String binName) {
        mBinName = binName;
    }

    public String getBinInfo() {
        return mBinInfo;
    }

    public void setBinInfo(String binInfo) {
        mBinInfo = binInfo;
    }
}
