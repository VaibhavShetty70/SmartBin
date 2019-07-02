package com.example.priti.smartbin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsFragment extends Fragment {
    private Switch mRemeberUserSwitch;
    private Switch mEnableMessageNotifySwitch;
    private EditText mAppPassword;
    private TextView mTextViewSeek;
    private SeekBar mSeekBar;
    private TextView mInputNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mRemeberUserSwitch=v.findViewById(R.id.switch_remember_user);
        mRemeberUserSwitch.setChecked(QueryPreferences.isRemeberUserAndPassword(getActivity()));
        mRemeberUserSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QueryPreferences.setIsRemeberUserAndPassword(getActivity(),isChecked);
            }
        });

        mInputNumber=v.findViewById(R.id.input_number);
        mInputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                QueryPreferences.setMessageNumber(getActivity(),s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mEnableMessageNotifySwitch=v.findViewById(R.id.enable_message_notify);
        mEnableMessageNotifySwitch.setChecked(QueryPreferences.isMessageEnabledNotify(getActivity()));
        mEnableMessageNotifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QueryPreferences.setMessageEnabledNotify(getActivity(),isChecked);
                if(isChecked){
                    mInputNumber.setVisibility(View.VISIBLE);
                    mInputNumber.setText(QueryPreferences.getMessageNumber(getActivity()));
                }
                else {
                    mInputNumber.setVisibility(View.GONE);
                }
            }
        });

        if(mEnableMessageNotifySwitch.isChecked()){
            mInputNumber.setVisibility(View.VISIBLE);
            mInputNumber.setText(QueryPreferences.getMessageNumber(getActivity()));
        }
        else {
            mInputNumber.setVisibility(View.GONE);
        }

        mAppPassword=v.findViewById(R.id.editText_app_password);
        mAppPassword.setText(QueryPreferences.getAppPassword(getActivity()));
        mAppPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
QueryPreferences.setAppPassword(getActivity(),s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTextViewSeek=v.findViewById(R.id.textViewseek);

        mSeekBar=v.findViewById(R.id.seekBar5);
        mSeekBar.setProgress(QueryPreferences.getGarbageLevelLimit(getActivity())/10);
        mTextViewSeek.setText(String.valueOf(QueryPreferences.getGarbageLevelLimit(getActivity())));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextViewSeek.setText(String.valueOf(progress * 10));
                QueryPreferences.setGarbageLevelLimit(getActivity(),progress * 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return v;
    }
}
