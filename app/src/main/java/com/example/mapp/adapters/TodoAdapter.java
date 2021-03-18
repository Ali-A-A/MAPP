package com.example.mapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapp.MyPreferenceManager;
import com.example.mapp.R;
import com.example.mapp.models.Todo;
import com.example.mapp.models.TodoList;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    Context context;
    List<Todo> items;
    View view;
    private int lastPosition = -1;
    public TodoAdapter(List<Todo> items , Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_todo , parent , false);
        context = parent.getContext();
        view = v;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.currTodo = items.get(position);
        if(holder.currTodo.isCompleted()) {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.imageView.setImageResource(R.drawable.ic_baseline_check_circle_24);
        } else {
            holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.imageView.setImageResource(R.drawable.tick);
        }
        for(int i = 0 ; i < getItemCount() ; i++) {
//            YoYo.with(Techniques.Shake).playOn(view);
            animate(view , i);
        }

    }

    private void animate(final View view, final int position){

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageButton imageButton;
        public ImageView imageView;
        public Todo currTodo;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.todo_titles);
            imageButton = itemView.findViewById(R.id.bt_delete);
            imageView = itemView.findViewById(R.id.bt_tick);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("todo_detail_click");
                    intent.putExtra("title" , currTodo.getTitle());
                    intent.putExtra("content" , currTodo.getContent());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to recover this file!")
                            .setConfirmText("Yes")
                            .setCancelText("No")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    TodoList todoList = MyPreferenceManager.getIns(context).getWorks();
                                    todoList.removeTodo(currTodo.getTitle());
                                    MyPreferenceManager.getIns(context).setWorks(todoList);
                                    Intent intent = new Intent("added_to_shared");
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                }
                            })
                            .show();
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TFDS" , "BOOL" + currTodo.isCompleted());
                    TodoList todoList = MyPreferenceManager.getIns(context).getWorks();
                    todoList.setComplete(currTodo.getTitle() , !currTodo.isCompleted());
                    MyPreferenceManager.getIns(context).setWorks(todoList);
                    Intent intent = new Intent("added_to_shared");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        }
    }
}
