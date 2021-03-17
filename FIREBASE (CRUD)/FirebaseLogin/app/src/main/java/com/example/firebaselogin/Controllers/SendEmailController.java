package com.example.firebaselogin.Controllers;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.example.firebaselogin.R;
import com.example.firebaselogin.RecoveryPasswordActivity;
import javax.mail.Message;
import javax.mail.Transport;

public class SendEmailController extends AsyncTask<Message, String, String> {

    // Properties

    private ProgressDialog progressDialog;
    private Context context;
    private Activity activity;

    // Constructor

    public SendEmailController(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    // Methods

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,
                "Wait!", "Sending mail", true, false);
    }

    @Override
    protected String doInBackground(Message... messages) {
        try {
            Transport.send(messages[0]);
            return "Success";
        }catch(Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s.equals("Success")){
            new FunctionController(activity).dialog(
                    "<h1><font>Mail sent</font></h1>",
                    "Mail <b>successfully</b> sent",
                    R.drawable.ic_email_success,
                    "Success"
            );
            RecoveryPasswordActivity.edtRecoveryEmail.setText(null);
        } else {
            new FunctionController(activity).dialog(
                    "<h1><font>Fail</font></h1>",
                    "Something went <b>wrong</b>!",
                    R.drawable.ic_email_error,
                    "error"
            );
        }
    }

}
