package group12.whosplayin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.LogRecord;

/**
 * Created by kjdwyer on 2/29/16.
 */
public class CreateGame_Fragment extends Fragment
{
    View currentView;
    private EditText mEventTitle;
    private static AutoCompleteTextView mLocation;
    private Spinner mGameType;
    private EditText mMaxPlayers;
    private static EditText mDate;
    private static EditText mStartTime;
    private static EditText mEndTime;
    private EditText mDescription;
    private Button mSubmit;
    private Button mCancel;

    private static String sessionUserName;
    private static String sessionID;
    private static int sessionUserID;

    private UserCreateGameTask mCreateGameTask = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Get incoming bundle.
        Bundle incoming = this.getArguments();
        sessionUserName = incoming.getString("USERNAME");
        sessionUserID = incoming.getInt("USER_ID");
        sessionID = incoming.getString("SESSION_ID");
        Log.d("Incoming Create Game", sessionUserName + ", " + sessionUserID + ", " + sessionID);


        currentView = inflater.inflate(R.layout.creategame_layout, container, false);

        // Make the Fields
        mEventTitle = (EditText) currentView.findViewById(R.id.eventTitle_text);
        mLocation = (AutoCompleteTextView) currentView.findViewById(R.id.location_text);
        mGameType = (Spinner) currentView.findViewById(R.id.gameType_select);
        mMaxPlayers = (EditText) currentView.findViewById(R.id.maxPlayers_text);
        mDate = (EditText) currentView.findViewById(R.id.date_text);
        mStartTime = (EditText) currentView.findViewById(R.id.startTime_text);
        mEndTime = (EditText) currentView.findViewById(R.id.endTime_text);
        mDescription = (EditText) currentView.findViewById(R.id.description_text);
        mSubmit = (Button) currentView.findViewById(R.id.submit_button);
        mCancel = (Button) currentView.findViewById(R.id.cancel_button);


