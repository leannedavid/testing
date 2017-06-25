package com.example.li_en.newsapp.model;

/**
 * Created by li-en on 6/25/17.
 */

public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public NewsItem(String authorIn, String titleIn, String descriptIn, String urlIn, String imgUrl, String publishedIn){
        this.author = authorIn;
        this.title = titleIn;
        this.description = descriptIn;
        this.url = urlIn;
        this.urlToImage = imgUrl;
        this.publishedAt = publishedIn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
