package com.example.li_en.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.li_en.newsapp.model.NewsItem;
import com.example.li_en.newsapp.utilities.NetworkUtils;
import com.example.li_en.newsapp.utilities.NewsJsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";

    //private TextView mUrlDisplayTextView;
    private RecyclerView recyclerView;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mUrlDisplayTextView = (TextView) findViewById(R.id.url_display);
    }

    private void loadNewsData(){
        /* Was used to test if the URL was built correctly */
        //URL testNews = NetworkUtils.buildUrl(NetworkUtils.get_API_Key());
        //mUrlDisplayTextView.setText(testNews.toString());

        new FetchNewsTask().execute(NetworkUtils.get_API_Key());
    }



    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>>{
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(String... params){
            ArrayList<NewsItem> articleList = null;

            if(params.length == 0){
                return null;
            }

            String api = params[0];
            URL newsRequestUrl = NetworkUtils.buildUrl(api);

            try{
                String jsonNewsDataResponse = NetworkUtils.getResponseFromHttpUrl(newsRequestUrl);
                articleList = NewsJsonUtils.parseJSON(jsonNewsDataResponse);

            } catch (IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }

            return articleList;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> articleList){
            super.onPostExecute(articleList);
            bar.setVisibility(View.GONE);

            if(articleList!= null) {
                NewsAdapter adapter = new NewsAdapter(articleList, new NewsAdapter.NewsClickListener() {
                    @Override
                    public void onNewsClick(int clickedItemIndex) {
                        String url = articleList.get(clickedItemIndex).getUrl();

                        Log.d(TAG, String.format("URL CLICKED: %s", url));

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_search){
            loadNewsData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
