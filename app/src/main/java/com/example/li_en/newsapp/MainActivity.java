package com.example.li_en.newsapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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

import com.example.li_en.newsapp.database.DBHelper;
import com.example.li_en.newsapp.database.DatabaseUtils;
import com.example.li_en.newsapp.tasks.FetchNews;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.NewsClickListener{
    static final String TAG = "mainactivity";

    //private TextView mUrlDisplayTextView;
    private RecyclerView recyclerView;
    private ProgressBar bar;
    private Cursor cursor;
    private SQLiteDatabase db;
    private NewsAdapter adapter;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mUrlDisplayTextView = (TextView) findViewById(R.id.url_display);
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        //adapter = new NewsAdapter(cursor, this);
    }

    @Override
    protected void onStop(){
        super.onStop();
        db.close();
        cursor.close();
    }



    private void loadNewsData(){
        /* Was used to test if the URL was built correctly */
        //URL testNews = NetworkUtils.buildUrl(NetworkUtils.get_API_Key());
        //mUrlDisplayTextView.setText(testNews.toString());

       // new FetchNewsTask().execute();
    }


    public Loader<Void> onCreateLoader(int id, final Bundle args){
        return new AsyncTaskLoader<Void>(this) {

            //Equivalent to AsyncTask's onPreExecute method
            @Override
            public void onStartLoading(){
                super.onStartLoading();
                bar.setVisibility(View.VISIBLE);
            }

            //Equivalent to AsyncTask's doInBackGround method
            @Override
            public Void loadInBackground() {
                FetchNews.fetchArticles(MainActivity.this);
                return null;
            }
        };
    }

    //Equivalent to AsyncTask's onPostExecute method
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        bar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Void> data){
    }



/*
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
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_search){
            load();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNewsClick(int clickedItem){
        //ahhhh Cursor
    }

    public void load(){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }

}

