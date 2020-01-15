package com.trivia.lambatriviaapp.Model_Class;

public class News_blog_model {
    String id;
    String title;
    String image;
    String news_link;
    String coin;

    public News_blog_model(String id, String title, String image, String news_link, String coin) {
        this.id=id;
        this.title=title;
        this.image=image;
        this.news_link=news_link;
        this.coin=coin;
    }

    public String getTitle() {
        return title;
    }

    public String getNews_link() {
        return news_link;
    }

    public void setNews_link(String news_link) {
        this.news_link = news_link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
