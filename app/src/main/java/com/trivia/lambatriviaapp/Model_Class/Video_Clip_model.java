package com.trivia.lambatriviaapp.Model_Class;

public class Video_Clip_model {
    String id;
    String title;
    String coin;
    String video;

    public Video_Clip_model(String id, String title, String coin, String video) {
        this.coin=coin;
        this.id=id;
        this.title=title;
        this.video=video;

    }

    public String getTitle() {
        return title;
    }

    public String getCoin() {
        return coin;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
