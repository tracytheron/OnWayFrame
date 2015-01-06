/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.offgoing.mao.aliframe.bitmap.core;

import java.io.FileDescriptor;
import com.offgoing.mao.aliframe.bitmap.assist.ImageSize;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapDecoder {
	
	private BitmapDecoder(){}

    public static Bitmap decodeBitmapFromResource(Resources res, int resId, BitmapDisplayConfig config) {
    	if (null == config
    			|| !config.isCompress()) {
    		try {
    			return BitmapFactory.decodeResource(res, resId);
    		} catch (OutOfMemoryError e) {
	   			e.printStackTrace();
	   			return null;
    		}
    		
   		} else {
   			final BitmapFactory.Options options = new BitmapFactory.Options();
   	        options.inJustDecodeBounds = true;
   	        options.inPurgeable = true;
   	        BitmapFactory.decodeResource(res, resId, options);
   	        
   	        ImageSize srcSize = new ImageSize(options.outWidth, options.outHeight);
   	        ImageSize targetSize = new ImageSize(config.getBitmapWidth(), config.getBitmapHeight());
         
   	        options.inSampleSize = calculateInSampleSize(srcSize, targetSize);
   	        
   	        options.inJustDecodeBounds = false;
   	        try {
   	        	 return BitmapFactory.decodeResource(res, resId, options);
   			} catch (OutOfMemoryError e) {
   				 e.printStackTrace();
   				 return null;
   			}
   		}
    }

    public static Bitmap decodeBitmapFromDescriptor(FileDescriptor fileDescriptor, BitmapDisplayConfig config) {
    	if (null == config
    			|| !config.isCompress()) {
    		try {
    			return BitmapFactory.decodeFileDescriptor(fileDescriptor);
    		} catch (OutOfMemoryError e) {
	   			e.printStackTrace();
	   			return null;
    		}
    		
   		} else {
   			final BitmapFactory.Options options = new BitmapFactory.Options();
   	        options.inJustDecodeBounds = true;
   	        options.inPurgeable = true;
   	        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
   	        
   	        ImageSize srcSize = new ImageSize(options.outWidth, options.outHeight);
   	        ImageSize targetSize = new ImageSize(config.getBitmapWidth(), config.getBitmapHeight());
         
   	        options.inSampleSize = calculateInSampleSize(srcSize, targetSize);
   	        
   	        options.inJustDecodeBounds = false;
   	        try {
   	        	 return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
   			} catch (OutOfMemoryError e) {
   				 e.printStackTrace();
   				 return null;
   			}
   		}
    }
    
    public static Bitmap decodeBitmapFromByteArray(byte[] data,  int offset, int length, BitmapDisplayConfig config) {
    	if (null == config
    			|| !config.isCompress()) {
    		 try {
            	 return  BitmapFactory.decodeByteArray(data, offset, length);
    		} catch (OutOfMemoryError e) {
    			 e.printStackTrace();
    			 return null;
    		}
    		
    	} else {
    		final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true; // 内存不足回收
            BitmapFactory.decodeByteArray(data, offset, length, options);
            
            /**
             *  ALPHA_8      每个像素只要1字节~可惜只能代表透明度,没有颜色属性
				ARGB_4444    每个像素要2字节~带透明度的颜色~可惜官方不推荐使用了
				ARGB_8888  每个像素要4字节~带透明度的颜色, 默认色样
				RGB_565    每个像素要2字节~不带透明度的颜色
             */
            options.inPreferredConfig = Bitmap.Config.ARGB_8888 ; // RGB_565，设置可进一步减小占用内存
            
            ImageSize srcSize = new ImageSize(options.outWidth, options.outHeight);
            ImageSize targetSize = new ImageSize(config.getBitmapWidth(), config.getBitmapHeight());
            
            options.inSampleSize = calculateInSampleSize(srcSize, targetSize);
            
            options.inJustDecodeBounds = false;
            try {
            	 return  BitmapFactory.decodeByteArray(data, offset, length, options);
    		} catch (OutOfMemoryError e) {
    			 e.printStackTrace();
    			 return null;
    		}
    	}
    }
    
    private static int calculateInSampleSize(ImageSize srcSize, ImageSize targetSize) {
        final int srcWidth = srcSize.getWidth();
        final int srcHeight = srcSize.getHeight();
        final int targetWidth = targetSize.getWidth();
        final int targetHeight = targetSize.getHeight();
        
        int inSampleSize = 1;

        if (srcWidth > targetWidth || srcHeight > targetHeight) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round((float) srcHeight / (float) targetHeight);
                
            } else {
                inSampleSize = Math.round((float) srcWidth / (float) targetWidth);
            }

            final float totalPixels = srcWidth * srcHeight;
            final float totalReqPixelsCap = targetWidth * targetHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        
//    	Log.i("calculateInSampleSize", "srcSize = " + srcSize.toString());
//        Log.i("calculateInSampleSize", "targetSize = " + targetSize.toString());
//        Log.i("calculateInSampleSize", "inSampleSize = " + inSampleSize);

       return inSampleSize;
    }
}
