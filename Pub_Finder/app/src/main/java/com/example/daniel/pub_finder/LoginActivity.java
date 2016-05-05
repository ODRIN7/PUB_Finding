package com.example.daniel.pub_finder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.daniel.entities.User;
import com.example.daniel.facades.DataBaseHelper;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.j256.ormlite.stmt.Where;
import com.facebook.FacebookSdk;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private User user;
    private DataBaseHelper<User> userDataBaseHelper;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;
    LoginButton loginButton;
    private CallbackManager callbackManager;

    public LoginActivity() {
        this.userDataBaseHelper = new DataBaseHelper<User>(this, User.class);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        init();
    }

    public void init() {
        callbackManager = CallbackManager.Factory.create();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);

            }
        });
        FacebookLogin();
    }

    public void login() throws SQLException {
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

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                super.onActivityResult(requestCode, resultCode, data);
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public boolean validate() throws SQLException {
        boolean valid = false;


        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
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
        Where<User, Integer> userIntegerWhere = userDataBaseHelper.getGenericDao().queryBuilder().where().eq("emailaddress", email).and().eq("password", (password));
        user = userIntegerWhere.queryForFirst();
        if (user !=null && user.getEmailaddress().trim().equals(email.trim()) && user.getPassword().trim().equals(password.trim())) {
            valid = true;
        } else {
            _emailText.setError("wrong Error " + user.getName() + " " + user.getPassword() + " " + user.getName());
        }

        return valid;
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        this.finish();
        Intent intent = new Intent(this, activity_fragment_layout.class);
        intent.putExtra("user_id", user.getUser_id());
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }



    public void FacebookLogin() {

        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("password");

        // If using in a fragment

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                String userId = loginResult.getAccessToken().getUserId();
                String accessToken = loginResult.getAccessToken().getToken();
                try {
                 User user =  userDataBaseHelper.getEntityById(User.class,Integer.parseInt(userId));
                    if(user==null){
                        User newuser =new User();
                        newuser.setEmailaddress(userId);
                        newuser.setPassword(accessToken);
                        userDataBaseHelper.createEntity(newuser);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                onLoginSuccess();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Login error"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}