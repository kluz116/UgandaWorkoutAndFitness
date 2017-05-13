package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;


/**
 * Created by kluz on 5/2/17.
 */





        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
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
 * Created by kluz on 11/26/16.
 */
public class Fitness extends Fragment {

    private FitnessAdapter adapter;
    private List<FitnessModel> articleList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        articleList = new ArrayList<>();
        adapter = new FitnessAdapter(getActivity(), articleList);
        recyclerView.setAdapter(adapter);


                getArticlesFitness();



        return view;
        // Progress dialog
        //progressDialog = new ProgressDialog;
        //progressDialog.setCancelable(false);
    }
    private void getArticlesFitness(){


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

