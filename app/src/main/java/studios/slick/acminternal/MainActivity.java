package studios.slick.acminternal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gc.materialdesign.views.ProgressBarIndeterminate;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.slick.acminternal.customviews.MyButton;
import studios.slick.acminternal.customviews.MyTextView;
import studios.slick.acminternal.networkmanagement.NetworkManager;
import studios.slick.acminternal.volleyhandler.MyVolley;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyVolley.OnFailureListener, MyVolley.OnSuccessListener {

    SharedPreferences sharedPreferences;
    Button loginButton, exitButton;
    MaterialEditText etRegno, etPassword;
    ProgressBarIndeterminate progressBar;
    LinearLayout loginLayout;
    MyTextView loginSupportTextView;
    Toolbar toolbar;

    private boolean hasLoggedIn;
    private String registrationNumber;
    private String password;
    private int userMode;

    private static final String URL = "http://vit.acm.org/1misc/android/mylogin.php";

    private static final String FILENAME = "LoginDetails";
    private static final String SPLOGGEDIN = "hasLoggedIn";
    private static final String SPREGNO = "registrationNumber";
    private static final String SPPWD = "password";
    private static final String SPNAME = "userName";
    private static final String SPUSERMODE = "userMode";
    private static final String SPIMGURL = "imageUrl";

    private MyVolley myVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        exitButton = (Button)findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);

        myVolley = new MyVolley();
        myVolley.setOnSuccessListener(this);
        myVolley.setOnFailureListener(this);

        etRegno = (MaterialEditText)findViewById(R.id.etRegNo);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
                    validateForm();
                    return true;
                }
                return false;
            }
        });
        loginLayout = (LinearLayout)findViewById(R.id.llLoginDetails);
        progressBar = (ProgressBarIndeterminate)findViewById(R.id.progressBarLogin);
        loginSupportTextView = (MyTextView) findViewById(R.id.loginSupportText);

        toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(FILENAME, 0);

        checkLogin();
    }

    /**
     * Checks if the user has already logged in. If so, it'll launch the app, else
     * it makes the login form visible.
     */
    private void checkLogin() {
        hasLoggedIn = sharedPreferences.getBoolean(SPLOGGEDIN, false);
        if(hasLoggedIn){
            showForm(false);
            launchApp();
        }else{
            showForm(true);
        }

    }

    private void launchApp() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Posts data to the server for authentication after converting data into appropriate JSONObject format.
     */
    private void authenticateCredentials() {

        // Creating a new ToPost object and filling in the values.
        ToPost toPost = new ToPost();
        toPost.regno = registrationNumber;
        toPost.pass = password;

        Gson gson = new Gson();
        JSONObject jsonObject = null;

        try{
            jsonObject = new JSONObject(gson.toJson(toPost,ToPost.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(jsonObject != null){
                myVolley.insertRequest(this, URL, jsonObject);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                validateForm();
                break;
            case R.id.exitButton:
                finish();
                break;
        }
    }

    /**
     * Checks if the fields of the form has valid data entered in it. If so, it'll begin the authentication process.
     */
    private void validateForm() {
        registrationNumber = etRegno.getText().toString().trim().toUpperCase();
        password = etPassword.getText().toString().trim();

        if(registrationNumber.isEmpty()){
            etRegno.setError("Please enter a valid registration number");
            etPassword.clearFocus();
            etRegno.requestFocus();
        } else if (password.isEmpty()){
            etPassword.setError("Please enter the password");
            etRegno.clearFocus();
            etPassword.requestFocus();
        } else{

            if(NetworkManager.isNetworkConnected(this)){
                showForm(false);
                authenticateCredentials();
            } else{
                launchNetworkError();
            }

        }
    }


    /**
     * Launches a context sensitive toast (Crouton) on the top of the screen with the error message.
     */
    private void launchNetworkError() {
        Crouton.makeText(this, "Network error occured. Try again.", Style.ALERT).show();
    }

    /**
     * Launches a context sensitive toast on the top of the screen with invalid credentials error message.
     */
    private void launchInvalidCredentialsError(){
        Crouton.makeText(this, "Invalid credentials. Please try again.", Style.ALERT).show();
    }

    /**
     * Toggles the visibility of the login form
     */
    private void showForm(boolean isVisible){
        loginButton.setEnabled(isVisible);
        exitButton.setEnabled(isVisible);
        if(isVisible){
            loginLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            loginSupportTextView.setText("Enter Your Credentials");
        }else{
            loginLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            loginSupportTextView.setText("Authenticating Info");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }

    /**
     * Called when the request given to volley fails due to some error.
     */
    @Override
    public void onFail(VolleyError error) {
        Log.i("MAINACTIVITY","VolleyError: " + error.toString());
        launchNetworkError();
        showForm(true);
    }

    /**
     * Called after volley successfully completes the given request.
     */
    @Override
    public void onSuccess(JSONObject response) {
        Gson gson = new Gson();
        ToReceive toReceive = gson.fromJson(response.toString(), ToReceive.class);
        userMode = toReceive.priority;
        if(userMode > -1){
            updateSharedPrefs(toReceive);
            launchApp();
        }else{
            launchInvalidCredentialsError();
            showForm(true);
        }
    }

    /**
     * Updates the shared preferences with the latest data fetched from the server.
     */
    private void updateSharedPrefs(ToReceive toReceive) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SPREGNO, registrationNumber);
        editor.putString(SPPWD, password);
        editor.putInt(SPUSERMODE, userMode);
        editor.putString(SPIMGURL, toReceive.url);
        editor.putString(SPNAME, toReceive.name);
        editor.putBoolean(SPLOGGEDIN, true);
        editor.apply();

    }

    /**
     * The class ToPost used as a blueprint to convert java object to JSONString before sending a post request.
     * In a similar way, ToReceive is a blueprint for converting JSONString to java object after receiving a
     * reply from the server.
     */
    private class ToPost{
        public String regno;
        public String pass;
    }

    private class ToReceive{
        public String name;
        public int priority;
        public String url;
    }
}
