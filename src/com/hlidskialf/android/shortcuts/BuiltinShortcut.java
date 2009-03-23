
package com.hlidskialf.android.shortcuts;

import android.net.wifi.WifiManager;
import android.media.AudioManager;
import android.content.Intent;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.provider.Settings;

import java.lang.reflect.Method;

public class BuiltinShortcut {
  public static final String EXTRA_ONOFF="com.hlidskialf.android.shortcuts.extra.ONOFF";
  public static final String ON="on";
  public static final String OFF="off";
  public static final String YES="yes";
  public static final String NO="no";
  public static final String TOGGLE="toggle";
  public static boolean onoff_toggle(String state, boolean old_state)
  {
    if (BuiltinShortcut.ON.equals(state)) return true;
    if (BuiltinShortcut.YES.equals(state)) return true;
    if (BuiltinShortcut.OFF.equals(state)) return false;
    if (BuiltinShortcut.NO.equals(state)) return false;
    if (BuiltinShortcut.TOGGLE.equals(state)) return !old_state;
    return true; 
  }

  /* Vibrator */
  public static final String ACTION_VIBRATE="com.hlidskialf.android.shortcuts.action.VIBRATE";
  public static final String EXTRA_VIBRATE_PATTERN="com.hlidskialf.android.shortcuts.extra.VIBRATE_PATTERN";

  /* MediaPlayer */
  public static final String ACTION_PLAY_MEDIA="com.hlidskialf.android.shortcuts.action.PLAY_MEDIA";

  /* BuiltinFlashlight */
  public static final String ACTION_FLASHLIGHT="com.hlidskialf.android.shortcuts.action.FLASHLIGHT";
  public static final String EXTRA_FLASHLIGHT_COLOR="com.hlidskialf.android.shortcuts.extra.flashlight.COLOR";
  public static final String EXTRA_FLASHLIGHT_BRIGHTNESS="com.hlidskialf.android.shortcuts.extra.flashlight.BRIGHTNESS";
  public static final String EXTRA_FLASHLIGHT_DURATION="com.hlidskialf.android.shortcuts.extra.flashlight.DURATION";

  /* NotificationManager, Toast */
  public static final String ACTION_NOTIFICATION="com.hlidskialf.android.shortcuts.action.NOTIFICATION";
  public static final String ACTION_TOAST="com.hlidskialf.android.shortcuts.action.NOTIFICATION";
  public static final String EXTRA_MESSAGE="com.hlidskialf.android.shortcuts.extra.MESSAGE";

  /* AudioManager */
  public static final String ACTION_RINGER_MODE="com.hlidskialf.android.shortcuts.action.RINGER_MODE";
  public static final String EXTRA_RINGER_MODE="com.hlidskialf.android.shortcuts.extra.RINGER_MODE";
  public static final String RINGER_MODE_SILENT  = "silent";
  public static final String RINGER_MODE_VIBRATE = "vibrate";
  public static final String RINGER_MODE_NORMAL  = "normal";
  public static final String ACTION_STREAM_VOLUME="com.hlidskialf.android.shortcuts.action.STREAM_VOLUME";
  public static final String EXTRA_STREAM="com.hlidskialf.android.shortcuts.extra.STREAM";
  public static final String EXTRA_VOLUME="com.hlidskialf.android.shortcuts.extra.VOLUME";
  public static final String EXTRA_SHOW_UI="com.hlidskialf.android.shortcuts.extra.SHOW_UI";
  public static final String STREAM_VOICE = "voice";
  public static final String STREAM_SYSTEM = "system";
  public static final String STREAM_RING = "ring";
  public static final String STREAM_MUSIC = "music";
  public static final String STREAM_ALARM = "alarm";
  public static final String STREAM_MICROPHONE = "microphone";
  public static final String ACTION_STREAM_MUTE="com.hlidskialf.android.shortcuts.action.STREAM_MUTE";
  public static final String ACTION_STREAM_SOLO="com.hlidskialf.android.shortcuts.action.STREAM_SOLO";
  public static final String ACTION_SPEAKERPHONE="com.hlidskialf.android.shortcuts.action.SPEAKERPHONE";
  public static final String ACTION_RINGER_VIBRATE="com.hlidskialf.android.shortcuts.action.RINGER_VIBRATE";
  public static final String ACTION_NOTIFY_VIBRATE="com.hlidskialf.android.shortcuts.action.NOTIFY_VIBRATE";
  public static final String ONLY_SILENT="only_silent";
  public static final String ACTION_PLAY_SOUND_EFFECT="com.hlidskialf.android.shortcuts.action.PLAY_SOUND_EFFECT";
  public static final String EXTRA_SOUND_EFFECT="com.hlidskialf.android.shortcuts.extra.SOUND_EFFECT";
  public static final String EFFECT_KEY_CLICK="key_click";
  public static final String EFFECT_NAV_UP="nav_up";
  public static final String EFFECT_NAV_DOWN="nav_down";
  public static final String EFFECT_NAV_LEFT="nav_left";
  public static final String EFFECT_NAV_RIGHT="nav_right";

