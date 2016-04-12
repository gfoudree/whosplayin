package group12.whosplayin;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ContactActivity extends Activity {

    private String contactEmail;
    private String contactConcern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final EditText emailEditText = (EditText) findViewById(R.id.contact_email);
        final EditText concernEditText = (EditText) findViewById(R.id.contact_concern);
        final Button submitButton = (Button) findViewById(R.id.contact_submit);

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                contactConcern = concernEditText.getText().toString();
                contactEmail = emailEditText.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"whosplayin12@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Whosplayin Concern");
                emailIntent.putExtra(Intent.EXTRA_TEXT, contactConcern);
                
                try{
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }catch (Exception e){
                    Toast.makeText(ContactActivity.this, "There are no email clients installed", Toast.LENGTH_SHORT).show();
                    
                }

            }
        });

    }

}
