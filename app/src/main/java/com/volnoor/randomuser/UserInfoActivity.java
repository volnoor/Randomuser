package com.volnoor.randomuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfoActivity extends AppCompatActivity {
    private static String TAG = UserInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ImageView ivLarge = findViewById(R.id.iv_large);
        TextView tvFirst = findViewById(R.id.tv_first_name);
        TextView tvLast = findViewById(R.id.tv_last_name);
        TextView tvEmail = findViewById(R.id.tv_email);
        TextView tvRegistered = findViewById(R.id.tv_registered);

        RandomuserResponse.Result user = getIntent().getExtras().getParcelable("user");

        // Load large user image
        Picasso.with(this)
                .load(user.getPicture().getLarge())
                .into(ivLarge);

        tvFirst.setText(user.getName().getFirst());
        tvLast.setText(user.getName().getLast());
        tvEmail.setText(user.getEmail());

        // Parse registration date
        Log.d(TAG, user.getRegistered());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(user.getRegistered());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        tvRegistered.setText(dateFormat.format(date));
    }
}
