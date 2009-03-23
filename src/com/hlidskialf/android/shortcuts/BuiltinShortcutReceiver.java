
package com.hlidskialf.android.shortcuts;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class BuiltinShortcutReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if (BuiltinShortcut.ACTION_STREAM_VOLUME.equals(action)) BuiltinShortcut.Audio.setStreamVolume(am,intent);
        else if (BuiltinShortcut.ACTION_STREAM_MUTE.equals(action)) BuiltinShortcut.Audio.setStreamMute(am,intent);
        else if (BuiltinShortcut.ACTION_STREAM_SOLO.equals(action)) BuiltinShortcut.Audio.setStreamSolo(am,intent);
        else if (BuiltinShortcut.ACTION_SPEAKERPHONE.equals(action)) BuiltinShortcut.Audio.setSpeakerphone(am,intent);
        else if (BuiltinShortcut.ACTION_RINGER_MODE.equals(action)) BuiltinShortcut.Audio.setRingerMode(am,intent);
        else if (BuiltinShortcut.ACTION_RINGER_VIBRATE.equals(action)) BuiltinShortcut.Audio.setRingerVibrate(am,intent);
        else if (BuiltinShortcut.ACTION_NOTIFY_VIBRATE.equals(action)) BuiltinShortcut.Audio.setNotifyVibrate(am,intent);
        else if (BuiltinShortcut.ACTION_PLAY_SOUND_EFFECT.equals(action)) BuiltinShortcut.Audio.playSoundEffect(am,intent);
        else if (BuiltinShortcut.ACTION_WIFI_ENABLE.equals(action)) BuiltinShortcut.Wifi.setEnabled(wm,intent);
        else if (BuiltinShortcut.ACTION_WIFI_CONNECTION.equals(action)) BuiltinShortcut.Wifi.setConnection(wm,intent);
        else if (BuiltinShortcut.ACTION_WIFI_SCAN.equals(action)) BuiltinShortcut.Wifi.setConnection(wm,intent);
        else if (BuiltinShortcut.ACTION_GPS_ENABLE.equals(action)) BuiltinShortcut.Gps.enableProvider(context,intent);
    }
}
