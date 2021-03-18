package com.example.mapp.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapp.MyPreferenceManager;
import com.example.mapp.R;
import com.example.mapp.adapters.TodoAdapter;
import com.example.mapp.models.TodoList;
import com.skyfishjy.library.RippleBackground;


public class TodoFragment extends Fragment {

    Button newBtn;
//    RippleBackground rippleBackground;
    RecyclerView todos;
    TodoAdapter todoAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todo , container , false);
        findViews(v);
        showBtn();
//        rippleBackground.startRippleAnimation();
        initList();
        onNew();
        return v;
    }

    private void showBtn() {
        ObjectAnimator alphaBtn = ObjectAnimator.ofFloat(newBtn , "alpha" , 0f , 1f);
        alphaBtn.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaBtn);
        animatorSet.start();
    }

    private void findViews(View v) {
        newBtn = v.findViewById(R.id.new_btn);
        todos = v.findViewById(R.id.todos);
//        rippleBackground = v.findViewById(R.id.content_ripple_todo);
    }

    private void initList() {
        TodoList todoList = MyPreferenceManager.getIns(getActivity()).getWorks();
        todoAdapter = new TodoAdapter(todoList.getTodos() , getActivity());
        todos.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        todos.setAdapter(todoAdapter);
    }

    private void onNew() {
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("new_todo_click");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(br);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                br , new IntentFilter("added_to_shared")
        );
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initList();
            Log.d("UPA" , "update");
        }
    };
}
