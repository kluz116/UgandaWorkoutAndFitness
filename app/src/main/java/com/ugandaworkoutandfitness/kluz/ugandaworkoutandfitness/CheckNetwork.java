package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by kluz on 5/6/17.
 */

class CheckNetwork {
    private static final String TAG = CheckNetwork.class.getSimpleName();



    public static boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            Log.d(TAG,"no internet connection");
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                Log.d(TAG," internet connection available...");
                return true;
            }
            else
            {
                Log.d(TAG," internet connection");
                return true;
            }

        }
    }

}
