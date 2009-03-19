
package com.hlidskialf.android.shortcuts;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

public class BuiltinShortcutReceiver extends BroadcastReceiver
{
    private Intent mIntent;
    private Context mContext;

    public void onReceive(Context context, Intent intent)
    {
        mContext = context;
        mIntent = intent; 

        String xml = XmlShortcuts.intentToXml(mIntent);
        android.util.Log.v("shortcuts/toXml", xml);

        Intent i = XmlShortcuts.xmlToIntent(xml);
        android.util.Log.v("shortcuts/toIntent", i.toString());
        
        xml = XmlShortcuts.intentToXml(mIntent);
        android.util.Log.v("shortcuts/toXml", xml);

    }
}
