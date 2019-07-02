package com.example.priti.smartbin;

public class SensorParsingValues {
private String mGarbageLevel;

    public String getLightLevel() {
        return mLightLevel;
    }

    public void setLightLevel(String lightLevel) {
        mLightLevel = lightLevel;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    private String mLightLevel;
    private String mTemperature;
    private String mHumidity;

    public String getGarbageLevel() {
        return mGarbageLevel;
    }

    public void setGarbageLevel(String garbageLevel) {
        mGarbageLevel = garbageLevel;
    }
}
