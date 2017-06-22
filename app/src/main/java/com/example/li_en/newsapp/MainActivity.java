package com.example.li_en.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.li_en.newsapp.utilities.NetworkUtils;

import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;
    private TextView mUrlDisplayTextView;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_search_results);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        //mUrlDisplayTextView = (TextView) findViewById(R.id.url_display);
    }

    private void loadNewsData(){
        /* Was used to test if the URL was built correctly */
        //URL testNews = NetworkUtils.buildUrl(NetworkUtils.get_API_Key());
        //mUrlDisplayTextView.setText(testNews.toString());
        new FetchNewsTask().execute(NetworkUtils.get_API_Key());
    }



    public class FetchNewsTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){

            if(params.length == 0){
                return null;
            }

            String api = params[0];
            URL newsRequestUrl = NetworkUtils.buildUrl(api);

            String jsonNewsDataResponse = null;

            try{
                jsonNewsDataResponse = NetworkUtils.getResponseFromHttpUrl(newsRequestUrl);

            } catch (Exception e){
                e.printStackTrace();
            }

            return jsonNewsDataResponse;
        }

        @Override
        protected void onPostExecute(String newsData){
            bar.setVisibility(View.GONE);
            if(newsData != null && !newsData.equals("")) {
                mNewsTextView.setText(newsData);

//                for (String news : newsData){
//                    mNewsTextView.append(news + "\n\n");
//                }
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_search){
            mNewsTextView.setText("");
            loadNewsData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
