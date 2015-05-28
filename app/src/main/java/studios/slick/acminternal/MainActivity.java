package studios.slick.acminternal;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rengwuxian.materialedittext.MaterialEditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    Button loginButton;
    MaterialEditText etRegno, etPassword;
    ProgressBar progressBar;
    LinearLayout loginLayout;

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

        etRegno = (MaterialEditText)findViewById(R.id.etRegNo);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);
        loginLayout = (LinearLayout)findViewById(R.id.llLoginDetails);

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
        validateForm();
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
     * Toggles the visibility of the login form
     */
    private void showForm(boolean isVisible){
        if(isVisible){
            loginLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else{
            loginLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
