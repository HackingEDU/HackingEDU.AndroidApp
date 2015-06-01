package hackingedu.sf.com.hackingedu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Locale;


public class LoginActivity extends ActionBarActivity {

    ActionProcessButton btnLogin;
    private EditText mEmailInputField;
    private EditText mPasswordInputField;
    ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (ActionProcessButton)findViewById(R.id.btnLogin);
        mEmailInputField = (EditText) findViewById(R.id.emailInputField);
        mPasswordInputField = (EditText) findViewById(R.id.passwordInputField);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(cm, btnLogin, mEmailInputField, mPasswordInputField);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void attemptLogin(ConnectivityManager conmn, ActionProcessButton login, EditText email, EditText Ppassword) {

        clearErrors();
        if(checkInternet(conmn) == true)
        {
            if(checkCredentials(email, Ppassword) == false)
            {
                String username = email.getText().toString();
                String password = Ppassword.getText().toString();
                login(login, username, password);
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), "Unable to connect to Internet", Toast.LENGTH_LONG).show();
        }
    }

    public void login(final ActionProcessButton btnLogin, String username, String Password)
    {
        btnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        String LogInUserName = mEmailInputField.getEditableText().toString();
        String LogInPassword = mPasswordInputField.getEditableText().toString();// + getString(R.string.password_hash_salt);

        ParseUser.logInInBackground(LogInUserName, LogInPassword, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    btnLogin.setProgress(100);
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, WaitlistActivity.class);
                            startActivity(intent);
                        }
                    }, 2);
                    finish();
                } else {
                    btnLogin.setProgress(-1);
                    Toast.makeText(getBaseContext(), "Unable to login, please try again", Toast.LENGTH_LONG).show();
                    mEmailInputField.getText().clear();
                    mPasswordInputField.getText().clear();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnLogin.setProgress(0);
                        }
                    },2000);
                }
            }
        });
    }

    private void clearErrors(){
        mEmailInputField.setError(null);
        mPasswordInputField.setError(null);
    }

    public boolean checkInternet(ConnectivityManager conmn)
    {
        NetworkInfo activeNetwork = conmn.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public boolean checkCredentials(EditText email, EditText Ppassword)
    {
        // Store values at the time of the login attempt.
        String username = email.getText().toString();
        String password = Ppassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordInputField.setError(getString(R.string.error_field_required));
            focusView = mPasswordInputField;
            cancel = true;
        } else if (password.length() < 4) {
            mPasswordInputField.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordInputField;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mEmailInputField.setError(getString(R.string.error_field_required));
            focusView = mEmailInputField;
            cancel = true;
        }

        if(cancel == true)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return cancel;
    }




}
