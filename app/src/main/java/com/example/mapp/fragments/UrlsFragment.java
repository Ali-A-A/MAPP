package com.example.mapp.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.WallpaperManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mapp.Api;
import com.example.mapp.R;
import com.example.mapp.UrlsController;

import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UrlsFragment extends Fragment {
    ImageView img;
//    RippleBackground rippleBackground;
    Button btn;
    String urlString = "";
    public AVLoadingIndicatorView progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_urls , container , false);
        findViews(v);
        showBtn();
        onClickNew();
//        rippleBackground.startRippleAnimation();
        onClickImg();
        setImage();
        return v;
    }

    private void setImage() {
        if(urlString == "") return;
//        new DownloadImageTask(img).execute(urlString);
        Picasso.with(getActivity()).load(urlString).resize(600,600).into(img , new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onError() {

            }
        });

    }

    private void showBtn() {
        ObjectAnimator alphaBtn = ObjectAnimator.ofFloat(btn , "alpha" , 0f , 1f);
        alphaBtn.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaBtn);
        animatorSet.start();
    }

    Api.GetUrls getUrls = new Api.GetUrls() {
        @Override
        public void onResponse(boolean successful, String u) {
            if(successful) {
                try{
//                    JSONObject jsonObject = new JSONObject(u);
//                    String url = jsonObject.getJSONObject("urls").getString("full");
                    JSONObject jsonObject = new JSONObject(u);
                    String url = jsonObject.getString("url");
                    Log.d("TSG" , url);

                    urlString = url;
                    setImage();
                } catch (Exception e) {}
            } else {
                Toast.makeText(getActivity() , "error" , Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String c) {
            Toast.makeText(getActivity() , "error" , Toast.LENGTH_SHORT).show();
        }
    };

    private void findViews(View v) {
        img = v.findViewById(R.id.image_view);
        btn = v.findViewById(R.id.btn_image);
        progressBar = v.findViewById(R.id.img_loader);
//        rippleBackground = v.findViewById(R.id.content_ripple_img);
    }

    private void onClickNew() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlsController urlsController = new UrlsController(getUrls);
                urlsController.start();
                progressBar.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Shake).playOn(img);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.INVISIBLE);
            bmImage.setImageBitmap(result);
        }
    }

    private void onClickImg() {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressBar.getVisibility() == View.VISIBLE || urlString == "") return;

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to set your wallpaper by this picture?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Glide.with(getActivity()).asBitmap()
                                        .load(urlString)
                                        .into(new CustomTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                try{
                                                    DisplayMetrics metrics = new DisplayMetrics();
                                                    WindowManager windowManager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
                                                    windowManager.getDefaultDisplay().getMetrics(metrics);
                                                    int height = metrics.heightPixels;
                                                    int width = metrics.widthPixels;
                                                    Bitmap bitmap = Bitmap.createScaledBitmap(resource,width,height, true);
                                                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
                                                    wallpaperManager.setWallpaperOffsetSteps(1, 1);
                                                    wallpaperManager.suggestDesiredDimensions(width, height);
                                                    try {
                                                        wallpaperManager.setBitmap(bitmap);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }catch(Exception e){
                                                    Log.d("Tag",e.toString());
                                                }
                                            }
                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {}
                                        });
                            }
                        })
                        .show();
            }
        });
    }
}
