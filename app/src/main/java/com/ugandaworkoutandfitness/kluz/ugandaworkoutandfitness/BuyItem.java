package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by kluz on 5/20/17.
 */

public class BuyItem extends Fragment {
    Button button;
    EditText item_name,item_comment,item_contact;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_item, container, false);


        button = (Button) view.findViewById(R.id.button);
        item_name = (EditText) view.findViewById(R.id.editText);
        item_comment = (EditText) view.findViewById(R.id.editText4);
        item_contact = (EditText) view.findViewById(R.id.editText5);
        //button.view.setOnClickListener(getActivity());

        return view;
    }




}
