package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mroot;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedsAdapter adapter;
    private List<FitnessModel> articleList;
    private BottomNavigationView bottomNavigation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mroot = (RelativeLayout) findViewById(R.id.fitness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                getFeeds();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeeds();
            }
        });

        articleList = new ArrayList<>();
        adapter = new FeedsAdapter(this,articleList);
        recyclerView.setAdapter(adapter);


        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_search:
                        //Intent i = new Intent(MainActivity.this,SellItem.class);
                        //startActivity(i);
                        break;
                    case R.id.action_cart:
                        Intent i = new Intent(MainActivity.this,SellItem.class);
                        startActivity(i);

                        break;
                    case R.id.action_hot_deals:

                        break;

                }


                return true;
            }
        });



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
        int id = item.getItemId();

      if(id==R.id.buy_item){
            Intent i = new Intent(getApplicationContext(),BuyItem.class);
            startActivity(i);

        }else if(id==R.id.sell_item){
            Intent i = new Intent(getApplicationContext(),SellItem.class);
            startActivity(i);

        }else if(id==R.id.search_item){

        }

        return super.onOptionsItemSelected(item);
    }
    private void getFeeds(){
        mSwipeRefreshLayout.setRefreshing(true);
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://takethecorner.com/get_fitness_articles.php";
        JsonArrayRequest arrayreq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {


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
                        mSwipeRefreshLayout.setRefreshing(false);

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

}
