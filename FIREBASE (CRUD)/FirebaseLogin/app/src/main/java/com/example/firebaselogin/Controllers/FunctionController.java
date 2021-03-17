package com.example.firebaselogin.Controllers;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import com.example.firebaselogin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class FunctionController {

    // Property

    Activity activity;

    // Constructor

    public FunctionController(Activity activity) {
        this.activity = activity;
    }

    // Methods

    public void changeStatusBar(String color){
        String colorFull = "#" + color;
        activity.getWindow().setStatusBarColor(Color.parseColor(colorFull));
    }


    public void dialog(String title, String message, int icon, String type){
        MaterialAlertDialogBuilder dialogBuilder;

        dialogBuilder = (type.equals("error")) ? new MaterialAlertDialogBuilder(activity, R.style.dialogError) :
                new MaterialAlertDialogBuilder(activity, R.style.dialogSuccess);

        dialogBuilder
                .setTitle(Html.fromHtml(title))
                .setMessage(Html.fromHtml(message))
                .setIcon(icon)
                .setBackground(activity.getResources().getDrawable(R.drawable.bg_dialog))
                .setPositiveButton("Exit", null)
                .show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void dialog(String title, String message, int icon, boolean isInternetDialog){
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity, R.style.dialog);
        dialogBuilder
                .setTitle(Html.fromHtml(title))
                .setMessage(Html.fromHtml(message))
                .setIcon(icon)
                .setBackground(activity.getResources().getDrawable(R.drawable.bg_dialog))
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Objects.requireNonNull(activity).finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * @return if the network is on or off (true or false)
     */
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()) return true; // Connected
        else return false; // Disconnected
    }

    /**
     * @param price double parameter to R$
     * @return
     */
    public static String toBrazilianCoin(double price){
        Locale ptBr = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(price);
    }

}
