package com.hlidskialf.android.shortcuts;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlShortcuts
{
  private static enum PullState { NONE, INTENT_IN_EXTRA };

  public static String shortcutToXml(Shortcut s)
  {
    StringBuilder sb = new StringBuilder("<shortcut");
    String name = s.getName();
    if (name != null && name.length() > 0)
      sb.append("name=\"").append(name).append("\" ");
    sb.append(">");

    Iterator<Intent> it = s.getIntentIterator();
    while (it.hasNext()) {
      sb.append( intentToXml(it.next()) );
    }
    sb.append("</shortcut>");
    return sb.toString();
  }

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
      }
    }

    Set<String> cats = i.getCategories();
    if (cats != null) {
      Iterator<String> it = cats.iterator();
      while (it != null && it.hasNext()) {
        String cat = it.next();
        sb.append("<category name=\"").append(cat).append("\"/>");
      }
    }

    sb.append("</intent>");
    return sb.toString();
  }

  public static Intent xmlToIntent(String xml_string)
  {
    XmlPullParserFactory factory;
    XmlPullParser parser;
    try {
      factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      parser = factory.newPullParser();
      parser.setInput( new StringReader(xml_string) );
    } catch (XmlPullParserException e) {
      return null;
    }

    return pullIntent(parser);
  }

  public static Shortcut pullShortcut(XmlPullParser parser)
  {
    Shortcut shortcut = new Shortcut();
    String name = null;

    try {
      int evt_type = parser.getEventType(); 
      for (evt_type = parser.getEventType(); evt_type != XmlPullParser.END_DOCUMENT; evt_type = parser.next()) {
        String tag_name = parser.getName();
        switch (evt_type) {
          case XmlPullParser.END_TAG:
            if (tag_name.equals("shortcut")) return shortcut;
          break;
          case XmlPullParser.START_TAG:
            if (tag_name.equals("shortcut")) {
              shortcut.setName( parser.getAttributeValue(null, "name") );
            }
            else if (tag_name.equals("intent")) {
              shortcut.addIntent( pullIntent(parser) );
            }
          break;
        }
      }
    } catch (XmlPullParserException e) {
      return null;
    } catch (java.io.IOException e) {
      return null;
    }

    return shortcut;
  }

  public static Intent pullIntent(XmlPullParser parser)
  {
    Intent intent = new Intent();
    String extra_name = null;
    String extra_type = null;

    try {
      int evt_type;
      for (evt_type = parser.getEventType(); evt_type != XmlPullParser.END_DOCUMENT; evt_type = parser.next()) {
        String tag_name = parser.getName();
        switch (evt_type) {
          case XmlPullParser.END_TAG:
            if (tag_name.equals("extra")) {
              extra_name = null; extra_type = null;
            }
            else
            if (tag_name.equals("intent")) {
              return intent;
            }
          break;
          case XmlPullParser.START_TAG:
            if (tag_name.equals("action")) {
              intent.setAction( parser.getAttributeValue(null, "name") );
            }
            else if (tag_name.equals("data")) {
              String t = intent.getType();
              if (t == null) 
                intent.setData( Uri.parse(parser.getAttributeValue(null, "uri")) );
              else 
                intent.setDataAndType( Uri.parse(parser.getAttributeValue(null, "uri")), t );
            }
            else if (tag_name.equals("type")) {
              Uri d = intent.getData();
              if (d == null)
                intent.setType( parser.getAttributeValue(null, "mime") );
              else
                intent.setDataAndType( d, parser.getAttributeValue(null, "mime") );
            }
            else if (tag_name.equals("component")) {
              intent.setComponent( ComponentName.unflattenFromString(parser.getAttributeValue(null, "name")) );
            }
            else if (tag_name.equals("flags")) {
              intent.setFlags( Integer.decode(parser.getAttributeValue(null, "value")) );
            }
            else if (tag_name.equals("extra")) {
              extra_name = parser.getAttributeValue(null, "name");
              extra_type = parser.getAttributeValue(null, "type");
            }
            else if (tag_name.equals("category")) {
              intent.addCategory( parser.getAttributeValue(null, "name") );
            }
          break;
          case XmlPullParser.TEXT: 
            if (extra_name != null) {
              String text = parser.getText();  
              try {
                Class cls = Class.forName(extra_type);
                if (cls.equals(java.lang.String.class)) intent.putExtra(extra_name, text);
                if (cls.equals(java.lang.Integer.class)) intent.putExtra(extra_name, Integer.decode(text));
                if (cls.equals(java.lang.Long.class)) intent.putExtra(extra_name, Long.decode(text));
                if (cls.equals(java.lang.Boolean.class)) intent.putExtra(extra_name, Boolean.getBoolean(text));
                if (cls.equals(java.lang.Double.class)) intent.putExtra(extra_name, Double.parseDouble(text));
                if (cls.equals(java.lang.Float.class)) intent.putExtra(extra_name, Float.parseFloat(text));
                if (cls.equals(java.lang.Short.class)) intent.putExtra(extra_name, Short.parseShort(text));
                if (cls.equals(java.lang.Byte.class)) intent.putExtra(extra_name, Byte.parseByte(text));
              } catch (java.lang.ClassNotFoundException e) {
                android.util.Log.v("shortcuts/pullintent", "class not found: "+extra_type);
              }
            }
          break;
        }
      }
    } catch (XmlPullParserException e) {
      return null;
    } catch (java.io.IOException e) {
      return null;
    }
    return intent;
  }
}
