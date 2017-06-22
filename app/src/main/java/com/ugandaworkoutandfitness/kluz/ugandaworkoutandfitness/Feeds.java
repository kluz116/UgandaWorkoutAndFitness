package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by kluz on 6/2/17.
 */

public class Feeds extends Fragment {
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedsAdapter adapter;
    private List<FitnessModel> articleList;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feeds_fragment, container, false);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swifeRefresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(mLayoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
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
        adapter = new FeedsAdapter(getActivity(),articleList);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void getFeeds(){
        mSwipeRefreshLayout.setRefreshing(true);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

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
