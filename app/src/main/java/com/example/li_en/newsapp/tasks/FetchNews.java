package com.example.li_en.newsapp.tasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.li_en.newsapp.database.DBHelper;
import com.example.li_en.newsapp.database.DatabaseUtils;
import com.example.li_en.newsapp.model.NewsItem;
import com.example.li_en.newsapp.utilities.NetworkUtils;
import com.example.li_en.newsapp.utilities.NewsJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by li-en on 7/23/17.
 */

public class FetchNews {
    //method to fetch the articles from the API and store them in the database
    public static void fetchArticles(Context context){
        ArrayList<NewsItem>  articles = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try{
            //clear the database of its current data
            DatabaseUtils.deleteAll(db);

            //Grabs results from the API and parses the JSON
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            articles = NewsJsonUtils.parseJSON(json);

            //insert the json results of the articles into the database
            DatabaseUtils.bulkInsert(db, articles);

        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
