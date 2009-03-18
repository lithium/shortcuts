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

        /*
        Intent i = new Intent(this, Shortcuts.class);
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setDataAndType(Uri.parse("foo://foobar/baz/quux"), "text/plain");
        i.putExtra("FOO", (byte)42);
        i.putExtra("BAR", 42L);
        i.putExtra("foobar", new String[] {"foo","bar","baz"});

        android.util.Log.v("shorcuts/intent", i.toString());

        String str = Shortcut.intentToXml(i);
        android.util.Log.v("shorcuts/toXML", str);

        Intent intent = Shortcut.xmlToIntent(str);
        android.util.Log.v("shorcuts/toIntent", intent.toString());
*/

        Intent i = new Intent("com.hlidskialf.android.shortcuts.builtin.VOLUME");
        i.putExtra("com.hlidskialf.android.shortcuts.builtin.VOLUME_STREAM", "volume_ring"); 
        i.putExtra("com.hlidskialf.android.shortcuts.builtin.VOLUME_VALUE", 4);
        sendBroadcast(i);

    }
}
