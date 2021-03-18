package com.example.mapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mapp.MyPreferenceManager;
import com.example.mapp.R;
import com.example.mapp.models.Todo;
import com.example.mapp.models.TodoList;
import com.skyfishjy.library.RippleBackground;

public class NewTodoFragment extends Fragment {
    EditText title;
    EditText content;
    Button btn;
    RippleBackground rippleBackground;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_todo , container , false);
        findViews(v);
        rippleBackground.startRippleAnimation();
        onFocusEditText();
        onNew();
        return v;
    }

    private void onFocusEditText() {
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    YoYo.with(Techniques.RubberBand).playOn(title);
                }
            }
        });
        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    YoYo.with(Techniques.RubberBand).playOn(content);
                }
            }
        });
    }

    private void findViews(View v) {
        title = v.findViewById(R.id.title_edit);
        content = v.findViewById(R.id.content_edit);
        btn = v.findViewById(R.id.new_work_btn);
        rippleBackground = v.findViewById(R.id.content_ripple_new_todo);
    }

    private void onNew() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoList todoList = MyPreferenceManager.getIns(getActivity()).getWorks();
                Todo todo = new Todo(title.getText().toString() , content.getText().toString());
                todoList.addTodo(todo);
                MyPreferenceManager.getIns(getActivity()).setWorks(todoList);
                Intent intent = new Intent("added_to_shared");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                getActivity().onBackPressed();
            }
        });
    }
}
