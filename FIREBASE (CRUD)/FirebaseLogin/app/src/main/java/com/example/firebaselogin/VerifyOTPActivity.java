package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.firebaselogin.Controllers.FunctionController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    EditText txvCode1, txvCode2, txvCode3, txvCode4, txvCode5, txvCode6;
    TextView txvOTPDesc;
    Button btnVerifyCode;
    ProgressBar pbVerifyingOPT;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.blueDif)));

        txvCode1 = findViewById(R.id.txvCode1);
        txvCode2 = findViewById(R.id.txvCode2);
        txvCode3 = findViewById(R.id.txvCode3);
        txvCode4 = findViewById(R.id.txvCode4);
        txvCode5 = findViewById(R.id.txvCode5);
        txvCode6 = findViewById(R.id.txvCode6);
        txvOTPDesc = findViewById(R.id.txvOTPDesc);
        btnVerifyCode = findViewById(R.id.btnVerifyCode);
        pbVerifyingOPT = findViewById(R.id.pbVerifyingOPT);

        if(getIntent().getExtras() != null) {
            txvOTPDesc.setText(String.format("Enter the code sent to: %s", getIntent().getStringExtra("number")));
            verificationId = getIntent().getStringExtra("verificationId");
        }

        setFlow();

    }

    // Methods

    private void editFlow(EditText[] editTexts){
        editTexts[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editTexts[1].getText().toString().length() == 0) editTexts[0].requestFocus();
                else editTexts[2].requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void editFlow(EditText[] editTexts, boolean isFirst){

        EditText main, secondary;

        main = (isFirst) ? editTexts[0] : editTexts[1];
        secondary = (main == editTexts[0]) ? editTexts[1] : editTexts[0];

        main.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isFirst){
                    if(!charSequence.toString().isEmpty()) secondary.requestFocus();
                }else{
                    if(charSequence.toString().isEmpty()) secondary.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void setFlow(){
        EditText[] editTexts = new EditText[]{txvCode1, txvCode2, txvCode3, txvCode4, txvCode5, txvCode6};
        for(int i = 0; i < 6; i++){
            if(i == 0) editFlow(new EditText[]{editTexts[0], editTexts[1]}, true);
            else if(i == 5) editFlow(new EditText[]{editTexts[4], editTexts[5]}, false);
            else editFlow(new EditText[]{editTexts[i - 1], editTexts[i], editTexts[i + 1]});
        }
    }

    public void btnVerifyCode(View view) {
        EditText[] editTexts = new EditText[]{txvCode1, txvCode2, txvCode3, txvCode4, txvCode5, txvCode6};
        String code = "";
        boolean isValid = true;

        for(int i = 0; i < 6; i++){
            if(editTexts[i].getText().toString().trim().isEmpty()) isValid = false;
            code += editTexts[i].getText().toString();
        }

        if(!isValid) {
            new FunctionController(this).dialog(
                    "Error", "Number invalid", R.drawable.ic_error,"error");
            return;
        }

        if(verificationId != null){
            pbVerifyingOPT.setVisibility(View.VISIBLE);
            btnVerifyCode.setVisibility(View.INVISIBLE);

            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pbVerifyingOPT.setVisibility(View.INVISIBLE);
                        btnVerifyCode.setVisibility(View.VISIBLE);

                        if(task.isSuccessful()){
                            Intent it = new Intent(VerifyOTPActivity.this, MainActivity.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(it);
                        } else {
                            new FunctionController(VerifyOTPActivity.this).dialog(
                                    "Error", "The verification code entered was <b>wrong<b>",
                                    R.drawable.ic_error, "error");
                        }
                    }
                });

        } else {
            new FunctionController(VerifyOTPActivity.this).dialog(
                    "Error", "Something was <b>wrong<b>", R.drawable.ic_error, "error");
        }

    }

    public void txvResendCode(View view) {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBack =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) { }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) { }

                    @Override
                    public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationId = newVerificationId;
                        Toast.makeText(VerifyOTPActivity.this, "New code sent", Toast.LENGTH_SHORT).show();
                    }
                };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber("+55" + getIntent().getStringExtra("number"))
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