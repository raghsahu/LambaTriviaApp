package com.trivia.lambatriviaapp.Model_Class.Wallet_History;

import java.io.Serializable;

/**
 * Created by Raghvendra Sahu on 26/11/2019.
 */
public class BankDetailsModel implements Serializable {
    String id;
    String user_id;
    String account_holder;
    String branch_code;
    String account_number;

    public String getId() {
        return id;
    }

    public BankDetailsModel(String id, String user_id, String account_holder, String branch_code, String account_number) {
        this.id = id;
        this.user_id = user_id;
        this.account_holder = account_holder;
        this.branch_code = branch_code;
        this.account_number = account_number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(String account_holder) {
        this.account_holder = account_holder;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
}
