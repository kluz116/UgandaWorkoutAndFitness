package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kluz on 5/4/17.
 */

public class FitnessDetails extends AppCompatActivity {
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.textView);
        TextView date_created = (TextView) findViewById(R.id.date);
        TextView author = (TextView) findViewById(R.id.author);
        TextView articl = (TextView) findViewById(R.id.textView2);
        ImageView thumbnail = (ImageView) findViewById(R.id.imageView);
        //setSupportActionBar(toolbar);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getSerializableExtra("article")!=null){
            FitnessModel article = (FitnessModel) getIntent().getSerializableExtra("article");
            title.setText(article.getTitle());
            author.setText(""+ article.getAuthor());
            articl.setText(article.getArticlel());
            Glide.with(this).load(article.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().crossFade().placeholder(R.drawable.images).into(thumbnail);
            try {
                long now = System.currentTimeMillis();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                Date convertedDate = dateFormat.parse(article.getDateCreated());

                CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                        convertedDate.getTime(),
                        now,
                        DateUtils.HOUR_IN_MILLIS);
                date_created.setText(relavetime1);

            }catch(ParseException e) {
                e.printStackTrace();
            }
            initCollapsingToolbar(article.getTitle() );
        }
    }
    private void initCollapsingToolbar( final String title) {

        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }

}

