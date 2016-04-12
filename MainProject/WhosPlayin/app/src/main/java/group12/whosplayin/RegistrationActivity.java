package group12.whosplayin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

    private String registerName;
    private String registerEmail;
    private String registerPhone;
    private String registerUserName;
    private String registerGender;
    private String registerPassword;
    private String registerLocation;
    private String registerAge;
    private RegistrationTask regTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        //Name EditText
        final EditText editTextName = (EditText) findViewById(R.id.registration_name);
        //Email EditText
        final EditText editTextEmail = (EditText) findViewById(R.id.registration_email);
//        //Username TextEdit
        final EditText editTextUsername = (EditText) findViewById(R.id.registration_username);
//        //Gender TextEdit
        final EditText editTextGender = (EditText) findViewById(R.id.registration_gender);

        final EditText editTextPassword = (EditText) findViewById(R.id.registration_password);
//        //Phone Number EditText
        final EditText editTextPhone = (EditText) findViewById(R.id.registration_phone);

        final EditText editTextLocation = (EditText) findViewById(R.id.registration_location);

        final EditText editTextAge = (EditText) findViewById(R.id.registration_age);

        //When they submit
        Button submitButton = (Button) findViewById(R.id.registration_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerName = editTextName.getText().toString();
                registerUserName = editTextUsername.getText().toString();
                registerEmail = editTextEmail.getText().toString();
                registerGender = editTextGender.getText().toString();
                registerPhone = editTextPhone.getText().toString();
                registerPassword = editTextPassword.getText().toString();
                registerLocation = editTextLocation.getText().toString();
                registerAge = editTextAge.getText().toString();

                regTask = new RegistrationTask(registerUserName, registerPassword, registerName, registerGender,
                        registerPassword, registerPhone, registerLocation,registerAge);
                regTask.execute((Void) null);

                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });

    }


    public class RegistrationTask extends AsyncTask<Void, Void, Boolean>{
        private final String rName;
        private final String rEmail;
        private final String rUsername;
        private final String rGender;
        private final String rPassword;
        private final String rPhoneNumber;
        private final String rLocation;
        private final String rAge;

        RegistrationTask(String name, String email, String username, String gender, String password, String phonenumber, String location, String age)
        {
            this.rName = name;
            this.rEmail = email;
            this.rUsername = username;
            this.rGender = gender;
            this.rPassword = password;
            this.rPhoneNumber = phonenumber;
            this.rLocation = location;
            this.rAge = age;
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            User user = new User();
            try{
                return user.createUser(rUsername,rPassword, rName,Integer.parseInt(rAge), rGender, rEmail, rPhoneNumber);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//
//        }
    }
}
