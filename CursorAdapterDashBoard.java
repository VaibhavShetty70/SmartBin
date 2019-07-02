package com.example.priti.smartbin;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CursorAdapterDashBoard extends CursorAdapter {
    private RequestQueue mqueue;
   protected Context context;
    String uid;
    TextView mDisplayValue;

    public CursorAdapterDashBoard (Context context, Cursor cursor) {
        super(context, cursor,0);
    }


    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_dashboard, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView mDisplayName = (TextView) view.findViewById(R.id.displayName);
       mDisplayValue= (TextView) view.findViewById(R.id.displayValue);
        // Extract properties from cursor
        this.context=context;
        uid=cursor.getString(cursor.getColumnIndexOrThrow("uid"));
      new GetUltrasonicSensor().execute(uid);
        String mName = cursor.getString(cursor.getColumnIndexOrThrow("binname"));
        //String mValue = cursor.getString(cursor.getColumnIndexOrThrow("bininfo"));

        // Populate fields with extracted properties
        mDisplayName.setText(mName);


    }
    private void jsonParse(String uid,Context context) {
        mqueue= Volley.newRequestQueue(context.getApplicationContext());
        String url="https://dweet.io/get/latest/dweet/for/"+uid;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jarray=response.getJSONArray("with");


                    JSONObject content=jarray.getJSONObject(0);
                    JSONObject subcontent=content.getJSONObject("content");
                    String hello=subcontent.getString("hello");
                    mDisplayValue.setText(String.valueOf(hello));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

       mqueue.add(request);

    }
    private class GetUltrasonicSensor extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... strings) {
              jsonParse(uid,context);
            return null;
        }
    }}
