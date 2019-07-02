package com.example.priti.smartbin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class BinManagerFragment extends Fragment {
    private RecyclerView mHomeRecyclerView;
    private BinManagerFragment.BinMangerDesignAdapter mAdapter;
    private static final int REQUEST_CODE=0;
    private Button mbtndialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview, container, false);

        mHomeRecyclerView = v.findViewById(R.id.home_recycler_view);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mbtndialog = (Button) v.findViewById(R.id.showDialog);
        mbtndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new CustomAddBinDialog();
                dialog.setTargetFragment(BinManagerFragment.this,REQUEST_CODE);
                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        updateUI();
        return v;
    }

    private class BinManagerDesignHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Data mData;
        private TextView mBinNameTextView;
        private TextView mBinInfoTextView;
        private TextView mBinIdTextView;

        public BinManagerDesignHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.add_bin, parent, false));

            mBinNameTextView = itemView.findViewById(R.id.bm_name);

            mBinInfoTextView = itemView.findViewById(R.id.bm_info);

            mBinIdTextView=itemView.findViewById(R.id.bm_id);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Data data) {
            mData = data;
            mBinNameTextView.setText(mData.getBinName());
            mBinInfoTextView.setText(mData.getBinInfo());
            mBinIdTextView.setText("ID: "+mData.getBinId());
        }
    }

    private class BinMangerDesignAdapter extends RecyclerView.Adapter<BinManagerDesignHolder> {

        private List<Data> mDataList;

        public BinMangerDesignAdapter(List<Data> dataList) {
            mDataList = dataList;
        }

        @Override
        public BinManagerDesignHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BinManagerDesignHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BinManagerDesignHolder designHolder, int i) {
            Data data = mDataList.get(i);
            designHolder.bind(data);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public void setDataList(List<Data> dataList) {
            mDataList = dataList;
        }
    }

    private void updateUI() {
        DataLab dataLab = DataLab.get(getActivity());
        List<Data> dataList = dataLab.getallData();
        if (mAdapter == null) {
            mAdapter = new BinMangerDesignAdapter(dataList);
            mHomeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDataList(dataList);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE){
            updateUI();
        }
    }
}
