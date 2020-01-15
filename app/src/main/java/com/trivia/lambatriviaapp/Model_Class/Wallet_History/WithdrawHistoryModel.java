package com.trivia.lambatriviaapp.Model_Class.Wallet_History;

/**
 * Created by Raghvendra Sahu on 26/11/2019.
 */
public class WithdrawHistoryModel {
    String id;
    String user_id;
    String payment_type;
    String payment_method_id;
    String status;
    String phone_numer;

    public WithdrawHistoryModel(String id, String user_id, String payment_type,
                                String payment_method_id, String status, String amount,
                                String date, String phone_numer) {
        this.id = id;
        this.user_id = user_id;
        this.payment_type = payment_type;
        this.payment_method_id = payment_method_id;
        this.status = status;
        this.amount = amount;
        this.date = date;
        this.phone_numer = phone_numer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPhone_numer() {
        return phone_numer;
    }

    public void setPhone_numer(String phone_numer) {
        this.phone_numer = phone_numer;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String amount;
    String date;

}
