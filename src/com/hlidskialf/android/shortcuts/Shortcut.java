package com.hlidskialf.android.shortcuts;

import android.content.Intent;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Shortcut 
{
  private String mName;
  private Bitmap mIcon;
  private ArrayList<Intent> mIntents;

  public Shortcut() { init(); }
  public Shortcut(Intent intent) { init(); addIntent(intent); }
  public Shortcut(Intent intent, String name) { init(); addIntent(intent); setName(name); }
  public Shortcut(Intent intent, String name, Bitmap icon) { init(); addIntent(intent); setName(name); setIcon(icon); }
  private void init() {
    mIntents = new ArrayList<Intent>(1);
  }

  public Shortcut setName(String name) { mName = name; return this; }
  public Shortcut setIcon(Bitmap icon) { mIcon = icon; return this; }
  public String getName() { return mName; }
  public Bitmap getIcon() { return mIcon; }
  public Shortcut addIntent(Intent intent) { mIntents.add(intent); return this; }
  public Shortcut addIntent(int position, Intent intent) { mIntents.add(position, intent); return this; }
  public boolean addAllIntents(Collection<Intent> intents) { return mIntents.addAll(intents); }
  public ArrayList<Intent> getIntents() { return mIntents; }
  public Iterator getIntentIterator() { return mIntents.iterator(); }
  public Intent getIntent(int position) { return mIntents.get(position); }
  public boolean hasIntent(Intent intent) { return mIntents.contains(intent); }
  public Intent removeIntent(Intent intent) { mIntents.remove(intent); return intent; }


  /*public class ShortcutScript extends ArrayList<Shortcut>
  {
    public ShortcutScript() { super(); }
    public ShortcutScript(int capacity) { super(capacity); }
  }*/
}

