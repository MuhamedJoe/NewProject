package com.selema.newproject.Messages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {

    Context mContext;

    BaseApiService mApiService;
    private RecyclerView recyclerView;
    private ChatAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    List<Messages> messages;
    String senderphone = "2";
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();

        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());


//        senderphone =extras.getString("senderphone");
        getMessages();

        Toast.makeText(this, " My messages " + senderphone, Toast.LENGTH_SHORT).show();

    }

    private void getMessages() {
        mApiService.GetMessages("1")
                .enqueue(new Callback<MessageList>() {
                    @Override
                    public void onResponse(Call<MessageList> call, Response<MessageList> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(mContext, " My messages" + "success", Toast.LENGTH_SHORT).show();

                            List<Inbox> inbox = response.body().getInbox();
                            messages = inbox.get(position).getMessages();
                            mAdapter = new ChatAdapter(messages, inbox, senderphone);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setLayoutManager(mLayoutManager);


                        } else {
                            Toast.makeText(mContext, " a7a", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageList> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });


    }
}
