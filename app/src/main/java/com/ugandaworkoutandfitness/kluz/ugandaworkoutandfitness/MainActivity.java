package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FitnessAdapter adapter;
    private List<FitnessModel> articleList;
    ProgressDialog progressDialog;
    TextView header;
    ImageView banner;
    RelativeLayout mroot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mroot = (RelativeLayout) findViewById(R.id.fitness);
        header = (TextView) findViewById(R.id.header);
        banner = (ImageView) findViewById(R.id.backdrop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        articleList = new ArrayList<>();
        adapter = new FitnessAdapter(this, articleList);
        recyclerView.setAdapter(adapter);




        if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {
            getArticlesFitness();
            getArticles();
        }
        else
        {
            Snackbar snack = Snackbar.make(mroot,"Ooops, Please Check Your Internet Connection.....",Snackbar.LENGTH_LONG);
            snack.show();
        }

        setSupportActionBar(toolbar);
        initCollapsingToolbar( );

    }

    private void getArticlesFitness(){
        progressDialog.setMessage("Loading...");
        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://takethecorner.com/get_fitness_articles.php";
        JsonArrayRequest arrayreq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        hideDialog();

                        int count = 0;
                        while (count<response.length()){
                            try {
                                JSONObject obj = response.getJSONObject(count);

                                FitnessModel a = new FitnessModel(obj.getInt("id"),obj.getString("heading"), obj.getString("author"), obj.getString("image"),obj.getString("article"),obj.getString("date_created"));

                                articleList.add(a);
                                adapter.notifyDataSetChanged();


                            }

                            catch (JSONException e) {

                                e.printStackTrace();
                            }
                            count++;
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        hideDialog();
                    }
                }
        );

        queue.add(arrayreq);


    }

    public  void getArticles(){

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://takethecorner.com/get_header.php";
        JsonArrayRequest arrayreq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        String data = "";
                        int count = 0;
                        while (count<response.length()){
                            try {
                                JSONObject obj = response.getJSONObject(count);
                                int rank = obj.getInt("id");
                                header.setText(obj.getString("heading"));
                                String image_url = obj.getString("image");
                                Glide.with(MainActivity.this).load(image_url).centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(banner);


                            }

                            catch (JSONException e) {

                                e.printStackTrace();
                            }
                            count++;
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        queue.add(arrayreq);


    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initCollapsingToolbar( ) {

        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
