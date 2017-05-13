package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Header_article extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.textView);
        TextView date_created = (TextView) findViewById(R.id.date);
        TextView author = (TextView) findViewById(R.id.textView2);
        ImageView thumbnail = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        String message = intent.getStringExtra("header_article");
        String image_preview = intent.getStringExtra("header_article_image");
        title.setText(message);

        Glide.with(Header_article.this).load(thumbnail).centerCrop().crossFade().placeholder(R.drawable.images).diskCacheStrategy(DiskCacheStrategy.ALL).into(thumbnail);

    }

}
