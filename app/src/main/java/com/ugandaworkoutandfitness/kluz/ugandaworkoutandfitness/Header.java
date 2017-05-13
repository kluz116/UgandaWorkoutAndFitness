package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

import java.io.Serializable;

/**
 * Created by com.takethecorner.kluz Musembya on 18/05/16.
 */
public class Header implements Serializable {
    private int id;
    private String title;
    private  String thumbnail;



    public Header() {
    }

    public Header(int id, String title, String thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;

    }
    public int getId(){
        return  id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setSetTitle(String name) {
        this.title = name;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
