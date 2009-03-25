package com.hlidskialf.android.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.view.animation.AnimationUtils;

public class IntentWizard extends Activity
{
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.wizard);
    LayoutInflater li = getLayoutInflater();
    LinearLayout layout = (LinearLayout)findViewById(R.id.wizard);


    View v = li.inflate(R.layout.wizard_item, null, false);

    TextView tv = (TextView)v.findViewById(R.id.wizard_item_title);
    tv.setText("Name");

    tv = (TextView)v.findViewById(R.id.wizard_item_value);
    tv.setHint("the name of your new intent");

    v.setAnimation( AnimationUtils.loadAnimation(this, R.anim.wizard_item) );
    layout.addView(v);
  }
}
