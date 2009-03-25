package com.hlidskialf.android.shortcuts;

import android.app.TabActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import android.view.View;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Button;

public class Shortcuts extends TabActivity
{
  private static final int RESULT_SHORTCUT_WIZARD=1;

  //private ArrayList<Intent> intents;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    TabHost host = getTabHost();

    LayoutInflater li = getLayoutInflater();
    View v = li.inflate(R.layout.main, host.getTabContentView(), true);

    Button b = (Button)v.findViewById(R.id.button_new_intent);
    b.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        android.util.Log.v("shortcuts","clicked");
        Intent i = new Intent(Shortcuts.this, IntentWizard.class);
        startActivityForResult(i, RESULT_SHORTCUT_WIZARD);
      }
    });

    host.addTab(host.newTabSpec("scripts") .setIndicator("Scripts") .setContent(R.id.tab_scripts));
    host.addTab(host.newTabSpec("intents") .setIndicator("Intents") .setContent(R.id.tab_intents));
    host.addTab(host.newTabSpec("filters") .setIndicator("Filters") .setContent(R.id.tab_intents));


/*
    setContentView(R.layout.main);

    Intent i = new Intent(BuiltinShortcut.ACTION_STREAM_VOLUME);
    i.putExtra(BuiltinShortcut.EXTRA_STREAM, BuiltinShortcut.STREAM_RING);
    i.putExtra(BuiltinShortcut.EXTRA_VOLUME, 10);
    i.putExtra(BuiltinShortcut.EXTRA_SHOW_UI, true);
    sendBroadcast(i);
    */

/*
    Intent i = new Intent(BuiltinShortcut.ACTION_3G_ENABLE);
    i.putExtra(BuiltinShortcut.EXTRA_ONOFF, "on");
    sendBroadcast(i);
    BuiltinShortcut.Mobile.enable3G(this,new Intent());
    */

  }
}
