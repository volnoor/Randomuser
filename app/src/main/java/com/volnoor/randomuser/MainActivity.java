package com.volnoor.randomuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        UserAdapter.OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RandomuserClient mClient;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_users);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Progressbar is active by default
        mProgressBar = findViewById(R.id.pb_main);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        mClient = APIClient.getClient(this).create(RandomuserClient.class);

        loadUsers(++mCurrentPage);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");

        loadUsers(++mCurrentPage);
    }

    private void loadUsers(int page) {
        Call<RandomuserResponse> call = mClient.getUsers(page, 10, "abc");

        call.enqueue(new Callback<RandomuserResponse>() {
            @Override
            public void onResponse(Call<RandomuserResponse> call, Response<RandomuserResponse> response) {
                List<RandomuserResponse.Result> results = response.body().getResults();

                UserAdapter adapter = new UserAdapter(MainActivity.this, results);
                mRecyclerView.setAdapter(adapter);

                // Disable progressbar and refresh indicator
                mProgressBar.setVisibility(View.INVISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onResponse ");
            }

            @Override
            public void onFailure(Call<RandomuserResponse> call, Throwable t) {
                // Disable progressbar and refresh indicator
                mProgressBar.setVisibility(View.INVISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onFailure " + t.getMessage());
            }
        });
    }

    @Override
    public void itemClicked(RandomuserResponse.Result user) {
        Log.d(TAG, "item clicked");

        Intent intent = new Intent(this, UserInfoActivity.class)
                .putExtra("user", user); // Pass user to UserInfoActivity

        startActivity(intent);
    }
}
