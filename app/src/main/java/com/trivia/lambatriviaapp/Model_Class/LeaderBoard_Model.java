package com.trivia.lambatriviaapp.Model_Class;

public class LeaderBoard_Model {
    String username;
    String total_naira;
    String image;


    public LeaderBoard_Model(String username, String total_naira, String image) {

        this.image=image;
        this.total_naira=total_naira;
        this.username=username;
    }

    public String getTotal_naira() {
        return total_naira;
    }

    public void setTotal_naira(String total_naira) {
        this.total_naira = total_naira;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
