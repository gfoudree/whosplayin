package group12.whosplayin;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout2);

        Button yesButton = (Button) findViewById(R.id.logout_yes_button);
        Button noButton = (Button) findViewById(R.id.logout_no_button);

        //If user clicks No button, app takes user back to the homepage of the app
        noButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogoutActivity.this,MainActivity.class));
            }
        });

        //If user clicks yes button, app will log user out of the app
        yesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogoutActivity.this, LoginActivity.class));
            }
        });
    }

}