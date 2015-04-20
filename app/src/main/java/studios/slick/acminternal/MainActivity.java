package studios.slick.acminternal;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    Button loginButton;
    EditText etRegno, etPassword;
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

        etRegno = (EditText)findViewById(R.id.etRegNo);
        etPassword = (EditText)findViewById(R.id.etPassword);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);
        loginLayout = (LinearLayout)findViewById(R.id.llLoginDetails);

        sharedPreferences = getSharedPreferences(FILENAME, 0);

        checkLogin();
    }

    private void checkLogin() {
        hasLoggedIn = sharedPreferences.getBoolean(SPLOGGEDIN, false);
        if(hasLoggedIn){
            registrationNumber = sharedPreferences.getString(SPREGNO, "-1");
            password = sharedPreferences.getString(SPPWD, "-1");
            userMode = sharedPreferences.getInt(SPUSERMODE, -1);
            showForm(false);
            authenticateCredentials();
        }else{
            showForm(true);
        }

    }

    private void authenticateCredentials() {

    }


    @Override
    public void onClick(View v) {
        showForm(false);
    }

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
