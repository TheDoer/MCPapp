package zw.co.munaticommunications;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.BindView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zw.co.munaticommunications.client.AppLoginClient;
import zw.co.munaticommunications.db.DBUser;
import zw.co.munaticommunications.model.LoginBody;
import zw.co.munaticommunications.model.LoginResponse;
import zw.co.munaticommunications.model.User;

import android.app.ProgressDialog;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    Retrofit retrofit;
    Retrofit.Builder retrofitBuilder;
    OkHttpClient okHttpClient;
    ScrollView scrollView;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //Database initialization
        ActiveAndroid.initialize(getApplicationContext());

        scrollView = findViewById(R.id.login_layout);
        scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = retrofitBuilder.baseUrl("http://dev.munat.co.zw/").build();
        AppLoginClient appLoginClient = retrofit.create(AppLoginClient.class);
        Call<LoginResponse> loginResponseCall = appLoginClient.loginWithUsenameAndPassword(new LoginBody(username, password));
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                User user = response.body().getUser();

                if(response.body().getError()){
                    onLoginFailed(response.body().getMessage());
                    progressDialog.dismiss();
                }else{
                    DBUser dbUser = new DBUser(user);
                    dbUser.save();
                    progressDialog.dismiss();
                    onLoginSuccess(user);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onLoginFailed();
                progressDialog.dismiss();
            }
        });
    }

    private void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(User user) {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("TOKEN",user.getToken());
        intent.putExtra("NAME",user.getName());
        intent.putExtra("ID",user.getUser_id());
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || email.length() < 4) {
            _emailText.setError("enter a valid username");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
