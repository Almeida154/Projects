package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.Mask;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {

    EditText edtNumberOPT;
    ProgressBar pbGettingOPT;
    Button btnGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.blueDif)));

        pbGettingOPT = findViewById(R.id.pbGettingOPT);
        btnGetCode = findViewById(R.id.btnGetCode);
        (edtNumberOPT = findViewById(R.id.edtNumberOPT)).addTextChangedListener(Mask.mask(edtNumberOPT, Mask.FORMAT_CELL));
    }

    // Methods

    public void btnGetCode(View view) {
        if(edtNumberOPT.getText().toString().length() < -1) {
            new FunctionController(this).dialog(
                    "Error", "Number invalid", R.drawable.ic_error,"error");
            return;
        }

        pbGettingOPT.setVisibility(View.VISIBLE);
        btnGetCode.setVisibility(View.INVISIBLE);

        String phone = edtNumberOPT.getText().toString();
        String phoneSub = phone.substring(1, 3) + phone.substring(5);

        PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBack =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        pbGettingOPT.setVisibility(View.GONE);
                        btnGetCode.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        pbGettingOPT.setVisibility(View.GONE);
                        btnGetCode.setVisibility(View.VISIBLE);
                        Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        pbGettingOPT.setVisibility(View.GONE);
                        btnGetCode.setVisibility(View.VISIBLE);

                        Intent it = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                        it.putExtra("number", edtNumberOPT.getText().toString());
                        it.putExtra("verificationId", verificationId);
                        startActivity(it);
                    }
                };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber("+55" + phoneSub)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(verificationCallBack)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public void imBack(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}