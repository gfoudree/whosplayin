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
//        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerName = v.getText().toString();
//                }
//                return handled;
//            }
//        });
//        editTextName.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
//                    registerName = editTextName.getText().toString();
//                    return true;
//                }
//                return true;
//
//            }
//        });

        //Email EditText
        final EditText editTextEmail = (EditText) findViewById(R.id.registration_email);
//        editTextEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerEmail = v.getText().toString();
//                }
//                return handled;
//            }
//        });
//
//        //Username TextEdit
        final EditText editTextUsername = (EditText) findViewById(R.id.registration_username);
//        editTextEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerUserName = v.getText().toString();
//                }
//                return handled;
//            }
//        });
//
//        //Gender TextEdit
        final EditText editTextGender = (EditText) findViewById(R.id.registration_gender);
//        editTextEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerGender = v.getText().toString();
//                }
//                return handled;
//            }
//        });
//
        final EditText editTextPassword = (EditText) findViewById(R.id.registration_password);
//        editTextEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerPassword = v.getText().toString();
//                }
//                return handled;
//            }
//        });
//
//        //Phone Number EditText
        final EditText editTextPhone = (EditText) findViewById(R.id.registration_phone);
//        editTextPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerPhone = v.getText().toString();
//                }
//                return handled;
//            }
//        });
//
        final EditText editTextLocation = (EditText) findViewById(R.id.registration_location);
//        editTextEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    registerLocation = v.getText().toString();
//                }
//                return handled;
//            }
//        });
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

                regTask = new RegistrationTask(registerName, registerEmail, registerUserName, registerGender,
                        registerPassword, registerPhone, registerLocation,registerAge);
                regTask.doInBackground();

                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });

    }


    public static class RegistrationTask extends AsyncTask<Void, Void, Boolean>{
        String name;
        String email;
        String username;
        String gender;
        String password;
        String phoneNumber;
        String location;
        String age;

        RegistrationTask(String name, String email, String username, String gender, String password, String phonenumber, String location, String age)
        {
            this.name = name;
            this.email = email;
            this.username = username;
            this.gender = gender;
            this.password = password;
            this.phoneNumber = phonenumber;
            this.location = location;
            this.age = age;
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            User user = new User();
            try{user.registerUser(username, email, name, gender, password, location, phoneNumber,age);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }


        }
    }
}
