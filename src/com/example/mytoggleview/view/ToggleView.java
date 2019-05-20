package com.example.mytoggleview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author niyang
 *
 */
public class ToggleView extends View {

	private Bitmap switchBackgroundBitmap;
	private Bitmap SlideButtonBitmap;
	private Paint paint;
	private boolean mSwitchState = false;
	private float currentX;
	private OnSwitchStateUpdateListener onSwitchStateUpdateListener;

	public ToggleView(Context context) {
		super(context);
		init();
	}
	
	
	/**设置属性
	 * @param context
	 * @param attrs
	 */
	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		String namespace = "http://schemas.android.com/apk/res/com.example.mytoggleview";

		int SwitchBackgarounde = attrs.getAttributeResourceValue(namespace, "switch_background", -1);
		int SlideButtonBackgaround = attrs.getAttributeResourceValue(namespace, "slide_backgaround", -1);

		mSwitchState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);

		setSwitchBackgaroundResource(SwitchBackgarounde);
		setSlideButtonBackgaround(SlideButtonBackgaround);

		setState(mSwitchState);
	}

	public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public void init() {
		paint = new Paint();
	}

	boolean isTouch = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			isTouch = true;
			currentX = event.getX();

			break;
		case MotionEvent.ACTION_UP:

			currentX = event.getX();
			isTouch = false;

			float center = switchBackgroundBitmap.getWidth() / 2.0f;
			boolean state = currentX > center;

			if (state != mSwitchState && onSwitchStateUpdateListener != null) {
				// TODO
				onSwitchStateUpdateListener.onStateUpdate(state);
			}
			mSwitchState = state;
			break;
		case MotionEvent.ACTION_MOVE:
			currentX = event.getX();

			break;
		}
		invalidate();// 会引发onDraw被调用,里面的变量会重新生效

		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(switchBackgroundBitmap.getWidth(), switchBackgroundBitmap.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(switchBackgroundBitmap, 0, 0, paint);
		System.out.println("isTouch" + isTouch);
		if (isTouch) {
			// 根据用户触摸到的位置划动滑块
			float newLeft = currentX - SlideButtonBitmap.getWidth() / 2.0f;
			float maxLeft = switchBackgroundBitmap.getWidth() - SlideButtonBitmap.getWidth();
			if (newLeft < 0) {
				newLeft = 0;
			} else if (newLeft > maxLeft) {
				newLeft = maxLeft;
			}
			canvas.drawBitmap(SlideButtonBitmap, newLeft, 0, paint);
		} else {

			if (mSwitchState) {
				int left = switchBackgroundBitmap.getWidth() - SlideButtonBitmap.getWidth();
				canvas.drawBitmap(SlideButtonBitmap, left, 0, paint);
			} else {
				canvas.drawBitmap(SlideButtonBitmap, 0, 0, paint);
			}
		}
	}

	/**
	 * 设置背景图片
	 * 
	 * @param switchBackground
	 */
	public void setSwitchBackgaroundResource(int switchBackground) {
		switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * 设置滑块图片资源
	 * 
	 * @param slideButton
	 */
	public void setSlideButtonBackgaround(int slideButton) {
		SlideButtonBitmap = BitmapFactory.decodeResource(getResources(), slideButton);
	}

	/**
	 * 设置滑块当前的状态
	 * 
	 * @param b
	 */
	public void setState(boolean mSwitchState) {
		this.mSwitchState = mSwitchState;
	}

	public interface OnSwitchStateUpdateListener {
		public void onStateUpdate(boolean state);
	}

	public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
		this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;

	}

}
