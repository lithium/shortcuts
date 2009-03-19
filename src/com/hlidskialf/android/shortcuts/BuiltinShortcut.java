
package com.hlidskialf.android.shortcuts;

public class BuiltinShortcut {
  public static final String ON="on";
  public static final String OFF="off";
  public static final String TOGGLE="toggle";

  /* Vibrator */
  public static final String ACTION_VIBRATE="com.hlidskialf.android.shortcuts.action.VIBRATE";
  public static final String EXTRA_VIBRATE_PATTERN="com.hlidskialf.android.shortcuts.extra.VIBRATE_PATTERN";

  /* MediaPlayer */
  public static final String ACTION_PLAY_MEDIA="com.hlidskialf.android.shortcuts.action.PLAY_MEDIA";
  public static final String ACTION_PLAY_SOUND="com.hlidskialf.android.shortcuts.action.PLAY_SOUND";

  /* BuiltinFlashlight */
  public static final String ACTION_FLASHLIGHT="com.hlidskialf.android.shortcuts.action.FLASHLIGHT";
  public static final String EXTRA_FLASHLIGHT_COLOR="com.hlidskialf.android.shortcuts.extra.flashlight.COLOR";
  public static final String EXTRA_FLASHLIGHT_BRIGHTNESS="com.hlidskialf.android.shortcuts.extra.flashlight.BRIGHTNESS";

  /* NotificationManager */
  public static final String ACTION_NOTIFICATION="com.hlidskialf.android.shortcuts.action.NOTIFICATION";
  public static final String EXTRA_NOTIFICATION_MESSAGE="com.hlidskialf.android.shortcuts.extra.notification.MESSAGE";

  /* AudioManager */
  public static final String ACTION_RINGER_MODE="com.hlidskialf.android.shortcuts.action.RINGER_MODE";
  public static final String EXTRA_RINGER_MODE="com.hlidskialf.android.shortcuts.extra.RINGER_MODE";
  public static final String RINGER_MODE_SILENT  = "ringer_mode_silent";
  public static final String RINGER_MODE_VIBRATE = "ringer_mode_vibrate";
  public static final String RINGER_MODE_NORMAL  = "ringer_mode_normal";
  public static final String ACTION_STREAM_VOLUME="com.hlidskialf.android.shortcuts.action.STREAM_VOLUME";
  public static final String EXTRA_STREAM="com.hlidskialf.android.shortcuts.extra.STREAM";
  public static final String EXTRA_VOLUME="com.hlidskialf.android.shortcuts.extra.VOLUME";
  public static final String EXTRA_SHOW_UI="com.hlidskialf.android.shortcuts.extra.SHOW_UI";
  public static final String STREAM_VOICE = "stream_voice";
  public static final String STREAM_SYSTEM = "stream_system";
  public static final String STREAM_RING = "stream_ring";
  public static final String STREAM_MUSIC = "stream_music";
  public static final String STREAM_ALARM = "stream_alarm";
  public static final String ACTION_STREAM_MUTE="com.hlidskialf.android.shortcuts.action.STREAM_MUTE";
  public static final String EXTRA_MUTE="com.hlidskialf.android.shortcuts.extra.MUTE";
  public static final String ACTION_STREAM_SPEAKERPHONE="com.hlidskialf.android.shortcuts.action.STREAM_SPEAKERPHONE";
  public static final String EXTRA_SPEAKERPHONE="com.hlidskialf.android.shortcuts.extra.SPEAKERPHONE";
  public static final String ACTION_STREAM_VIBRATE="com.hlidskialf.android.shortcuts.action.STREAM_VIBRATE";
  public static final String EXTRA_VIBRATE="com.hlidskialf.android.shortcuts.extra.VIBRATE";


  /* Settings.System */
  public static final String ACTION_SETTINGS="com.hlidskialf.android.shortcuts.action.SETTINGS";
  public static final String EXTRA_AIRPLANE_MODE="com.hlidskialf.android.shortcuts.extra.AIRPLANE_MODE";
  public static final String EXTRA_DATA_ROAMING="com.hlidskialf.android.shortcuts.extra.DATA_ROAMING";
  public static final String EXTRA_LOCK_PATTERN_ENABLED="com.hlidskialf.android.shortcuts.extra.LOCK_PATTERN_ENABLED";
  public static final String EXTRA_ON_WHILE_PLUGGED="com.hlidskialf.android.shortcuts.extra.ON_WHILE_PLUGGED";
  public static final String EXTRA_USB_DEBUG="com.hlidskialf.android.shortcuts.extra.USB_DEBUG";
  public static final String EXTRA_DIM_SCREEN="com.hlidskialf.android.shortcuts.extra.DIM_SCREEN";
  public static final String EXTRA_RINGTONE="com.hlidskialf.android.shortcuts.extra.RINGTONE";
  public static final String EXTRA_NOTIFICATION="com.hlidskialf.android.shortcuts.extra.NOTIFICATION";


  public static final String ACTION_BLUETOOTH="com.hlidskialf.android.shortcuts.action.BLUETOOTH";
  public static final String ACTION_GPS="com.hlidskialf.android.shortcuts.action.GPS";
  public static final String ACTION_WIFI="com.hlidskialf.android.shortcuts.action.WIFI";
  public static final String ACTION_3G="com.hlidskialf.android.shortcuts.action.3G";
}
