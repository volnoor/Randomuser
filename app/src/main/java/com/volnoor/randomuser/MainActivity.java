package com.volnoor.randomuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.rv_users);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        RandomuserClient client = APIClient.getClient().create(RandomuserClient.class);

        Call<RandomuserResponse> call = client.getUsers(1, 10, "abc");

        call.enqueue(new Callback<RandomuserResponse>() {
            @Override
            public void onResponse(Call<RandomuserResponse> call, Response<RandomuserResponse> response) {
                List<RandomuserResponse.Result> results = response.body().getResults();

                UserAdapter adapter = new UserAdapter(MainActivity.this, results);
                recyclerView.setAdapter(adapter);

                Log.d(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<RandomuserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        });
    }
}
