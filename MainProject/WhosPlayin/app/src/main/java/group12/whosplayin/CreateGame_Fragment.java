package group12.whosplayin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

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
    private static EditText mLocation;
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

    private double latitude;
    private double longitude;
    private double altitude = 1.0;
    private int zipCode;
    private String city;
    private String state;
    private String location;

    private UserCreateGameTask mCreateGameTask = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Get important user information... TODO Figure out why the userID always returns 0...
        // Something is wrong with the authentication... hard coding in user id of 19.
        sessionUserName = User.getInstance().getUsername();
        sessionUserID = 19;
        sessionID = User.getInstance().getSessionId();
        Log.d("Incoming Create Game", sessionUserName + ", " + sessionUserID + ", " + sessionID);


        currentView = inflater.inflate(R.layout.creategame_layout, container, false);

        // Make the Fields
        mEventTitle = (EditText) currentView.findViewById(R.id.eventTitle_text);
        mLocation = (EditText) currentView.findViewById(R.id.location_text);
        mGameType = (Spinner) currentView.findViewById(R.id.gameType_select);
        mMaxPlayers = (EditText) currentView.findViewById(R.id.maxPlayers_text);
        mDate = (EditText) currentView.findViewById(R.id.date_text);
        mStartTime = (EditText) currentView.findViewById(R.id.startTime_text);
        mEndTime = (EditText) currentView.findViewById(R.id.endTime_text);
        mDescription = (EditText) currentView.findViewById(R.id.description_text);
        mSubmit = (Button) currentView.findViewById(R.id.submit_button);
        mCancel = (Button) currentView.findViewById(R.id.cancel_button);

        // Location Text Box jack
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click");
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        // Load spinner values
        String[] gameTypeArray = new String[] {
                "Basketball", "Baseball", "Board Games", "Card Games", "Cricket", "Combat",
                "Combat Sports", "Football", "Golf", "Hockey", "Outdoor Sports", "Running", "Volleyball"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, gameTypeArray);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mGameType.setAdapter(adapter);


        // Submit button on click listner. On click we'll submit their data and go back to the
        // home screen.
        mSubmit.setOnClickListener(new View.OnClickListener()
        {
            // Submit button does some database stuff.
            public void onClick(View v)
            {
                attemptSubmit();
            }
        });

        // Cancel on click listener. On click we would go back to the home screen.
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

        // Start time on click listener. On click we are going to launch a time picker.
        mStartTime.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                DialogFragment tp = new TimePickerFragment();
                tp.show(getFragmentManager(), "StartTime");

            }
        });

        // End time on lick listener. ON click we are going to launch a time picker.
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

    @Override
    /**
     * On activity result for the place picker.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == 1
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, getActivity().getApplicationContext());
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            final LatLng latLong = place.getLatLng();
            latitude = latLong.latitude;
            longitude = latLong.longitude;
            city = "Ames";
            state = "Iowa";
            zipCode = 50014;

            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null)
            {
                attributions = "";
            }

            mLocation.setText(name);

        }

        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
        location = mLocation.getText().toString();
        String gameType = mGameType.getSelectedItem().toString();
        int maxPlayers = Integer.parseInt(mMaxPlayers.getText().toString());
        String date = mDate.getText().toString();
        String startTime = mStartTime.getText().toString();
        String endTime = mEndTime.getText().toString();
        String description = mDescription.getText().toString();

        int gameTypeID = 1;

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

            endTime = endTime.replaceAll("\\s", "");
            startD = format.parse(startTime);
            endD = format.parse(endTime);

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

        if(cancel)
        {
            focusView.requestFocus();
        }

        else
        {

            String finalStart = convertTime(startTime);
            String finalEnd = convertTime(endTime);


            Log.d("TIME", finalStart + " - " + finalEnd);

            mCreateGameTask = new UserCreateGameTask(eventTitle, gameTypeID, 1, maxPlayers, "2016-05-05 00:00:00",
                    finalStart, finalEnd, sessionUserID, zipCode, altitude, latitude, longitude,
                    state, city, location);
            mCreateGameTask.execute((Void) null);
        }

    }

    /**
     * Helper function to converts times to the proper format for the datbase.
     * @param time
     * @return
     */
    private String convertTime(String time)
    {
        time = time.replaceAll("\\s", "");
        time = time + ":00.0000";

        return time;
    }

    /**
     * Represents an asynchonus create a game task used to create games.
     */
    public static class UserCreateGameTask extends AsyncTask<Void, Void, Boolean>
    {
        private String gameTitle;
        private int gameTypeID;
        private int numPlayers;
        private int maxPlayers;
        private String dateCreated;
        private String startTime;
        private String endTime;
        private int captainID;
        private int zipCode;
        private double altitude;
        private double latitude;
        private double longitude;
        private String state;
        private String city;
        private String location;

        UserCreateGameTask(String gameTitle, int gameTypeID, int numPlayers, int maxPlayers,
                           String dateCreated, String startTime, String endTime, int captainID,
                           int zipCode, double altitude, double latitude, double longitude,
                           String state, String city, String location)
        {
            this.gameTitle = gameTitle;
            this.gameTypeID = gameTypeID;
            this.numPlayers = numPlayers;
            this.maxPlayers = maxPlayers;
            this.dateCreated = dateCreated;
            this.startTime = startTime;
            this.endTime = endTime;
            this.captainID = captainID;
            this.zipCode = zipCode;
            this.altitude = altitude;
            this.latitude = latitude;
            this.longitude = longitude;
            this.state =  state;
            this.city = city;
            this.location = location;
        }


        @Override
        protected Boolean doInBackground(Void... params)
        {
            Boolean success = false;

            Game game = new Game(0, gameTitle, gameTypeID, numPlayers, maxPlayers, dateCreated, startTime,
                    endTime, captainID, latitude, longitude, location);

            Log.d("Game", game.toString());
            game.setZipcode(zipCode);
            game.setAltitude(altitude);
            game.setState(state);
            game.setCity(city);

            try {
                success = game.createGame(User.getInstance(), game);
                Log.d("User", User.getInstance().toString());
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }


            return success;

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



