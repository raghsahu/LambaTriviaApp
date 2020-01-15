package com.trivia.lambatriviaapp.Model_Class.Live_Game_Model;

public class LiveWinner_model {
    String username;
    String image;
    String price;

    public LiveWinner_model(String username, String image, String price) {
        this.image=image;
        this.username=username;
        this.price=price;

    }

    public String getUsername() {
        return username;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
