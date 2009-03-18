package com.hlidskialf.android.shortcuts;

import android.os.Bundle;
import android.content.Intent;
import java.util.Set;
import java.util.Iterator;
import android.net.Uri;
import android.content.ComponentName;


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import java.io.StringReader;




public class Shortcut 
{

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

  public static class IntentXMLHandler extends DefaultHandler {
    private Intent mIntent;
    private enum State { NONE, EXTRAS, CATEGORIES, READING_EXTRA };
    private State mState;
    private String mExtraName,mExtraType;

    public IntentXMLHandler() { mState = State.NONE; mIntent = new Intent(); }
    public Intent getIntent() { return mIntent; }

    public void startElement(String uri, String name, String qName, Attributes attrs) {
      name = name.trim();
      if (mState == State.EXTRAS) startElement_EXTRAS(uri, name, qName, attrs);
      else if (mState == State.CATEGORIES) startElement_CATEGORIES(uri, name, qName, attrs);
      else if (mState == State.NONE)  startElement_NONE(uri, name, qName, attrs);
    }
    private void startElement_NONE(String uri, String name, String qName, Attributes attrs) {
      if (name.equals("extras")) {
        mState = State.EXTRAS;
      }
      else if (name.equals("categories")) {
        mState = State.CATEGORIES;
      }
      else if (name.equals("action")) {
        mIntent.setAction( attrs.getValue("name") );
      }
      else if (name.equals("data"))  {
        String t = mIntent.getType();
        if (t == null)
          mIntent.setData( Uri.parse(attrs.getValue("uri")) );
        else
          mIntent.setDataAndType( Uri.parse(attrs.getValue("uri")), t );
      }
      else if (name.equals("type"))  {
        Uri u = mIntent.getData();
        if (u == null) 
          mIntent.setType( attrs.getValue("mime") );
        else
          mIntent.setDataAndType(u, attrs.getValue("mime"));
      }
      else if (name.equals("component"))  {
        mIntent.setComponent( ComponentName.unflattenFromString( attrs.getValue("name") ) );
      }
      else if (name.equals("flags"))  {
        mIntent.setFlags( Integer.decode( attrs.getValue("value") ) );
      }
    }
    private void startElement_EXTRAS(String uri, String name, String qName, Attributes attrs) {
      if (name.equals("extra")) {
        mExtraName = attrs.getValue("name");
        mExtraType = attrs.getValue("type");
      }
    }
    private void startElement_CATEGORIES(String uri, String name, String qName, Attributes attrs) {
      if (name.equals("category")) {
        mIntent.addCategory(attrs.getValue("name"));
      }
    }
    @Override
    public void endElement(String uri, String name, String qName) {
      if (name.equals("extras")) {
        mState = State.NONE;
        mExtraName = null;
      }
      else if (name.equals("categories")) {
        mState = State.NONE;
      }
    }
    @Override
    public void characters(char ch[], int start, int length) 
    {
      if (mState == State.EXTRAS && mExtraName != null) {
        String chars = new String(ch, start,length);
        android.util.Log.v("shortcuts/characters", chars);
        try {
          Class cls = Class.forName(mExtraType);
          if (cls.equals(java.lang.String.class)) mIntent.putExtra(mExtraName, chars);
          if (cls.equals(java.lang.Integer.class)) mIntent.putExtra(mExtraName, Integer.decode(chars));
          if (cls.equals(java.lang.Long.class)) mIntent.putExtra(mExtraName, Long.decode(chars));
          if (cls.equals(java.lang.Boolean.class)) mIntent.putExtra(mExtraName, Boolean.getBoolean(chars));
          if (cls.equals(java.lang.Double.class)) mIntent.putExtra(mExtraName, Double.parseDouble(chars));
          if (cls.equals(java.lang.Float.class)) mIntent.putExtra(mExtraName, Float.parseFloat(chars));
          if (cls.equals(java.lang.Short.class)) mIntent.putExtra(mExtraName, Short.parseShort(chars));
          if (cls.equals(java.lang.Byte.class)) mIntent.putExtra(mExtraName, Byte.parseByte(chars));
        } catch (java.lang.ClassNotFoundException e) {
          android.util.Log.v("shortcuts/characters", "class not found: "+mExtraType);
        }
      }
    }
  }

  public static Intent xmlToIntent(String xml_string)
  {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      XMLReader reader = parser.getXMLReader();
      IntentXMLHandler handler = new Shortcut.IntentXMLHandler();
      reader.setContentHandler(handler);
      reader.parse(new InputSource(new StringReader(xml_string)));
      return handler.getIntent();
    } catch (javax.xml.parsers.ParserConfigurationException e) {
    } catch (org.xml.sax.SAXException e) {
    } catch (java.io.IOException e) {
    }
    return null;
  }
}
