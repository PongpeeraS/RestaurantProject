package com.example.restaurantproject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogOut, btnFmenu, btnReserve, btnOrdering, btnCoupon, btnSettings;
    private TextView textUsername;
    private ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TEST Email: testmail@mail.com Pass: 12345678
        auth = FirebaseAuth.getInstance();
        textUsername = findViewById(R.id.text_username);
        userPic = findViewById(R.id.userPicView);
        btnFmenu = findViewById(R.id.fmenuButton);
        btnReserve = findViewById(R.id.reserveButton);
        btnOrdering = findViewById(R.id.orderingButton);
        btnCoupon = findViewById(R.id.couponButton);
        btnSettings = findViewById(R.id.settingsButton);
        btnLogOut = findViewById(R.id.logoutButton);

        textUsername.setText(auth.getCurrentUser().getEmail()); //get email to display here
        //userPic.setImageURI(auth.getCurrentUser().getPhotoUrl()); //HOW TO GET USER PROFILE PICS?

        //Preferences button: press to see or change the app's settings.
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        //Log out button: press to sign out and return to login screen. DIALOG IS USED HERE
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you would like to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        auth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
}
