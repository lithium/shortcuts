package com.hlidskialf.android.shortcuts;

import android.os.Bundle;
import android.content.Intent;
import java.util.Set;
import java.util.Iterator;
import android.net.Uri;
import android.content.ComponentName;


import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;




public class Shortcut 
{
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
  public static final int RINGER_MODE_SILENT  = 0;
  public static final int RINGER_MODE_VIBRATE = 1;
  public static final int RINGER_MODE_NORMAL  = 2;
  public static final String ACTION_STREAM_VOLUME="com.hlidskialf.android.shortcuts.action.STREAM_VOLUME";
  public static final String EXTRA_STREAM="com.hlidskialf.android.shortcuts.extra.STREAM";
  public static final String EXTRA_VOLUME="com.hlidskialf.android.shortcuts.extra.VOLUME";
  public static final String EXTRA_SHOW_UI="com.hlidskialf.android.shortcuts.extra.SHOW_UI";
  public static final int STREAM_VOICE = 0;
  public static final int STREAM_SYSTEM = 1;
  public static final int STREAM_RING = 2;
  public static final int STREAM_MUSIC = 3;
  public static final int STREAM_ALARM = 4;
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



  public static String intentToXml(Intent i)
  {
    StringBuilder sb = new StringBuilder("<intent>");
    
    sb.append("<action name=\"").append(i.getAction()).append("\"/>");

    Uri data = i.getData();
    if (data != null) {
      sb.append("<data uri=\"").append(data.toString()).append("\"/>");
    }

    String type = i.getType();
    if (type != null) {
      sb.append("<type mime=\"").append( type ).append("\"/>");
    }
    
    ComponentName pack = i.getComponent();
    if (pack != null) {
      sb.append("<component name=\"").append(pack.flattenToString()).append("\"/>");
    }

    int flags = i.getFlags();
    if (flags != 0) {
      sb.append("<flags value=\"").append(String.valueOf( flags )).append("\"/>");
    }

    Bundle extras = i.getExtras();
    if (extras != null) {
      Set<String> keys = extras.keySet();
      if (keys != null) {
        sb.append("<extras>");
        Iterator<String> it = keys.iterator();
        while (it != null && it.hasNext()) {
          String key = it.next();
          Object value = extras.get(key);
          if (key != null && value != null) {
            sb.append("<extra name=\"").append(key).append("\"")
              .append(" type=\"").append(value.getClass().getName()).append("\"")
              .append(">")
              
              .append(value.toString())
              .append("</extra>");
          }
        } 
        sb.append("</extras>");
      }
    }

    Set<String> cats = i.getCategories();
    if (cats != null) {
      sb.append("<categories>");
      Iterator<String> it = cats.iterator();
      while (it != null && it.hasNext()) {
        String cat = it.next();
        sb.append("<category name=\"").append(cat).append("\"/>");
      }
      sb.append("</categories>");
    }

    sb.append("</intent>");
    return sb.toString();
  }

  public static Intent xmlToIntent(String xml_string)
  {
    XmlPullIntent parser = new XmlPullIntent(xml_string);
    return parser.parseIntent();
  }
  private static class XmlPullIntent 
  {
    private Intent mIntent;
    private XmlPullParserFactory mFactory;
    private XmlPullParser mParser;
    private enum State { NONE, IN_EXTRAS, IN_CATEGORIES };
    private State mState;
    private String mExtraName,mExtraType;

    public XmlPullIntent() { init(null); }
    public XmlPullIntent(String xml_string) { init(xml_string); }
    private void init(String xml_string)
    {
      mIntent = new Intent();
      try {
        mFactory = XmlPullParserFactory.newInstance();
        mFactory.setNamespaceAware(true);
        mParser = mFactory.newPullParser();
        if (xml_string != null) 
          setXml(xml_string);
      } catch (XmlPullParserException e) {
      }
    }
    public void setXml(String xml_string) {
      try {
        mParser.setInput( new StringReader(xml_string) );
      } catch (XmlPullParserException e) {
      }
    }
    public Intent getIntent() { return mIntent; }

    public Intent parseIntent()
    {
      try {
        String tag_name;
        int evt_type = mParser.getEventType();
        mState = State.NONE;
        while (evt_type != XmlPullParser.END_DOCUMENT) {
            switch (evt_type) {
              case XmlPullParser.END_TAG:
                mState = State.NONE;
              break;
              case XmlPullParser.START_TAG:
                tag_name = mParser.getName();
                if (mState == State.NONE) {
                  if (tag_name.equals("extras")) {
                    mState = State.IN_EXTRAS;
                  }
                  else if (tag_name.equals("categories")) {
                    mState = State.IN_CATEGORIES;
                  }
                  else if (tag_name.equals("action")) {
                    mIntent.setAction( mParser.getAttributeValue(null, "name") );
                  }
                  else if (tag_name.equals("data")) {
                    String t = mIntent.getType();
                    if (t == null) 
                      mIntent.setData( Uri.parse(mParser.getAttributeValue(null, "uri")) );
                    else 
                      mIntent.setDataAndType( Uri.parse(mParser.getAttributeValue(null, "uri")), t );
                  }
                  else if (tag_name.equals("type")) {
                    Uri d = mIntent.getData();
                    if (d == null)
                      mIntent.setType( mParser.getAttributeValue(null, "mime") );
                    else
                      mIntent.setDataAndType( d, mParser.getAttributeValue(null, "mime") );
                  }
                  else if (tag_name.equals("component")) {
                    mIntent.setComponent( ComponentName.unflattenFromString(mParser.getAttributeValue(null, "name")) );
                  }
                  else if (tag_name.equals("flags")) {
                    mIntent.setFlags( Integer.decode(mParser.getAttributeValue(null, "value")) );
                  }
                } 
                else if (mState == State.IN_EXTRAS) {
                  if (tag_name.equals("extra")) {
                    mExtraName = mParser.getAttributeValue(null, "name");
                    mExtraType = mParser.getAttributeValue(null, "type");
                  }
                }
                else if (mState == State.IN_CATEGORIES) {
                  if (tag_name.equals("category")) {
                  }
                }
              break;
              case XmlPullParser.TEXT: 
                if (mState == State.IN_EXTRAS && mExtraName != null) {
                  String text = mParser.getText();  
                  try {
                    Class cls = Class.forName(mExtraType);
                    if (cls.equals(java.lang.String.class)) mIntent.putExtra(mExtraName, text);
                    if (cls.equals(java.lang.Integer.class)) mIntent.putExtra(mExtraName, Integer.decode(text));
                    if (cls.equals(java.lang.Long.class)) mIntent.putExtra(mExtraName, Long.decode(text));
                    if (cls.equals(java.lang.Boolean.class)) mIntent.putExtra(mExtraName, Boolean.getBoolean(text));
                    if (cls.equals(java.lang.Double.class)) mIntent.putExtra(mExtraName, Double.parseDouble(text));
                    if (cls.equals(java.lang.Float.class)) mIntent.putExtra(mExtraName, Float.parseFloat(text));
                    if (cls.equals(java.lang.Short.class)) mIntent.putExtra(mExtraName, Short.parseShort(text));
                    if (cls.equals(java.lang.Byte.class)) mIntent.putExtra(mExtraName, Byte.parseByte(text));
                  } catch (java.lang.ClassNotFoundException e) {
                    android.util.Log.v("shortcuts/characters", "class not found: "+mExtraType);
                  }
                }
              break;
            }
            evt_type = mParser.next();
        }
      } catch (XmlPullParserException e) {
        return null;
      } catch (java.io.IOException e) {
        return null;
      }
      return mIntent;
    }
  }


}

