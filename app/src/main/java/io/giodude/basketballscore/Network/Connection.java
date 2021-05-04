package io.giodude.basketballscore.Network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Connection {

    public boolean isConnected(final Activity activity) {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
            Toast.makeText(activity, "Disconnected to Network", Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
            alertDialog.setMessage("\tDisconnected to Network");
            alertDialog.setCancelable(false);
            alertDialog.setNegativeButton("Exit", (dialog, which) -> activity.finish());
            AlertDialog alert = alertDialog.create();
            alert.show();

        }
        return connected;
    }
}
