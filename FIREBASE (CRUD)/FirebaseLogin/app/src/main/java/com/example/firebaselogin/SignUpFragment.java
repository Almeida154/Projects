package com.example.firebaselogin;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    ImageButton ibSignUp;
    EditText edtRegUsername;
    EditText edtRegEmail;
    EditText edtRegPassword;

    DatabaseReference userFIREBASE = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        edtRegUsername = v.findViewById(R.id.edtRegUsername);
        edtRegEmail = v.findViewById(R.id.edtRegEmail);
        edtRegPassword = v.findViewById(R.id.edtRegPassword);
        (ibSignUp = v.findViewById(R.id.ibSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verify internet connection

                if(!new FunctionController(getActivity()).isConnected()) new FunctionController(getActivity()).dialog(
                        "<h1><font>No Internet</font></h1>",
                        "Connect to a <b>network</b> to continue",
                        R.drawable.ic_wifi_off,
                        true
                );
                else{
                    // TO OTP VERIFICATION

                    // Intent it = new Intent(getActivity(), SendOTPActivity.class);
                    // startActivity(it);
                    // CustomIntent.customType(getActivity(), "left-to-right");

                    // REGISTER NEW USER

                    String key = userFIREBASE.push().getKey();

                    User user = new User(
                            key,
                            edtRegUsername.getText().toString(),
                            edtRegPassword.getText().toString(),
                            edtRegEmail.getText().toString(),
                            "(11) 957648755"
                    );

                    userFIREBASE.child(Objects.requireNonNull(key)).setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                    clean();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        return v;
    }

    private void clean(){
        edtRegUsername.setText(null);
        edtRegPassword.setText(null);
        edtRegEmail.setText(null);
    }
}