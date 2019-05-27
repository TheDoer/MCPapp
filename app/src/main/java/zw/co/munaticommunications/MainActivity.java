package zw.co.munaticommunications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zw.co.munaticommunications.client.AppMessagesClient;
import zw.co.munaticommunications.model.Message;
import zw.co.munaticommunications.model.MessagesResponse;
import zw.co.munaticommunications.util.MessagesRecyclerviewAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Message>> {
    String token, name;
    int user_id;
    Toolbar myToolbar;
    RecyclerView recyclerView;
    TextView textViewNone;

    Retrofit retrofit;
    Retrofit.Builder retrofitBuilder;
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Database initialization
        ActiveAndroid.initialize(getApplicationContext());

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.icon);

        recyclerView = findViewById(R.id.recyclerView_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textViewNone = findViewById(R.id.textView_clear);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Message>> messagesLoader = loaderManager.getLoader(9090);

        if (messagesLoader == null) {
            loaderManager.initLoader(9090, new Bundle(), this);
        } else {
            loaderManager.restartLoader(9090, new Bundle(), this);
        }

        Intent intent = getIntent();
        user_id = intent.getIntExtra("ID",0);
        name = intent.getStringExtra("NAME");
        token = intent.getStringExtra("TOKEN");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<List<Message>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<List<Message>>(this) {
            @Nullable
            @Override
            public List<Message> loadInBackground() {
                okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                retrofitBuilder = new Retrofit.Builder()
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create());

                retrofit = retrofitBuilder.baseUrl("http://dev.munat.co.zw/")
                        .build();
                AppMessagesClient appMessagesClient = retrofit.create(AppMessagesClient.class);
                Call<MessagesResponse> countriesResponseCall = appMessagesClient.getAllMesages("Bearer "+token);
                try {
                    Response<MessagesResponse> response = countriesResponseCall.execute();
                    if(response.body() != null){
                        return response.body().getMessages();
                    } else{
                        return new ArrayList<>();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Message>> loader, List<Message> messages) {
        MessagesRecyclerviewAdapter messagesRecyclerviewAdapter = new MessagesRecyclerviewAdapter(this,messages);
        recyclerView.setAdapter(messagesRecyclerviewAdapter);

        if(messages.size() > 0){
            textViewNone.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Message>> loader) {

    }
}
