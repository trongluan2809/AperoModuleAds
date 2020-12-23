package com.ads.control;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ads.control.funtion.AdmodHelper;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class Purchase {
    private static final String LICENSE_KEY = null;
    private static final String MERCHANT_ID = null;
    private BillingProcessor bp;
    //    public static final String PRODUCT_ID = "android.test.purchased";
    @SuppressLint("StaticFieldLeak")
    private static Purchase instance;

    @SuppressLint("StaticFieldLeak")
    private String productId;
    private String price = "1.49$";
    private String oldPrice = "2.99$";

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public static Purchase getInstance() {
        if (instance == null) {
            instance = new Purchase();
        }
        return instance;
    }

    private Purchase() {

    }

    public void initBilling(final Context context, final String productId) {
        this.productId = productId;
        bp = new BillingProcessor(context, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                AdmodHelper.setPurchased((Activity) context, true);
            }

            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
            }

            @Override
            public void onBillingInitialized() {

            }

            @Override
            public void onPurchaseHistoryRestored() {
                if (bp.isPurchased(productId)) {
                    AdmodHelper.setPurchased((Activity) context, true);
                }
            }
        });
        bp.initialize();
    }

    public boolean isPurcharsed(Context context) {
        try {
            return AdmodHelper.isPurchased((Activity) context) || bp.isPurchased(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void purcharse(Activity activity) {
        try {
            bp.purchase(activity, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consumePurchase() {
        try {
            bp.consumePurchase(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        bp.handleActivityResult(requestCode, resultCode, data);
    }

    public String getPrice() {
        return price;
    }

    public String getOldPrice() {
        return oldPrice;
    }
}