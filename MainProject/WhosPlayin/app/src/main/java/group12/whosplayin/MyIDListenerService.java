package group12.whosplayin;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by gfoudree on 4/14/16.
 */
public class MyIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh()
    {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
