package com.trivia.lambatriviaapp.Model_Class.Wallet_History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wallet_History_Data {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_formatted")
    @Expose
    private String dateFormatted;
    @SerializedName("record")
    @Expose
    private List<Wallet_Record> record = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public List<Wallet_Record> getRecord() {
        return record;
    }

    public void setRecord(List<Wallet_Record> record) {
        this.record = record;
    }

}
