package group12.whosplayin;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class Registration_Fragment extends Fragment{

    private String registerName;
    private String registerEmail;
    private String registerPhone;
    private String registerUserName;
    private String registerGender;
    private String registerPassword;
    private String registerLocation;
    private String registerAge;
    private RegistrationTask regTask = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.registration_layout,container,false);

        Button regButton = (Button) rootView.findViewById(R.id.register_button);

        final EditText editTextName = (EditText) rootView.findViewById(R.id.registration_name);
        final EditText editTextEmail = (EditText) rootView.findViewById(R.id.registration_email);
        final EditText editTextPhone = (EditText) rootView.findViewById(R.id.registration_phone);
        final EditText editUserName = (EditText) rootView.findViewById(R.id.registration_username);
        final EditText editTextGender = (EditText) rootView.findViewById(R.id.registration_gender);
        final EditText editTextPassword = (EditText) rootView.findViewById(R.id.registration_password);
        final EditText editTextLocation = (EditText) rootView.findViewById(R.id.registration_location);
        final EditText editTextAge = (EditText) rootView.findViewById(R.id.registration_age);

        Button submitButton = (Button) rootView.findViewById(R.id.registration_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerName = editTextName.getText().toString();
                registerEmail = editTextEmail.getText().toString();
                registerPhone = editTextPhone.getText().toString();
                registerUserName = editUserName.getText().toString();
                registerGender = editTextGender.getText().toString();
                registerPassword = editTextPassword.getText().toString();
                registerLocation = editTextLocation.getText().toString();
                registerAge = editTextAge.getText().toString();

                regTask = new RegistrationTask(registerName, registerEmail, registerUserName, registerGender,
                        registerPassword, registerPhone, registerLocation,registerAge);
                regTask.doInBackground();

                //Starts the registration activity
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return rootView;
    }




    public static class RegistrationTask extends AsyncTask<Void, Void, Boolean> {
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