        // Set autocomplete adapters
        mLocation.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));

        // Load spinner values
        String[] gameTypeArray = new String[] {
                "Basketball", "Baseball", "Board Games", "Card Games", "Cricket", "Combat",
                "Combat Sports", "Football", "Golf", "Hockey", "Outdoor Sports", "Running", "Volleyball"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, gameTypeArray);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mGameType.setAdapter(adapter);


        // Set on Click Listener
        mSubmit.setOnClickListener(new View.OnClickListener()
        {
            // Submit button does some database stuff.
            public void onClick(View v)
            {
                attemptSubmit();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener()
        {
            // Cancel button goes back to the home page.
            public void onClick(View v)
            {
                Fragment fragment = null;
                Class fragmentClass = Home_Fragment.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();

                    // Make the outgoing bundle.
                    Bundle outgoing = new Bundle();
                    outgoing.putString("USERNAME", sessionUserName);
                    outgoing.putString("SESSION_ID", sessionID);
                    outgoing.putInt("USER_ID", sessionUserID);
                    fragment.setArguments(outgoing);
                    Log.d("Create Game Out Bundle", sessionUserName + ", " + sessionUserID + ", " + sessionID);
                }

                catch (Exception e)
                {
                    // TODO: Better exception handling
                    e.printStackTrace();
                }

                // Do the Navigation
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.flContent, fragment).commit();

            }
        });

        mLocation.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

        // On date text select, we need to open a datepicker. So call the DatePickerFragment inner
        // class below.
        mDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment dp = new DatePickerFragement();
                dp.show(getFragmentManager(), "DatePicker");
            }
        });

        mStartTime.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                DialogFragment tp = new TimePickerFragment();
                tp.show(getFragmentManager(), "StartTime");

            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                DialogFragment tp = new TimePickerFragment();
                tp.show(getFragmentManager(), "EndTime");
            }
        });

        return currentView;
    }

    /**
     * Function that attempts to submit. It will set errors if certain conditions are not met.
     */
    private void attemptSubmit()
    {
        boolean cancel = false;
        View focusView = null;


        // Get the values when the submnit button is pressed.
        String eventTitle = mEventTitle.getText().toString();
        String location = mLocation.getText().toString();
        String gameType = mGameType.getSelectedItem().toString();
        int maxPlayers = Integer.parseInt(mMaxPlayers.getText().toString());
        String date = mDate.getText().toString();
        String startTime = mStartTime.getText().toString();
        String endTime = mEndTime.getText().toString();
        String description = mDescription.getText().toString();

        if(mCreateGameTask != null)
        {
            return;
        }

        // Check for emtpy event title.
        if(TextUtils.isEmpty(eventTitle))
        {
            mEventTitle.setError("Event Title field cannot be empty.");
            focusView = mEventTitle;
            cancel = true;
        }

        // check for empty location.
        if(TextUtils.isEmpty(location))
        {
            mLocation.setError("Location field cannot be empty.");
            focusView = mLocation;
            cancel = true;
        }

        // check for empty game type.
        if(TextUtils.isEmpty(gameType))
        {
            ((TextView) mGameType.getSelectedView()).setError("Game Type field cannot be empty.");
            focusView = mGameType;
            cancel = true;
        }

        // check for empty max player.
        if(TextUtils.isEmpty(Integer.toString(maxPlayers)))
        {
            mMaxPlayers.setError("Max Players field cannot be empty.");
            focusView = mMaxPlayers;
            cancel = true;
        }

        // check for empty date.
        if(TextUtils.isEmpty(date))
        {
            mDate.setError("Date Field cannot be empty.");
            focusView = mDate;
            cancel = true;
        }

        // check for empty start time.
        if(TextUtils.isEmpty(startTime))
        {
            mStartTime.setError("Start Time field cannot be empty");
            focusView = mStartTime;
            cancel = true;
        }

        // check for empty end time.
        if(TextUtils.isEmpty(endTime))
        {
            mEndTime.setError("End Time field cannot be empty.");
            focusView = mEndTime;
            cancel = true;
        }

        // End Time needs to be greater than Start Time.
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date endD = null;
        Date startD = null;

        try
        {
            startTime = startTime.replaceAll("\\s", "");
            endTime = endTime.replaceAll("\\s", "");
            startD = format.parse(startTime);
            endD = format.parse(endTime);


            Log.d("Start Time", startTime);
            Log.d("End Time", endTime);
            Log.d("Compare Times", Integer.toString(endD.compareTo(startD)));

            if(endD.compareTo(startD) <= 0)
            {
                mEndTime.setError("End Time cannot be before Start Time");
                focusView = mEndTime;
                cancel = true;
            }
        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }

        // no need to check description, not required.
        // check if cancel was set, if it was focus to the first error.
        if(cancel)
        {
            focusView.requestFocus();
        }

        else
        {
            String start = date + " " + startTime + ":00";
            String end = date + " " + endTime+ ":00";
            mCreateGameTask = new UserCreateGameTask(sessionUserName, sessionID, eventTitle, maxPlayers, start, end, gameType, sessionUserID);
            mCreateGameTask.execute((Void) null);
        }

    }

    /**
     * Represents an asynchonus create a game task used to create games.
     */
    public static class UserCreateGameTask extends AsyncTask<Void, Void, Boolean>
    {
        private String userName;
        private String sessionID;
        private String eventTitle;
        private int maxPlayers;
        private String startTime;
        private String endTime;
        private String gameType;
        private int userId;

        UserCreateGameTask(String userName, String sessionID, String eventTitle, int maxPlayers, String startTime, String endTime, String gameType, int userId)
        {
            this.userName = userName;
            this.sessionID = sessionID;
            this.eventTitle = eventTitle;
            this.maxPlayers = maxPlayers;
            this.startTime = startTime;
            this.endTime = endTime;
            this.gameType = gameType;
            this.userId = 1;
        }


        @Override
        protected Boolean doInBackground(Void... params)
        {
            Game game = new Game();

            try
            {
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Inner Class for the time picker fragement that is loaded when a time picker is needed.
     */
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
    {
        private String tag;

        public Dialog onCreateDialog(Bundle savedInstanceState)
        {

            tag = this.getTag();

            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        }

        public void onTimeSet(TimePicker view, int hour, int minute)
        {
            setTime(hour, minute);
        }

        public void setTime(int hour, int minute)
        {
            if(tag.equals("StartTime"))
            {
                mStartTime.setText(hour + " : " + minute);
            }

            else if(tag.equals("EndTime"))
            {
                mEndTime.setText(hour + " : " + minute);
            }

            else
            {
                // Shouldn't happen
            }
        }
    }

    /**
     * Inner class that shows a DatePicker in a fragement. 
     */
    public static class DatePickerFragement extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Calendar calendar = Calendar.getInstance();

            // Get the current date so the calendar can display the right month on the calendar.
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * When the date is selected, call the populate setDate function to update the mDate field.
         * @param view
         * @param year
         * @param month
         * @param day
         */
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
           setDate(year, month + 1, day);
        }

        /**
         * Set the mDate field to show the selected date.
         * @param year
         *  selected year.
         * @param month
         *  selected month
         * @param day
         *  selected day
         */
        public void setDate(int year, int month, int day)
        {
            mDate.setText(year + "-" + month + "-" + day);
        }
    }

    /**
     * Class that makes the Places Auto Complete Work.
     */
    public static class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable
    {
        ArrayList<String> resultList;

        Context mContext;
        int mResource;

        PlaceAPI mPlaceAPI = new PlaceAPI();

        public PlacesAutoCompleteAdapter(Context context, int resource)
        {
            super(context, resource);

            mContext = context;
            mResource = resource;
        }

        /**
         * Gets the size of the result list.
         * @return
         *  size of the result array list.
         */
        public int getCount()
        {
            return resultList.size();
        }

        /**
         * Returns an item that exists at a specific index
         * @param position
         *  position wanted to index
         * @return
         *  item at specific index
         */
        public String getItem(int position)
        {
            return resultList.get(position);
        }

        @Override
        public Filter getFilter()
        {
            Filter filter = new Filter()
            {

                @Override
                protected FilterResults performFiltering(CharSequence constraint)
                {
                    FilterResults filterResults = new FilterResults();
                    if(constraint != null)
                    {
                        try
                        {
                            resultList = mPlaceAPI.autocomplete(constraint.toString());
                            filterResults.values = resultList;
                            filterResults.count = resultList.size();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results)
                {
                    if(results != null && results.count > 0)
                    {
                        notifyDataSetChanged();
                    }

                    else
                    {
                        notifyDataSetInvalidated();
                    }
                }
            };


            return filter;
        }
    }

}



