package com.example.firebaselogin;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Controllers.SendEmailController;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import maes.tech.intentanim.CustomIntent;

public class RecoveryPasswordActivity extends AppCompatActivity {
    public static EditText edtRecoveryEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        edtRecoveryEmail = findViewById(R.id.edtRecoveryEmail);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.pinkish)));

    }

    // Methods

    public void ibRecovery(View view) {
        String to = edtRecoveryEmail.getText().toString().trim();
        String subject = "Recovery Password";
        String messageContent = "Your password is: n sei pora kkk";
        String senderEmail = "spacesoft154@gmail.com";
        String senderPassword = "bgk35670";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(messageContent);

            new SendEmailController(this, this).execute(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void imBack(View view) {
        finish();
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }

    public void txvRemembered(View view) {
        finish();
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }
}