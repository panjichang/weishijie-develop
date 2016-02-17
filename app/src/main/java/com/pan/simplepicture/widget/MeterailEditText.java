package com.pan.simplepicture.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.pan.simplepicture.R;

public class MeterailEditText extends RelativeLayout implements
		OnFocusChangeListener {

	private TextView tv_text;
	private EditText et_text;
	private View divider_small, divider_big;
	private String hint;

	public MeterailEditText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public MeterailEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.customView);
		try {
			hint = a.getString(R.styleable.customView_hint);
		} finally {
			a.recycle();
		}
		init(context);
	}

	public MeterailEditText(Context context) {
		super(context);
		init(context);
	}

	int[] locationY = new int[2];

	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.edit_view,
				this);
		tv_text = (TextView) view.findViewById(R.id.tv_text);
		tv_text.setText(hint);
		et_text = (EditText) view.findViewById(R.id.et_text);
		divider_big = view.findViewById(R.id.divider_big);
		divider_small = view.findViewById(R.id.divider_small);
		et_text.setOnFocusChangeListener(this);
		et_text.getLocationInWindow(locationY);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!TextUtils.isEmpty(et_text.getText().toString())) {
			return;
		}
		ObjectAnimator translationY;
		ObjectAnimator translationX;
		if (hasFocus) {
			if (divider_big.getVisibility() != View.VISIBLE) {
				divider_big.setVisibility(View.VISIBLE);
			}
			tv_text.setTextColor(Color.argb(255, 24, 170, 141));
			translationY = ObjectAnimator.ofFloat(tv_text, "translationY",
					locationY[1], locationY[1] - et_text.getHeight());
			translationX = ObjectAnimator.ofFloat(divider_big, "translationX",
					divider_small.getLeft() - divider_small.getWidth(),
					divider_small.getLeft());
		} else {
			tv_text.setTextColor(Color.argb(255, 172, 172, 172));
			translationY = ObjectAnimator.ofFloat(tv_text, "translationY",
					locationY[1] - et_text.getHeight(), locationY[1]);
			translationX = ObjectAnimator.ofFloat(divider_big, "translationX",
					divider_small.getLeft(), divider_small.getLeft()
							- divider_small.getWidth());
		}
		translationY.setDuration(300);
		translationX.setDuration(300);
		translationY.setInterpolator(new LinearInterpolator());
		translationX.setInterpolator(new LinearInterpolator());
		translationY.start();
		translationX.start();
	}

	public void getFocus() {
		et_text.requestFocus();
	}

	public String getText() {
		return et_text.getText().toString();
	}

	public void setText(String text) {
		et_text.setText(text);
	}
}
