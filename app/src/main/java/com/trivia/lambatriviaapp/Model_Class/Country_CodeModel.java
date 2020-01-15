package com.trivia.lambatriviaapp.Model_Class;

public class Country_CodeModel {
    public String name;
    public String flag;
    public String callingCodes;

    public Country_CodeModel(String name, String flag, String callingCodes) {
        this.name=name;
        this.flag=flag;
        this.callingCodes=callingCodes;

    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(String callingCodes) {
        this.callingCodes = callingCodes;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setName(String name) {
        this.name = name;
    }
}
