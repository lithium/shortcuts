package com.hlidskialf.android.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Button;

import android.graphics.drawable.Drawable;

import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.lang.Runnable;

import android.widget.GridView;

class TextEntryDialog extends Dialog
{
  private Context mContext;
  private ViewGroup mLayout;
  private EditText mEditText;
  private TextEntryDialog.Listener mListener;

  public interface Listener {
    public void onOk(TextEntryDialog d, String new_value);
    public void onCancel(TextEntryDialog d);
  }

  public TextEntryDialog(Context ctx, int title_res, int splash_res, String initial)
  {
    super(ctx);
    mContext = ctx;

    if (title_res != 0) setTitle(title_res);

    ViewGroup layout = (ViewGroup)getLayoutInflater().inflate(R.layout.textentrydialog,null);
    setContentView(layout);

    TextView tv = (TextView) layout.findViewById(R.id.textentrydialog_splash);
    if (tv != null && splash_res != 0) tv.setText(splash_res);
    
    mEditText = (EditText) layout.findViewById(R.id.textentrydialog_text);
    if (mEditText != null && initial != null) mEditText.setText(initial);

    Button b = (Button)layout.findViewById(R.id.textentrydialog_ok);
    b.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        if (mListener != null) mListener.onOk(TextEntryDialog.this, mEditText.getText().toString().trim());
        dismiss();
      }
    });
    b = (Button)layout.findViewById(R.id.textentrydialog_cancel);
    b.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        if (mListener != null) mListener.onCancel(TextEntryDialog.this);
        dismiss();
      }
    });
  }

  public void setTextEntryDialogListener(TextEntryDialog.Listener listener) { mListener = listener; }
}


public class IntentWizard extends Activity
{
  LayoutInflater mInflater;
  LinearLayout mListLayout;

