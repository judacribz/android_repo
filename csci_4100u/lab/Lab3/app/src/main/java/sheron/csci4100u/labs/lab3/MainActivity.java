package sheron.csci4100u.labs.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST = 101;

    Button bAbout;
    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Setting listeners for login and about button
        bAbout = (Button) findViewById(R.id.b_about);
        bLogin = (Button) findViewById(R.id.b_login);

        bAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(iAbout);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(iLogin, LOGIN_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST) {
            String loginSuccess = this.getResources()
                                          .getString(R.string.login_success);
            String loginFail = this.getResources()
                                          .getString(R.string.login_fail);

             String loginStatusText = (resultCode == RESULT_OK)? loginSuccess : loginFail;

            Toast.makeText(getApplicationContext(), loginStatusText, Toast.LENGTH_SHORT).show();
        }
    }
}
