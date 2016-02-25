package com.example.indexsidebar.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.indexsidebar.utils.DensityUtils;
/**
 * 联系人 拼音搜索滚动条
 * @author Administrator
 *
 */
public class SideBar extends View {
	private static String textColor = "#636363";
	private static String selectTextColor = "#3399ff";
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	public static String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private int choose = -1;// 选中
	private Paint paint = new Paint();
	private int textSize = 20;
	private int bigTextSize = 42;

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SideBar(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		textSize = DensityUtils.dp2px(context, 14);
		bigTextSize = DensityUtils.dp2px(context, 32);
	}

	/**
	 * 重写这个方法
	 */
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		setBackgroundColor(Color.WHITE);
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / b.length;// 获取每一个字母的高度
		// L.e("刷新!!!!!!");
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.parseColor(textColor));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			// x坐标等于中间-字符串宽度的一半.
			int t = textSize;
			paint.setTextSize(t);
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			// 选中的状态
			if (recordY >= 0) {
				float xp = xPos - textSize * 6 + Math.abs(recordY - yPos);
				if (xp < 0) {
					xPos = xPos + xp;
					t = (t - (int) (xp / 2 + 0.5));
					paint.setColor(Color.argb((int) (textSize * 6 + xp - 0.5), 63, 63, 63));
				}
				if (i == choose) {
					paint.setColor(Color.parseColor(selectTextColor));
					paint.setFakeBoldText(true);
				}
			}
			paint.setTextSize(t);
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}
	}

	float recordY = -1;
	private float oldX;
	private float oldY;
	
	private int touchState;//0:未处理, 1:获取  2:未获取
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_UP:
			// setBackgroundColor(Color.WHITE);
			recordY = -1;
			choose = -1;//
			invalidate();
			break;
		 case MotionEvent.ACTION_DOWN:
			touchState=0;
			oldX = event.getX();
			oldY = event.getY();
			upDateView(event, oldChoose, listener, c);
		 break;
		default:
			//判断是否为横着滑动
			if (touchState==0) {
				float newx = event.getX();
				float newy = event.getY();
				if ((oldX-newx)*(oldX-newx)+(oldY-newy)*(oldY-newy)>bigTextSize) {
					if ((oldX-newx)*(oldX-newx)>(oldY-newy)*(oldY-newy)) {
						//重置状态
						recordY = -1;
						choose = -1;
						invalidate();
						touchState=2;
						return true;
					}else{
						touchState=1;
					}
				}
			}else if (touchState==2) {
				return true;
			}
			
			upDateView(event, oldChoose, listener, c);
			break;
		}
		invalidate();// 自动清屏,屏幕刷新
		return true;
	}

	private void upDateView(MotionEvent event, final int oldChoose, final OnTouchingLetterChangedListener listener,
			final int c) {
		recordY = event.getY();
		if (oldChoose != c) {
			if (c >= 0 && c < b.length) {
				if (listener != null) {
					listener.onTouchingLetterChanged(b[c]);
				}
				choose = c;
				// invalidate();//自动清屏,屏幕刷新
			}
		}
	}

	/**
	 * 向外公开的方法
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 选中字母的监听-接口
	 * @author coder
	 * 
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}