package group12.whosplayin;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gfoudree on 4/13/16.
 */
public class GpsService extends Service {

    private Gps gps;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("Info", "Service started!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                gps = new Gps(getApplicationContext());
                Looper.loop();
            }
        }).start();

        return START_STICKY; //Keeps going until we tell it to stop
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("Info", "Service stopped!");
    }
}
