package com.hlidskialf.android.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;


public class Shortcuts extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent i = new Intent(this, Shortcuts.class);
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setDataAndType(Uri.parse("foo://foobar/baz/quux"), "text/plain");
        i.putExtra("FOO", "bar");

        android.util.Log.v("shorcuts/intent", i.toString());

        String str = ShortcutScripts.intentToXml(i);
        android.util.Log.v("shorcuts/toXML", str);

        Intent intent = ShortcutScripts.xmlToIntent(str);
        android.util.Log.v("shorcuts/toIntent", intent.toString());

    }
}