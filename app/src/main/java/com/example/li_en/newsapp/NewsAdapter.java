package com.example.li_en.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.li_en.newsapp.model.NewsItem;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private ArrayList<NewsItem> articlesList;
    NewsClickListener listener;

    //Constructor of NewsAdapter
    public NewsAdapter(ArrayList<NewsItem> articlesList, NewsClickListener listener){
        this.articlesList = articlesList;
        this.listener = listener;

    }

    public interface NewsClickListener{
        void onNewsClick(int clickedNewsIndex);
    }


    //Creates each of the ViewHolders to display on the screen
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //gets the context of the current activity displayed on the screen
        Context context = viewGroup.getContext();

        //obtains the views from the xml file
        LayoutInflater inflater = LayoutInflater.from(context);

        //used to make any layout changes
        //if true, it will return to root object which won't show any changes made
        //for false: child views are inflated in onCreateViewHolder()
        boolean attachToParentImmediately = false;

        //used to instantiate the layout xml file into actual View objects
        View view = inflater.inflate(R.layout.news_article, viewGroup, attachToParentImmediately);

        NewsHolder holder = new NewsHolder(view);

        return holder;
    }

    //Displays the data information of an article at a specified position given as a parameter
    @Override
    public void onBindViewHolder(NewsHolder newsHolder, int position){
        newsHolder.bind(position);
    }

    //Returns the size of arrayList holding all our articles
    @Override
    public int getItemCount(){
        return articlesList == null ? 0 : articlesList.size();
    }



    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitleText;
        TextView mDescriptionText;
        TextView mTimeText;

        public NewsHolder(View view){
            super(view);

            //Getting references of the id's from the news_article.xml file
            mTitleText = (TextView) view.findViewById(R.id.news_title);
            mTimeText = (TextView) view.findViewById(R.id.news_time);
            mDescriptionText = (TextView) view.findViewById(R.id.news_description);

            view.setOnClickListener(this);
        }

        //Responsible for getting the position of a specific article and setting the views
        //corresponding to that article
        public void bind(int position){
            //gets the position of the specific article
            NewsItem news = articlesList.get(position);

            //sets the text of each view according to the specific position gotten above
            mTitleText.setText(news.getTitle());
            mDescriptionText.setText(news.getDescription());
            mTimeText.setText(news.getPublishedAt());
        }


        @Override
        public void onClick(View view){
            int pos = getAdapterPosition();
            listener.onNewsClick(pos);
        }
    }

}
