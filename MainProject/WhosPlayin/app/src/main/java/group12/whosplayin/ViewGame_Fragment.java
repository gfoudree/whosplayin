package group12.whosplayin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ViewGame_Fragment extends Fragment
{
    private int gameId;
    private Game currentGame;

    private TextView mTitle;
    private TextView mLocation;
    private TextView mTime;
    private TextView mGameType;
    private Button mGameControl;
    private TextView mChatText;
    private EditText mMessageText;
    private Button mSendMessage;

    public ViewGame_Fragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Bundle incoming = this.getArguments();
            gameId = incoming.getInt("GAME_ID", gameId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View currentView = inflater.inflate(R.layout.viewgame_layout, container, false);

        currentGame = new Game();
        currentGame.getGameInfo();

        mTitle = (TextView) currentView.findViewById(R.id.title_text);
        mLocation = (TextView) currentView.findViewById(R.id.location_text);
        mTime = (TextView) currentView.findViewById(R.id.time_text);
        mGameType = (TextView) currentView.findViewById(R.id.gameType_text);
        mChatText = (TextView) currentView.findViewById(R.id.chat_text);
        mMessageText = (EditText) currentView.findViewById(R.id.message_text);
        mGameControl = (Button) currentView.findViewById(R.id.gameControl_button);
        mSendMessage = (Button) currentView.findViewById(R.id.gameControl_button);

        mTitle.setText(currentGame.getEventTitle());
        mLocation.setText(currentGame.getLocation());
        mTime.setText(currentGame.getStartTime() + " - " + currentGame.getEndTime());
        mGameType.setText(currentGame.getGameType());

        // On click listeners
        // TODO, WAITING FOR BACKEND STUFF TO GET DONE.
        mGameControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        // TODO IMPLEMENT THIS WHITH GRANT
        mSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        return currentView;
    }

}
