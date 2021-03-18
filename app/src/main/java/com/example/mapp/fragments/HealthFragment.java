package com.example.mapp.fragments;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mapp.R;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

public class HealthFragment extends Fragment {


    BoomMenuButton boomMenuButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_health , container , false);
        findViews(v);

        builder();
        return v;
    }

    private void builder() {
        new BuilderAsync().execute();
    }

    private void findViews(View v) {
        boomMenuButton = v.findViewById(R.id.bmb);
    }

    private class BuilderAsync extends AsyncTask<Void , Void , Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            boomMenuButton.addBuilder(new HamButton.Builder().normalText("Quotes").listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Intent intent = new Intent("bmi_click");
                    intent.putExtra("index" , 1);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }));
            boomMenuButton.addBuilder(new HamButton.Builder().normalText("Works").listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Intent intent = new Intent("bmi_click");
                    intent.putExtra("index" , 2);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }));
            boomMenuButton.addBuilder(new HamButton.Builder().normalText("Wallpaper").listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Intent intent = new Intent("bmi_click");
                    intent.putExtra("index" , 3);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }));
            return null;
        }
    }
}