  public static class Audio
  {
    public static int sound_effect(String fx)
    {
      if (BuiltinShortcut.EFFECT_KEY_CLICK.equals(fx)) return AudioManager.FX_KEY_CLICK;
      if (BuiltinShortcut.EFFECT_NAV_UP.equals(fx)) return AudioManager.FX_FOCUS_NAVIGATION_UP;
      if (BuiltinShortcut.EFFECT_NAV_DOWN.equals(fx)) return AudioManager.FX_FOCUS_NAVIGATION_DOWN;
      if (BuiltinShortcut.EFFECT_NAV_LEFT.equals(fx)) return AudioManager.FX_FOCUS_NAVIGATION_LEFT;
      if (BuiltinShortcut.EFFECT_NAV_RIGHT.equals(fx)) return AudioManager.FX_FOCUS_NAVIGATION_RIGHT;
      return AudioManager.FX_KEY_CLICK;
    }
    public static int audio_stream(String stream)
    {
      if (BuiltinShortcut.STREAM_ALARM.equals(stream)) return AudioManager.STREAM_ALARM;
      if (BuiltinShortcut.STREAM_MUSIC.equals(stream)) return AudioManager.STREAM_MUSIC;
      if (BuiltinShortcut.STREAM_RING.equals(stream)) return AudioManager.STREAM_RING;
      if (BuiltinShortcut.STREAM_SYSTEM.equals(stream)) return AudioManager.STREAM_SYSTEM;
      if (BuiltinShortcut.STREAM_VOICE.equals(stream)) return AudioManager.STREAM_VOICE_CALL;
      if (BuiltinShortcut.STREAM_MICROPHONE.equals(stream)) return 5; //FIXME 
      return AudioManager.STREAM_RING;
    }
    public static int ringer_mode(String mode)
    {
      if (BuiltinShortcut.RINGER_MODE_NORMAL.equals(mode)) return AudioManager.RINGER_MODE_NORMAL;
      if (BuiltinShortcut.RINGER_MODE_SILENT.equals(mode)) return AudioManager.RINGER_MODE_SILENT;
      if (BuiltinShortcut.RINGER_MODE_VIBRATE.equals(mode)) return AudioManager.RINGER_MODE_VIBRATE;
      return AudioManager.RINGER_MODE_NORMAL;
    }
    public static int vibrate_setting(String state)
    {
      if (BuiltinShortcut.ON.equals(state)) return AudioManager.VIBRATE_SETTING_ON;
      if (BuiltinShortcut.OFF.equals(state)) return AudioManager.VIBRATE_SETTING_OFF;
      if (BuiltinShortcut.ONLY_SILENT.equals(state)) return AudioManager.VIBRATE_SETTING_ONLY_SILENT;
      return AudioManager.VIBRATE_SETTING_ON;
    }
    public static void setStreamVolume(AudioManager am, Intent intent) {
      String stream = intent.getStringExtra(BuiltinShortcut.EXTRA_STREAM);
      int vol = intent.getIntExtra(BuiltinShortcut.EXTRA_VOLUME,100);
      Boolean show_ui = intent.getBooleanExtra(BuiltinShortcut.EXTRA_SHOW_UI, true);
      int s = BuiltinShortcut.Audio.audio_stream(stream);
      int max = am.getStreamMaxVolume(s);
      am.setStreamVolume(s,  (int)((float)max * ((float)vol/100f)), show_ui ? AudioManager.FLAG_SHOW_UI : 0);
    }
    public static void setStreamMute(AudioManager am, Intent intent) {
      String stream = intent.getStringExtra(BuiltinShortcut.EXTRA_STREAM);
      Boolean state = intent.getBooleanExtra(BuiltinShortcut.EXTRA_ONOFF, true);
      if (BuiltinShortcut.STREAM_MICROPHONE.equals(stream)) am.setMicrophoneMute(state);
      else am.setStreamMute(BuiltinShortcut.Audio.audio_stream(stream), state);
    }
    public static void setStreamSolo(AudioManager am, Intent intent) {
      String stream = intent.getStringExtra(BuiltinShortcut.EXTRA_STREAM);
      Boolean state = intent.getBooleanExtra(BuiltinShortcut.EXTRA_ONOFF, true);
      am.setStreamSolo(BuiltinShortcut.Audio.audio_stream(stream), state);
    }
    public static void setSpeakerphone(AudioManager am, Intent intent) {
      Boolean state = intent.getBooleanExtra(BuiltinShortcut.EXTRA_ONOFF, true);
      am.setSpeakerphoneOn(state);
    }
    public static void setRingerMode(AudioManager am, Intent intent) {
      String mode = intent.getStringExtra(BuiltinShortcut.EXTRA_RINGER_MODE);
      am.setRingerMode(BuiltinShortcut.Audio.ringer_mode(mode));
    }
    public static void setRingerVibrate(AudioManager am, Intent intent) {
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_ONOFF);
      am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, BuiltinShortcut.Audio.vibrate_setting(state));
    }
    public static void setNotifyVibrate(AudioManager am, Intent intent) {
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_ONOFF);
      am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, BuiltinShortcut.Audio.vibrate_setting(state));
    }
    public static void playSoundEffect(AudioManager am, Intent intent) {
      String effect = intent.getStringExtra(BuiltinShortcut.EXTRA_SOUND_EFFECT);
      am.loadSoundEffects();
      am.playSoundEffect(BuiltinShortcut.Audio.sound_effect(effect)); 
    }
  }

  public static final String ACTION_GPS_ENABLE="com.hlidskialf.android.shortcuts.action.GPS_ENABLE";
  public static final String EXTRA_GPS_PROVIDER="com.hlidskialf.android.shortcuts.extra.GPS_PROVIDER";
  public static final String GPS_COARSE="coarse";
  public static final String GPS_FINE="fine";
  public static class Gps
  {
    public static String provider(String name)
    {
      if (BuiltinShortcut.GPS_COARSE.equals(name)) return LocationManager.NETWORK_PROVIDER;
      if (BuiltinShortcut.GPS_FINE.equals(name)) return LocationManager.GPS_PROVIDER;
      return LocationManager.GPS_PROVIDER;
    }

    public static void enableProvider(Context ctx, Intent intent) {
      String provider = intent.getStringExtra(BuiltinShortcut.EXTRA_GPS_PROVIDER);
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_ONOFF);
      ContentResolver resolver = ctx.getContentResolver();
      String providers = Settings.System.getString(resolver, Settings.System.LOCATION_PROVIDERS_ALLOWED);
      boolean val = providers.contains(provider);
      boolean onoff = BuiltinShortcut.onoff_toggle(state, val);
      if (onoff) {
        if (!val) providers += "," + provider;
        else return; // no action
      } else {
        if (val) providers.replace(provider, "");
        else return; // no action
      }
      Settings.System.putString(resolver, Settings.System.LOCATION_PROVIDERS_ALLOWED, providers);
      ctx.sendBroadcast( new Intent(Intent.ACTION_PROVIDER_CHANGED) );
    }
  }

  public static final String ACTION_3G_ENABLE="com.hlidskialf.android.shortcuts.action.3G_ENABLE";
  public static class Mobile
  {
    // Yanked from frameworks/base/telephony/java/com/android/internal/telephony/Phone.java
    static final int NT_AUTO_TYPE  = 0;  //   WCDMA preferred (auto mode)
    static final int NT_GSM_TYPE   = 1;  //   GSM only
    static final int NT_WCDMA_TYPE = 2;  //   WCDMA only

    public static void enable3G(Context ctx, Intent intent)
    {
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_ONOFF);
      int networkType = BuiltinShortcut.onoff_toggle(state, true) ? Mobile.NT_WCDMA_TYPE : Mobile.NT_GSM_TYPE;

      try {
        Class cls_factory = Class.forName("com.android.internal.telephony.PhoneFactory");
        Method makeDefaultPhones = cls_factory.getDeclaredMethod("makeDefaultPhones", new Class[] {Context.class});
        makeDefaultPhones.invoke(null,new Object[] {ctx});
        Method getDefaultPhone = cls_factory.getDeclaredMethod("getDefaultPhone", new Class[] {});
        Object phone = getDefaultPhone.invoke(null, (Object[]) null);

        Class cls_phone = Class.forName("com.android.internal.telephony.Phone");
        Method setPreferredNetworkType = cls_phone.getDeclaredMethod("setPreferredNetworkType", new Class[] {Integer.class, android.os.Message.class}); 
        setPreferredNetworkType.invoke(phone, new Object[] {networkType, null});
        
        /*
      } catch (java.lang.ClassNotFoundException e) {
      } catch (java.lang.NoSuchMethodException e) {
      } catch (java.lang.IllegalAccessException e) {
      }
      */
      } catch (java.lang.reflect.InvocationTargetException e) {
        android.util.Log.v("invocationrelfection", e.getTargetException().toString());
        e.getTargetException().printStackTrace();
      } catch (Exception e) {
        android.util.Log.v("3gtoggle","reflection failed! " + e.toString());
      }

    }
  }

  public static final String ACTION_WIFI_ENABLE="com.hlidskialf.android.shortcuts.action.WIFI_ENABLE";
  public static final String ACTION_WIFI_CONNECTION="com.hlidskialf.android.shortcuts.action.WIFI_CONNECTION";
  public static final String EXTRA_WIFI_CONNECTION="com.hlidskialf.android.shortcuts.extra.WIFI_CONNECTION";
  public static final String WIFI_REASSOCIATE="reassociate";
  public static final String WIFI_RECONNECT="reconnect";
  public static final String WIFI_DISCONNECT="disconnect";
  public static final String ACTION_WIFI_SCAN="com.hlidskialf.android.shortcuts.action.WIFI_SCAN";
  public static class Wifi
  {
    public static void setEnabled(WifiManager wm, Intent intent) {
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_ONOFF);
      wm.setWifiEnabled(  BuiltinShortcut.onoff_toggle(state, wm.isWifiEnabled()) );
    }
    public static void setConnection(WifiManager wm, Intent intent) {
      if (!wm.isWifiEnabled()) return;
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_WIFI_CONNECTION);
      if (BuiltinShortcut.WIFI_REASSOCIATE.equals(state)) wm.reassociate();
      if (BuiltinShortcut.WIFI_RECONNECT.equals(state)) wm.reconnect();
      if (BuiltinShortcut.WIFI_DISCONNECT.equals(state)) wm.disconnect();
    }
    public static void startScan(WifiManager wm, Intent intent) {
      wm.startScan();
    }
  }

  public static final String EXTRA_BLUETOOTH="bluetooth";
  public static final String EXTRA_BLUETOOTH_DISCOVERABLE="bluetooth_discover";

  /* Settings.System */
  public static final String ACTION_SYSTEM_SETTING="com.hlidskialf.android.shortcuts.action.SYSTEM_SETTING";
  public static final String EXTRA_SETTING="com.hlidskialf.android.shortcuts.extra.SETTING";
  public static final String EXTRA_VALUE="com.hlidskialf.android.shortcuts.extra.VALUE";

  public static final String SETTING_AIRPLANE_MODE="airplane_mode";
  public static final String SETTING_DEBUG_MODE="debug_mode";
  public static final String SETTING_DATA_ROAMING="data_roaming";
  public static final String SETTING_DIM_SCREEN="dim_screen";
  public static final String SETTING_DTMF_TONE="dtmf_tone";
  public static final String SETTING_LOCK_PATTERN="lock_pattern";
  public static final String SETTING_SHOW_PROCESSES="show_processes";
  public static final String SETTING_SOUND_EFFECTS="sound_effects";
  public static final String SETTING_STAY_ON_WHILE_PLUGGED_IN="stay_on_while_plugged_in";
  public static final String SETTING_TIME_24HR="time_24hr";

  public static final String ACTION_END_BUTTON_BEHAVIOR="com.hlidskialf.android.shortcuts.action.END_BUTTON_BEHAVIOR";
  public static final String ACTION_SET_FONT_SCALE="com.hlidskialf.android.shortcuts.action.SET_FONT_SCALE";
  public static final String ACTION_SCREEN_BRIGHTNESS="com.hlidskialf.android.shortcuts.action.SCREEN_BRIGHTNESS";
  public static final String ACTION_SCREEN_TIMEOUT="com.hlidskialf.android.shortcuts.action.SCREEN_TIMEOUT";

  public static class System
  {
    public static String setting_name(String setting)
    {
      if (BuiltinShortcut.SETTING_AIRPLANE_MODE.equals(setting)) return Settings.System.AIRPLANE_MODE_ON;
      if (BuiltinShortcut.SETTING_DEBUG_MODE.equals(setting)) return Settings.System.ADB_ENABLED;
      if (BuiltinShortcut.SETTING_DEBUG_MODE.equals(setting)) return Settings.System.ADB_ENABLED;
      if (BuiltinShortcut.SETTING_DATA_ROAMING.equals(setting)) return Settings.System.DATA_ROAMING;
      if (BuiltinShortcut.SETTING_DIM_SCREEN.equals(setting)) return Settings.System.DIM_SCREEN;
      if (BuiltinShortcut.SETTING_DTMF_TONE.equals(setting)) return Settings.System.DTMF_TONE_WHEN_DIALING;
      if (BuiltinShortcut.SETTING_LOCK_PATTERN.equals(setting)) return Settings.System.LOCK_PATTERN_ENABLED;
      if (BuiltinShortcut.SETTING_SHOW_PROCESSES.equals(setting)) return Settings.System.SHOW_PROCESSES;
      if (BuiltinShortcut.SETTING_SOUND_EFFECTS.equals(setting)) return Settings.System.SOUND_EFFECTS_ENABLED;
      if (BuiltinShortcut.SETTING_STAY_ON_WHILE_PLUGGED_IN.equals(setting)) return Settings.System.STAY_ON_WHILE_PLUGGED_IN;
      if (BuiltinShortcut.SETTING_TIME_24HR.equals(setting)) return Settings.System.TIME_12_24;
      return null;
    }
    public static void changeSetting(Context ctx, Intent intent)
    {
      android.util.Log.v("changeSetting", "WTF!!");
      ContentResolver resolver = ctx.getContentResolver();
      String setting = intent.getStringExtra(BuiltinShortcut.EXTRA_SETTING);
      String key = BuiltinShortcut.System.setting_name(setting);
      String state = intent.getStringExtra(BuiltinShortcut.EXTRA_VALUE);
      boolean val = Settings.System.getInt(resolver, key, 1) == 1 ? true : false;
      android.util.Log.v("changeSetting", setting +":"+key+"   "+String.valueOf( val ));
      Settings.System.putInt(resolver, key, BuiltinShortcut.onoff_toggle(state, val) ? 1 : 0);
    }
  }


}
