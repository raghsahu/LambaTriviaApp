package com.trivia.lambatriviaapp.Activity.Wallet_History;

import co.paystack.android.PaystackSdk;

public class Bootstrap {
    public static void setPaystackKey(String publicKey) {
        PaystackSdk.setPublicKey(publicKey);
    }
}
