package com.offgoing.mao.aliframe.bitmap.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

/**
 * sdcard缓存
 * 
 * @author mark
 *
 */
public class SdCache {
	
	private static final String TAG = SdCache.class.getSimpleName();
	
	private static final ArrayList<String> mSdCache = new ArrayList<String>();
	
	private String mSdCacheDir; // sdcard缓存目录
	private int mSdCacheCount; // sdcard缓存的图片数
	
	public SdCache(String dirPath, int sdCacheCount) throws IOException {
		mSdCacheDir = dirPath;
		mSdCacheCount = sdCacheCount;
		
//		Log.i("SdCache", "mSdCacheDir = " + mSdCacheDir + ", mSdCacheCount = " + mSdCacheCount);
    	
    	File dir = new File(mSdCacheDir);
		if(!dir.exists()){
			if(!dir.mkdirs()){
				throw new IOException("unable to make dirs");
			}
		}
	}
	
	public void put(String key, byte[] data) {
		File file = write(key, data); // 缓存或覆盖旧的缓存
		
		if (null != file) {
			int size = mSdCache.size();
			
			if (mSdCacheCount > size) { // 未超过最大缓存图片数
				if (null != file) {
					int index = mSdCache.indexOf(key); 
					if (-1 != index) { // 存在缓存，则替换旧的缓存
						mSdCache.add(index, key);
						
					} else {
						mSdCache.add(key);
					}
				}
				
			} else { // 超过最大缓存图片数，则删除存在最久的图片缓存
				String oldKey = mSdCache.get(0);
				
				if (remove(oldKey)) {
					mSdCache.add(key);
				}
			}
		}
	}
	
	public Bitmap get(String key, BitmapDisplayConfig config) {
		return read(key, config);
	}
	
	public boolean remove(String key) {
		if (delete(key)) {
			mSdCache.remove(key);
			
			return true;
		}
		return false;
	}
	
	public void evictAll() {
		int size = mSdCache.size();
		
		for (int i = 0; i < size; i++) {
			delete(mSdCache.get(i));
		}
		
		mSdCache.clear();
	}
	
	private String getKey(String urlStr) {
		if (!TextUtils.isEmpty(urlStr)) {
			URL url;  
	        try {  
	            url = new URL(urlStr);  
	        } catch (MalformedURLException e) {  
	            return null;  
	        }  
	          
	        String file = url.getFile();  
	        String[] splitStr = file.split("/");  
	        int len = splitStr.length;  
	        String result = splitStr[len-1];  
	        return result;  
		}
		return null;
	}
	
	private Bitmap read(String url, BitmapDisplayConfig config) {
		if (!TextUtils.isEmpty(url)) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(mSdCacheDir + File.separator + getKey(url));
				
				int length = fis.available();
				byte[] buffer = new byte[length];   
		        fis.read(buffer);   
				
		        return BitmapDecoder.decodeBitmapFromByteArray(buffer, 0, buffer.length, config);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				 if (null != fis) {   
		                try {   
		                	fis.close();   
		                } catch (IOException e1) {   
		                    e1.printStackTrace();   
		                }   
		            } 
			}
		}
		return null;
	}
	
	private File write(String url, byte[] data) {
		if (!TextUtils.isEmpty(url)
				&& null != data && data.length > 0) {
	        BufferedOutputStream bos = null;   
	        File file = null;   
	        try {   
	            file = new File(mSdCacheDir + File.separator + getKey(url));   
	            bos = new BufferedOutputStream(new FileOutputStream(file));   
	            bos.write(data);   
	            
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        } finally {   
	            if (null != bos) {   
	                try {   
	                	bos.close();   
	                } catch (IOException e1) {   
	                    e1.printStackTrace();   
	                }   
	            }   
	        }   
	        return file;  
		}
		return null;
	}
	
	private boolean delete(String url) {
		if (!TextUtils.isEmpty(url)) {
			File file = new File(mSdCacheDir + File.separator + getKey(url));   
			return file.delete();
		}
		return false;
	}
}
