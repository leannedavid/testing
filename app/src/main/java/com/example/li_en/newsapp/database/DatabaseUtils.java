package com.example.li_en.newsapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.li_en.newsapp.model.NewsItem;
import java.util.ArrayList;
import static com.example.li_en.newsapp.database.Contract.TABLE_ARTICLES.*;

public class DatabaseUtils {
    //Query that gets all the items in the table and sort them by the most recent updates
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_DATE + " DESC"
        );
        return cursor;
    }

    //easier way to insert multiple articles into the db
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles){
        db.beginTransaction();
        try{
            for(NewsItem news : articles){
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, news.getTitle());
                cv.put(COLUMN_NAME_AUTHOR, news.getAuthor());
                cv.put(COLUMN_NAME_DESCRIPTION, news.getDescription());
                cv.put(COLUMN_NAME_PUBLISHED_DATE, news.getPublishedAt());
                cv.put(COLUMN_NAME_URL, news.getUrl());
                cv.put(COLUMN_NAME_IMGURL, news.getUrlToImage());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    //completely deletes the table and all of its values
    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }
}
