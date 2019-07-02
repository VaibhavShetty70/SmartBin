package com.example.priti.smartbin;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonParsingSB {
    private static final String TAG = "JsonParsingSB";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }

    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<SensorParsingValues> parseSensorValues(List<String> values) {
        String uuid;
        List<SensorParsingValues> allSensorValues = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            uuid = values.get(i);
            allSensorValues.add(fetchItems(uuid));
        }
        return allSensorValues;
    }

    public SensorParsingValues fetchItems(String uuid) {

        SensorParsingValues items = new SensorParsingValues();
        SensorParsingValues sensorValue = new SensorParsingValues();
        try {
            String url = Uri.parse("https://dweet.io/get/latest/dweet/for/" + uuid)
                    .buildUpon()
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            sensorValue = parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return sensorValue;

    }

    private SensorParsingValues parseItems(SensorParsingValues items, JSONObject jsonBody)
            throws IOException, JSONException {

        JSONArray photoJsonArray = jsonBody.getJSONArray("with");
        JSONObject insideArrayJsonObject = photoJsonArray.getJSONObject(0);
//remember to change the key when updating in dweet
        JSONObject contentJsonObject = insideArrayJsonObject.getJSONObject("content");
        String garbageLevelValue = contentJsonObject.getString("garbageLevel");
        String lightLevelValue = contentJsonObject.getString("lightLevel");
        String humidityValue = contentJsonObject.getString("humidity");
        String temperatureValue = contentJsonObject.getString("temperature");

        SensorParsingValues item = new SensorParsingValues();
        item.setGarbageLevel(garbageLevelValue);
        item.setLightLevel(lightLevelValue);
        item.setTemperature(temperatureValue);
        item.setHumidity(humidityValue);

        return item;

    }

}
