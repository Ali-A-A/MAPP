package com.example.mapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.example.mapp.adapters.MyFragmentPagerAdapter;
import com.example.mapp.fragments.NewTodoFragment;
import com.example.mapp.fragments.TodoDetail;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        openViewPager();
    }

    private void findViews() {
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.view_pager);
    }

    private void openViewPager() {
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true , new RotateDownTransformer());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    private void openNewTodoFragment() {
        NewTodoFragment newTodoFragment = new NewTodoFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left , android.R.anim.slide_out_right).add(R.id.frame , newTodoFragment).addToBackStack(null).commit();
    }

    private void openDetailFragment(Bundle bundle) {
        TodoDetail todoDetail = new TodoDetail();
        todoDetail.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left , android.R.anim.slide_out_right).add(R.id.frame , todoDetail).addToBackStack(null).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(newTodoBroadcast);
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(detailBroadcast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                broadcastReceiver , new IntentFilter(("bmi_click"))
        );
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                newTodoBroadcast , new IntentFilter(("new_todo_click"))
        );
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                detailBroadcast , new IntentFilter("todo_detail_click")
        );
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            viewPager.setCurrentItem(intent.getIntExtra("index" , 0));
        }
    };

    private BroadcastReceiver newTodoBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            openNewTodoFragment();
        }
    };

    private BroadcastReceiver detailBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = new Bundle();
            bundle.putString("title" , intent.getStringExtra("title"));
            bundle.putString("content" , intent.getStringExtra("content"));
            openDetailFragment(bundle);
        }
    };
}