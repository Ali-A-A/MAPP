package com.example.mapp.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mapp.R;

public class TodoDetail extends Fragment {

    TextView title;
    TextView content;
    String titleStr;
    String contentStr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail , container , false);
        findViews(v);
        readArgs();
        setTexts();
        return v;
    }

    private void findViews(View v) {
        title = v.findViewById(R.id.title_detail);
        content = v.findViewById(R.id.body_detail);
        content.setMovementMethod(new ScrollingMovementMethod());
    }
    private void readArgs() {
        titleStr = getArguments().getString("title" , null);
        contentStr = getArguments().getString("content" , null);
    }
    private void setTexts() {
        title.setText(titleStr);
        content.setText(contentStr);
        YoYo.with(Techniques.ZoomIn).playOn(title);
        YoYo.with(Techniques.ZoomIn).playOn(content);
    }
}
