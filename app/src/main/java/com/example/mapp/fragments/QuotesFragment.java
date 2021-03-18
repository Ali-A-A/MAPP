package com.example.mapp.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mapp.Api;
import com.example.mapp.QuoteController;
import com.example.mapp.R;
import com.example.mapp.models.Quot;

import com.skyfishjy.library.RippleBackground;
import com.wang.avi.AVLoadingIndicatorView;


public class QuotesFragment extends Fragment {

    TextView quote;
    Button btn;
    AVLoadingIndicatorView progressBar;
//    RippleBackground rippleBackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quotes , container , false);
        findViews(v);
        showBtn();
//        rippleBackground.startRippleAnimation();
        onClickQuote();
        return v;
    }

    private void showBtn() {
        ObjectAnimator alphaBtn = ObjectAnimator.ofFloat(btn , "alpha" , 0f , 1f);
        alphaBtn.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaBtn);
        animatorSet.start();
    }

    Api.GetQuot getQuot = new Api.GetQuot() {
        @Override
        public void onResponse(boolean successful, Quot s) {
            if(successful) {
                quote.setText(s.getContent());
                progressBar.setVisibility(View.INVISIBLE);
                YoYo.with(Techniques.ZoomIn).playOn(quote);
            }
        }

        @Override
        public void onFailure(String c) {
            quote.setText("We can't connect to the server. Please check your internet connection");
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private void findViews(View v) {
        quote = v.findViewById(R.id.quote);
        btn = v.findViewById(R.id.quote_btn);
        progressBar = v.findViewById(R.id.progress_bar);
//        rippleBackground = v.findViewById(R.id.content_ripple);
    }

    private void onClickQuote() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuoteController quoteController = new QuoteController(getQuot);
                quoteController.star();
                progressBar.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomOut).playOn(quote);
            }
        });
    }
}
