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

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.GridView;

class TextEntryDialog extends Dialog
{
  private Context mContext;
  private ViewGroup mLayout;
  private EditText mEditText;
  private TextEntryDialogListener mListener;

  public interface TextEntryDialogListener {
    public void onOk(TextEntryDialog d, String new_value);
    public void onCancel(TextEntryDialog d);
  }

  public TextEntryDialog(Context ctx, String title, String splash, String initial)
  {
    super(ctx);
    mContext = ctx;

    setTitle(title);

    ViewGroup layout = (ViewGroup)getLayoutInflater().inflate(R.layout.textentrydialog,null);
    setContentView(layout);

    TextView tv = (TextView) layout.findViewById(R.id.textentrydialog_splash);
    if (tv != null) tv.setText(splash);
    
    mEditText = (EditText) layout.findViewById(R.id.textentrydialog_text);
    if (mEditText != null) mEditText.setText(initial);

    Button b = (Button)layout.findViewById(R.id.textentrydialog_ok);
    b.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        if (mListener != null) mListener.onOk(TextEntryDialog.this, mEditText.getText().toString());
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

  public void setTextEntryDialogListener(TextEntryDialogListener listener) { mListener = listener; }
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

    Animation anim = AnimationUtils.loadAnimation(this, R.anim.wizard_item);
    anim.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation a) {
        //Dialog d = new IconPicker(IntentWizard.this);
        Dialog d = new TextEntryDialog(IntentWizard.this, "Intent Name", "Name this intent", "");
        d.show();
      }
      public void onAnimationRepeat(Animation a) {}
      public void onAnimationStart(Animation a) {}
    });
    v.setAnimation(anim);
    layout.addView(v);
  }


}
