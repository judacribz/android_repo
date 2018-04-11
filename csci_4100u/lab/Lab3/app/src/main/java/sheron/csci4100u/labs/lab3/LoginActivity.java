package sheron.csci4100u.labs.lab3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME = "sheron";
    private static final String PASSWORD = "100504990";

    Button bLogin;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        bLogin     = (Button) findViewById(R.id.b_login2);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password) ;

        // Check login credentials on login button pressed
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user        = etUsername.getText().toString();
                String pass        = etPassword.getText().toString();

                // set login status and return to main activity
                int loginStatus = (USERNAME.equalsIgnoreCase(user) && PASSWORD.equals(pass))?
                                  RESULT_OK : RESULT_CANCELED;

                Intent iLoginResult = new Intent();
                setResult(loginStatus, iLoginResult);
                finish();
            }
        });
    }
}