  private interface step_listener {
    public void step(int title_res, int hint_res);
    public void onComplete(String value);
    public void onCancel();
  };
  private step_listener mStepListener;
  private TextView mStepItemValue;
  private ViewGroup mStepItem;
  private String[] builtin_actions,builtin_categories;
  private Button mUriButton,mTypeButton,mOkButton;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.wizard);
    mListLayout = (LinearLayout)findViewById(R.id.wizard_list);

    Button b;
    b = (Button)findViewById(R.id.wizard_button1);
    b.setText(R.string.add_category);
    b.setVisibility(View.VISIBLE);
    b.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        step_category();
      }
    });

    b = (Button)findViewById(R.id.wizard_button2);
    b.setText(R.string.add_extra);
    b.setVisibility(View.VISIBLE);

    b = (Button)findViewById(R.id.wizard_button3);
    b.setText(R.string.add_component);
    b.setVisibility(View.VISIBLE);

    b = (Button)findViewById(R.id.wizard_button4);
    b.setText(R.string.add_flags);
    b.setVisibility(View.VISIBLE);

    mUriButton = (Button)findViewById(R.id.wizard_button5);
    mUriButton.setText(R.string.add_uri);
    mUriButton.setVisibility(View.VISIBLE);
    mUriButton.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        step_uri();
      }
    });
    mTypeButton = (Button)findViewById(R.id.wizard_button6);
    mTypeButton.setText(R.string.add_type);
    mTypeButton.setVisibility(View.VISIBLE);
    mTypeButton.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        //step_type();
      }
    });


    
    mOkButton = (Button)findViewById(R.id.wizard_button_ok);
    mOkButton.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
      }
    });
    b = (Button)findViewById(R.id.wizard_button_cancel);
    b.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) { IntentWizard.this.finish(); }
    });





    mInflater = getLayoutInflater();
    builtin_actions = getResources().getStringArray(R.array.builtin_actions);
    builtin_categories = getResources().getStringArray(R.array.builtin_categories);

    step_name();
  }

  private void step_name() {
    do_step(R.string.intent_name, R.string.intent_name_hint, new step_listener() {
      public void step(int title, int hint) { do_step_name(title, hint); }
      public void onComplete(String value) { 
        if (value.length() < 1) 
          onCancel();
        else
          step_action(); 
      }
      public void onCancel() { 
        animate_item_out(new Runnable() { public void run() { step_name(); }});
      }
    });
  }
  private void step_action() {
    do_step(R.string.intent_action, R.string.intent_action_hint, new step_listener() {
      public void step(int title, int hint) { do_step_action(title, hint); }
      public void onComplete(String value) { 
        if (value.length() < 1) 
          onCancel();
        else {
          mOkButton.setEnabled(true);
          step_uri(); 
        }
      }
      public void onCancel() { 
        animate_item_out(new Runnable() { public void run() { step_action(); }});
      }
    });
  }
  private void step_uri() {
    do_step(R.string.intent_uri, R.string.intent_uri_hint, new step_listener() {
      public void step(int title, int hint) { do_step_url(title, hint); }
      public void onComplete(String value) { 
        if (value.length() < 1) 
          onCancel();
        else
          mUriButton.setVisibility(View.GONE);
      }
      public void onCancel() { 
        animate_item_out(null);
        mUriButton.setVisibility(View.VISIBLE);
      }
    });
  }
  private void step_category() {
    do_step(R.string.intent_category, R.string.intent_category_hint, new step_listener() {
      public void step(int title, int hint) { do_step_category(title, hint); }
      public void onComplete(String value) { 
        if (value.length() < 1) 
          onCancel();
      }
      public void onCancel() { animate_item_out(null); }
    });
  }



  private void do_step(int title_res, int hint_res, step_listener listener)
  {
    mStepItem = (ViewGroup)mInflater.inflate(R.layout.wizard_item, null, false);
    TextView tv = (TextView)mStepItem.findViewById(R.id.wizard_item_title);
    tv.setText(title_res);
    tv = (TextView)mStepItem.findViewById(R.id.wizard_item_value);
    tv.setHint(hint_res);

    mStepListener = listener;
    mStepItemValue = tv;

    final int title = title_res;
    final int hint = hint_res;

    Animation anim = AnimationUtils.loadAnimation(this, R.anim.wizard_item_in);
    anim.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation a) { mStepListener.step(title, hint); }
      public void onAnimationRepeat(Animation a) {}
      public void onAnimationStart(Animation a) {}
    });
    mStepItem.setAnimation(anim);
    mListLayout.addView(mStepItem);
  }
  private void animate_item_out(Runnable callback)
  {
    final Runnable cb = callback;
    Animation anim = AnimationUtils.loadAnimation(this, R.anim.wizard_item_out);
    anim.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation a) { 
        mListLayout.removeView(mStepItem); 
        if (cb != null) cb.run();
      }
      public void onAnimationRepeat(Animation a) {}
      public void onAnimationStart(Animation a) {}
    });
    mStepItem.startAnimation(anim);
  }

  private void do_step_name(int title_res, int hint_res)
  {
    TextEntryDialog d = new TextEntryDialog(IntentWizard.this, title_res, hint_res, null);
    d.setTextEntryDialogListener(new TextEntryDialog.Listener() {
      public void onOk(TextEntryDialog d, String new_value) { 
        mStepItemValue.setText(new_value);
        mStepListener.onComplete(new_value); 
      }
      public void onCancel(TextEntryDialog d) { mStepListener.onCancel(); }
    });
    d.show();
  }

  private void do_step_action(int title_res, int hint_res)
  {
    AlertDialog d = new AlertDialog.Builder(IntentWizard.this)
      .setTitle(title_res)
      .setItems(builtin_actions, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          String action = builtin_actions[which].replaceFirst("^ACTION_","android.intent.action.");
          mStepItemValue.setText(action);
          mStepListener.onComplete(action);
        }
      })
      .setOnCancelListener(new DialogInterface.OnCancelListener () {
        public void onCancel(DialogInterface dialog) {
          mStepListener.onCancel();
        }
      })
      .create();
    d.show();

  }
  private void do_step_url(int title_res, int hint_res)
  {
    TextEntryDialog d = new TextEntryDialog(IntentWizard.this, title_res, hint_res, null);
    d.setTextEntryDialogListener(new TextEntryDialog.Listener() {
      public void onOk(TextEntryDialog d, String new_value) { 
        mStepItemValue.setText(new_value);
        mStepListener.onComplete(new_value); 
      }
      public void onCancel(TextEntryDialog d) { mStepListener.onCancel(); }
    });
    d.show();

  }
  private void do_step_category(int title_res, int hint_res)
  {
    AlertDialog d = new AlertDialog.Builder(IntentWizard.this)
      .setTitle(title_res)
      .setItems(builtin_categories, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          String action = builtin_categories[which].replaceFirst("^CATEGORY_","android.intent.category.");
          mStepItemValue.setText(action);
          mStepListener.onComplete(action);
        }
      })
      .setOnCancelListener(new DialogInterface.OnCancelListener () {
        public void onCancel(DialogInterface dialog) {
          mStepListener.onCancel();
        }
      })
      .create();
    d.show();

  }


}
