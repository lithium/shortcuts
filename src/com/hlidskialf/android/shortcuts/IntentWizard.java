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

class IconPicker extends Dialog
{
  private static final int[] RESOURCEIDS = {
    17301534, 17301535, 17301536, 
    17301537, 17301538, 17301539, 
    17301540, 17301541, 17301542, 
    17301543, 17301544, 17301545, 
    17301546, 17301550, 17301597, 
    17301598, 17301599, 17301614, 
    17301615, 17301618, 17301619, 
    17301620, 17301622, 17301623, 
    17301624, 17301625, 17301626, 
    17301627, 17301628, 17301629, 
    17301630, 17301631, 17301632, 
    17301633, 17301634, 17301635, 
    17301636, 17301637, 17301638, 
    17301639, 17301640, 17301641, 
    17301642, 17301645, 17301646, 
    17301647, 17301648, 17301649, 
    17301650, 17301651, 17301555, 
    17301556, 17301557, 17301558, 
    17301559, 17301560, 17301561, 
    17301562, 17301563, 17301564, 
    17301565, 17301566, 17301567, 
    17301568, 17301569, 17301570, 
    17301571, 17301572, 17301573, 
    17301574, 17301575, 17301576, 
    17301577, 17301578, 17301579, 
    17301580, 17301581, 17301582, 
    17301583, 17301584, 17301585, 
    17301586, 17301587, 17301588, 
    17301589, 17301590, 17301591, 
    17301592, 17301593, 17301594, 
  };

  private Context mContext;
  private GridView mGrid;
  private OnIconPickedListener mPickedListener;

  static public interface OnIconPickedListener {
    abstract public void onIconPicked(Drawable icon);
  }

  public IconPicker(Context ctx) {
    super(ctx); 
    mContext = ctx;
    mGrid = new GridView(ctx);
    mGrid.setNumColumns(3);
    setContentView(mGrid);
    setTitle("Pick an Icon");
    mGrid.setAdapter( new BaseAdapter() {
      public View getView(int pos, View convert, ViewGroup parent) {
        ImageView i;
        if (convert == null) {
          i = new ImageView(mContext);
          i.setScaleType(ImageView.ScaleType.FIT_CENTER);
          i.setLayoutParams(new GridView.LayoutParams(48, 48));
        }
        else {
          i = (ImageView) convert;
        }
        i.setImageResource(IconPicker.RESOURCEIDS[pos]);
        return i;
      }
      public final int getCount() { return IconPicker.RESOURCEIDS.length; }
      public final Object getItem(int pos) { return IconPicker.RESOURCEIDS[pos]; }
      public final long getItemId(int pos) { return pos; }
    });
    mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView parent, View view, int pos, long id) {
        if (mPickedListener != null) {
          ImageView iv = (ImageView)view;
          mPickedListener.onIconPicked(iv.getDrawable());
        }
        dismiss();
      }
    });
  }
  public void setOnIconPickedListener(IconPicker.OnIconPickedListener listener) { mPickedListener = listener; }
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
  private Button mUriButton;

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

    mUriButton = (Button)findViewById(R.id.wizard_button3);
    mUriButton.setText(R.string.add_uri);
    mUriButton.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        step_uri();
      }
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
        mStepItem.setAnimation( AnimationUtils.loadAnimation(IntentWizard.this, R.anim.wizard_item_out) );
        mListLayout.removeView(mStepItem);
        step_name();
      }
    });
  }
  private void step_action() {
    do_step(R.string.intent_action, R.string.intent_action_hint, new step_listener() {
      public void step(int title, int hint) { do_step_action(title, hint); }
      public void onComplete(String value) { 
        if (value.length() < 1) 
          onCancel();
        else
          step_uri(); 
      }
      public void onCancel() { 
        mStepItem.setAnimation( AnimationUtils.loadAnimation(IntentWizard.this, R.anim.wizard_item_out) );
        mListLayout.removeView(mStepItem);
        step_action(); 
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
        mUriButton.setVisibility(View.VISIBLE);
        mStepItem.setAnimation( AnimationUtils.loadAnimation(IntentWizard.this, R.anim.wizard_item_out) );
        mListLayout.removeView(mStepItem);
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
      public void onCancel() { 
        mStepItem.setAnimation( AnimationUtils.loadAnimation(IntentWizard.this, R.anim.wizard_item_out) );
        mListLayout.removeView(mStepItem);
      }
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
