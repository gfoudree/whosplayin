package group12.whosplayin;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReportActivity extends Activity {

    private String reportName;
    private String reportReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        final EditText playerEditText = (EditText) findViewById(R.id.report_player);
        final EditText reasonEditText = (EditText) findViewById(R.id.report_reason);
        final Button reportSubmitButton =  (Button) findViewById(R.id.report_submit_button);

        reportSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contactConcern = concernEditText.getText().toString();
                reportName = playerEditText.getText().toString();
                reportReason = reasonEditText.getText().toString();

            }
        });
    }

}
