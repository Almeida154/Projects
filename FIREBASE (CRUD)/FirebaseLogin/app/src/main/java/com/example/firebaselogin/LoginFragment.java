package com.example.firebaselogin;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.firebaselogin.Controllers.FunctionController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;
import maes.tech.intentanim.CustomIntent;

public class LoginFragment extends Fragment {

    ImageButton ibLogin;
    TextView txvForgotPassword;
    EditText edtLoginEmail;
    EditText edtLoginPassword;

    DatabaseReference usersFIREBASE = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the layout for this fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_login, container, false);

        edtLoginEmail = v.findViewById(R.id.edtLoginEmail);
        edtLoginPassword = v.findViewById(R.id.edtLoginPassword);

        // tem q ta tirando dps

        edtLoginEmail.setText("dvd.com");
        edtLoginPassword.setText("ddd");

        (ibLogin = v.findViewById(R.id.ibLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verify internet connection

                if(!new FunctionController(getActivity()).isConnected()) new FunctionController(getActivity()).dialog(
                        "<h1><font>No Internet</font></h1>",
                        "Connect to a <b>network</b> to continue",
                        R.drawable.ic_wifi_off,
                        true
                );
                else{
                    usersFIREBASE.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            boolean found = false;

                            for(DataSnapshot userSnap : snapshot.getChildren()) {
                                //Log.d("firebaseUSERS", "email: " + userSnap.child("email").getValue());

                                if (Objects.equals(userSnap.child("email").getValue(), edtLoginEmail.getText().toString())
                                        && Objects.equals(userSnap.child("password").getValue(), edtLoginPassword.getText().toString())) {

                                    Objects.requireNonNull(getActivity()).finish();
                                    Intent it = new Intent(getActivity(), HomeActivity.class);
                                    it.putExtra("key", (String) userSnap.child("id").getValue());
                                    startActivity(it);
                                    CustomIntent.customType(getActivity(), "left-to-right");

                                    found = true;
                                }
                            }

                            if(!found) new FunctionController(getActivity()).dialog(
                                    "Error",
                                    "Not found",
                                    R.drawable.ic_error,
                                    "error"
                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        (txvForgotPassword = v.findViewById(R.id.txvForgotPassword)).setOnClickListener(new View.OnClickListener() {
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
                    Objects.requireNonNull(getActivity()).finish();
                    Intent it = new Intent(getActivity(), RecoveryPasswordActivity.class);
                    startActivity(it);
                    CustomIntent.customType(getActivity(), "left-to-right");
                }
            }
        });

        return v;
    }
}