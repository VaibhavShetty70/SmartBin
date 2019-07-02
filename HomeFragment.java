package com.example.priti.smartbin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class HomeFragment extends Fragment {
    private ProgressBar mLoadProgress;
    private RecyclerView mHomeRecyclerView;
    private DesignAdapter mAdapter;
    private List<String> allBinId;
    private List<SensorParsingValues> mSensorValues;
    private boolean sendOnce = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allBinId = DataLab.get(getActivity()).getallUUID();
        BinService.setServiceAlarm(getActivity(), true);
        new GetJSONSensorData().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mLoadProgress = v.findViewById(R.id.loading_progrees_bar);

        mHomeRecyclerView = v.findViewById(R.id.home_recycler_view);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  updateUI();
        return v;
    }

    private class DesignHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Data mData;
        private SensorParsingValues mSensorValue;
        private TextView mBinNameTextView;
        private TextView mBinInfoTextView;
        private TextView mGarbageLevel;
        private TextView mLightLevel;
        private TextView mHumidity;
        private TextView mTemperature;

        public DesignHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_design_home, parent, false));

            mBinNameTextView = itemView.findViewById(R.id.bin_name);

            mBinInfoTextView = itemView.findViewById(R.id.bin_info);

            mGarbageLevel = itemView.findViewById(R.id.garbage_value);

            mLightLevel = itemView.findViewById(R.id.light_value);

            mHumidity = itemView.findViewById(R.id.humidity_value);

            mTemperature = itemView.findViewById(R.id.temperature_value);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Data data, SensorParsingValues sensorValue) {
            mData = data;
            mSensorValue = sensorValue;
            mBinNameTextView.setText(mData.getBinName());
            mBinInfoTextView.setText(mData.getBinInfo());
            mGarbageLevel.setText(mSensorValue.getGarbageLevel());
            mLightLevel.setText(mSensorValue.getLightLevel());
            mHumidity.setText(mSensorValue.getHumidity()+"%");
            mTemperature.setText(mSensorValue.getTemperature()+"C");

            if (sendOnce) {
                if (QueryPreferences.isMessageEnabledNotify(getActivity())) {
                    if (Integer.parseInt(mSensorValue.getGarbageLevel()) < QueryPreferences.getGarbageLevelLimit(getActivity())) {
                        sendOnce = false;
                        sendMessage(QueryPreferences.getMessageNumber(getActivity()), mData, mSensorValue);
                    }
                }
            }

        }
    }

    private class DesignAdapter extends RecyclerView.Adapter<DesignHolder> {

        private List<Data> mDataList;
        private List<SensorParsingValues> mSensorValueList;

        public DesignAdapter(List<Data> dataList, List<SensorParsingValues> sensorValueList) {
            mDataList = dataList;
            mSensorValueList = sensorValueList;
        }

        @Override
        public DesignHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DesignHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DesignHolder designHolder, int i) {
            Data data = mDataList.get(i);
            SensorParsingValues sensorValue = mSensorValueList.get(i);
            designHolder.bind(data, sensorValue);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public void setDataList(List<Data> dataList, List<SensorParsingValues> mSensorValues) {
            mDataList = dataList;
            mSensorValueList = mSensorValues;
        }
    }

    private void updateUI() {
        DataLab dataLab = DataLab.get(getActivity());
        List<Data> dataList = dataLab.getallData();

        mLoadProgress.setVisibility(View.GONE);
        mHomeRecyclerView.setVisibility(View.VISIBLE);

        if (mAdapter == null) {
            mAdapter = new DesignAdapter(dataList, mSensorValues);
            mHomeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDataList(dataList, mSensorValues);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class GetJSONSensorData extends AsyncTask<Void, Void, List<SensorParsingValues>> {


        @Override
        protected List<SensorParsingValues> doInBackground(Void... params) {

            return new JsonParsingSB().parseSensorValues(allBinId);

        }

        @Override
        protected void onPostExecute(List<SensorParsingValues> sensorParsingValues) {
            mSensorValues = sensorParsingValues;
            updateUI();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new GetJSONSensorData().execute();
                }
            }, 2000);
        }
    }

    public void sendMessage(String number, Data mData, SensorParsingValues mSensorValues) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        } else {

            String message = mData.getBinName() + " has reached its level" + mSensorValues.getGarbageLevel() + "\n Info: " + mData.getBinInfo();
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number, null, message, null, null);
        }
    }
}


