package com.offgoing.mao.aliframe;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 图片加载接口
 * 
 * @author mark
 *
 */
public interface OnImageLoadListener {
	/**
	 * 图片加载失败
	 * 
	 * @param imageUri
	 * @param view
	 */
	void onLoadFailed(String imageUri, View view);
	/**
	 * 图片加载完成
	 * 
	 * @param imageUri
	 * @param view
	 * @param bitmap
	 */
	void onLoadComplete(String imageUri, View view, Bitmap bitmap);
}
