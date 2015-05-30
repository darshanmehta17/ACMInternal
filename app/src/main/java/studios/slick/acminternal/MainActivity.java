package studios.slick.acminternal;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gc.materialdesign.views.ProgressBarIndeterminate;
import com.rengwuxian.materialedittext.MaterialEditText;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.slick.acminternal.customviews.MyButton;
import studios.slick.acminternal.customviews.MyTextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    private static final String SPUSERMODE = "userMode";

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

        etRegno = (MaterialEditText)findViewById(R.id.etRegNo);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);
        loginLayout = (LinearLayout)findViewById(R.id.llLoginDetails);
        progressBar = (ProgressBarIndeterminate)findViewById(R.id.progressBarLogin);
        loginSupportTextView = (MyTextView) findViewById(R.id.loginSupportText);

        toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(FILENAME, 0);

        checkLogin();
    }

    /*
     * Checks if the user has already logged in. If so, it'll launch the app, else
     * it makes the login form visible.
     */
    private void checkLogin() {
        hasLoggedIn = sharedPreferences.getBoolean(SPLOGGEDIN, false);
        if(hasLoggedIn){
//            registrationNumber = sharedPreferences.getString(SPREGNO, "-1");
//            password = sharedPreferences.getString(SPPWD, "-1");
//            userMode = sharedPreferences.getInt(SPUSERMODE, -1);
            showForm(false);
            launchApp();
        }else{
            showForm(true);
        }

    }

    private void launchApp() {

    }

    private void authenticateCredentials() {

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

    /*
     * Checks if the fields of the form has valid data entered in it. If so, it'll begin the authentication process.
     */
    private void validateForm() {
        if(etRegno.getText().toString().trim().isEmpty()){
            etRegno.setError("Please enter a valid registration number");
        }else if (etPassword.getText().toString().trim().isEmpty()){
            etPassword.setError("Please enter the password");
        }else{
            showForm(false);
            authenticateCredentials();
        }
    }


    /*
     * Launches a context sensitive toast (Crouton) on the top of the screen with the error message.
     */
    private void launchNoNetworkError() {
        Crouton.makeText(this, "No Internet Connection", Style.ALERT).show();
    }

    /*
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

}
