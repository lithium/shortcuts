package com.hlidskialf.android.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

public class Shortcuts extends Activity
{
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Intent i = new Intent(BuiltinShortcut.ACTION_STREAM_VOLUME);
    i.putExtra(BuiltinShortcut.EXTRA_STREAM, BuiltinShortcut.STREAM_RING);
    i.putExtra(BuiltinShortcut.EXTRA_VOLUME, 10);
    i.putExtra(BuiltinShortcut.EXTRA_SHOW_UI, true);
    sendBroadcast(i);

  }
}
