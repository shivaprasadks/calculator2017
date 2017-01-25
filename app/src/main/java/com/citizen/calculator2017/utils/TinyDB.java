package com.citizen.calculator2017.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.citizen.calculator2017.BuildConfig;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class TinyDB {
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath;
    private SharedPreferences preferences;

    public TinyDB(Context appContext) {
        this.lastImagePath = BuildConfig.FLAVOR;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public Bitmap getImage(String path) {
        Bitmap bitmapFromPath = null;
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapFromPath;
    }

    public String getSavedImagePath() {
        return this.lastImagePath;
    }

    public String putImage(String theFolder, String theImageName, Bitmap theBitmap) {
        if (theFolder == null || theImageName == null || theBitmap == null) {
            return null;
        }
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = setupFullPath(theImageName);
        if (mFullPath.equals(BuildConfig.FLAVOR)) {
            return mFullPath;
        }
        this.lastImagePath = mFullPath;
        saveBitmap(mFullPath, theBitmap);
        return mFullPath;
    }

    public boolean putImageWithFullPath(String fullPath, Bitmap theBitmap) {
        return (fullPath == null || theBitmap == null || !saveBitmap(fullPath, theBitmap)) ? false : true;
    }

    private String setupFullPath(String imageName) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), this.DEFAULT_APP_IMAGEDATA_DIRECTORY);
        if (!isExternalStorageReadable() || !isExternalStorageWritable() || mFolder.exists() || mFolder.mkdirs()) {
            return mFolder.getPath() + '/' + imageName;
        }
        Log.e("ERROR", "Failed to setup folder");
        return BuildConfig.FLAVOR;
    }

    private boolean saveBitmap(String fullPath, Bitmap bitmap) {
        boolean bitmapCompressed = false;
        Exception e;
        Throwable th;
        if (fullPath == null || bitmap == null) {
            return false;
        }
        boolean fileCreated = false;
        boolean streamClosed = false;
        File imageFile = new File(fullPath);
        if (imageFile.exists() && !imageFile.delete()) {
            return false;
        }
        try {
            fileCreated = imageFile.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        FileOutputStream out = null;
        try {
            FileOutputStream out2 = new FileOutputStream(imageFile);
            try {
                bitmapCompressed = bitmap.compress(CompressFormat.PNG, 100, out2);
                if (out2 != null) {
                    try {
                        out2.flush();
                        out2.close();
                        streamClosed = true;
                        out = out2;
                    } catch (IOException e22) {
                        e22.printStackTrace();
                        streamClosed = false;
                        out = out2;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                out = out2;
                try {
                    e.printStackTrace();
                    bitmapCompressed = false;
                    if (out != null) {
                        try {
                            out.flush();
                            out.close();
                            streamClosed = true;
                        } catch (IOException e222) {
                            e222.printStackTrace();
                            streamClosed = false;
                        }
                    }
                    return !fileCreated ? false : false;
                } catch (Throwable th2) {
                    th = th2;
                    if (out != null) {
                        try {
                            out.flush();
                            out.close();
                        } catch (IOException e2222) {
                            e2222.printStackTrace();
                        }
                    }
                    try {
                        throw th;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                if (out != null) {
                    out.flush();
                    out.close();
                }
                try {
                    throw th;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            bitmapCompressed = false;
            if (out != null) {
                try {
                    out.close();
                    out.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
               
                streamClosed = true;
            }
            if (!fileCreated) {
            }
        }
        if (!fileCreated && bitmapCompressed && streamClosed) {
            return true;
        }
        return false;
    }

    public int getInt(String key) {
        return this.preferences.getInt(key, 0);
    }

    public ArrayList<Integer> getListInt(String key) {
        ArrayList<String> arrayToList = new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(key, BuildConfig.FLAVOR), "\u201a\u2017\u201a")));
        ArrayList<Integer> newList = new ArrayList();
        Iterator it = arrayToList.iterator();
        while (it.hasNext()) {
            newList.add(Integer.valueOf(Integer.parseInt((String) it.next())));
        }
        return newList;
    }

    public long getLong(String key, long defaultValue) {
        return this.preferences.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return this.preferences.getFloat(key, 0.0f);
    }

    public double getDouble(String key, double defaultValue) {
        try {
            defaultValue = Double.parseDouble(getString(key));
        } catch (NumberFormatException e) {
        }
        return defaultValue;
    }

    public ArrayList<Double> getListDouble(String key) {
        ArrayList<String> arrayToList = new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(key, BuildConfig.FLAVOR), "\u201a\u2017\u201a")));
        ArrayList<Double> newList = new ArrayList();
        Iterator it = arrayToList.iterator();
        while (it.hasNext()) {
            newList.add(Double.valueOf(Double.parseDouble((String) it.next())));
        }
        return newList;
    }

    public String getString(String key) {
        return this.preferences.getString(key, BuildConfig.FLAVOR);
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(key, BuildConfig.FLAVOR), "\u201a\u2017\u201a")));
    }

    public boolean getBoolean(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> myList = getListString(key);
        ArrayList<Boolean> newList = new ArrayList();
        Iterator it = myList.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).equals("true")) {
                newList.add(Boolean.valueOf(true));
            } else {
                newList.add(Boolean.valueOf(false));
            }
        }
        return newList;
    }

    public ArrayList<Object> getListObject(String key, Class<?> mClass) {
        Gson gson = new Gson();
        ArrayList<String> objStrings = getListString(key);
        ArrayList<Object> objects = new ArrayList();
        Iterator it = objStrings.iterator();
        while (it.hasNext()) {
            objects.add(gson.fromJson((String) it.next(), (Class) mClass));
        }
        return objects;
    }

    public Object getObject(String key, Class<?> classOfT) {
        return new Gson().fromJson(getString(key), (Class) classOfT);
    }

    public void putInt(String key, int value) {
        checkForNullKey(key);
        this.preferences.edit().putInt(key, value).apply();
    }

    public void putListInt(String key, ArrayList<Integer> intList) {
        checkForNullKey(key);
        this.preferences.edit().putString(key, TextUtils.join("\u201a\u2017\u201a", (Integer[]) intList.toArray(new Integer[intList.size()]))).apply();
    }

    public void putLong(String key, long value) {
        checkForNullKey(key);
        this.preferences.edit().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        checkForNullKey(key);
        this.preferences.edit().putFloat(key, value).apply();
    }

    public void putDouble(String key, double value) {
        checkForNullKey(key);
        putString(key, String.valueOf(value));
    }

    public void putListDouble(String key, ArrayList<Double> doubleList) {
        checkForNullKey(key);
        this.preferences.edit().putString(key, TextUtils.join("\u201a\u2017\u201a", (Double[]) doubleList.toArray(new Double[doubleList.size()]))).apply();
    }

    public void putString(String key, String value) {
        checkForNullKey(key);
        checkForNullValue(value);
        this.preferences.edit().putString(key, value).apply();
    }

    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        this.preferences.edit().putString(key, TextUtils.join("\u201a\u2017\u201a", (String[]) stringList.toArray(new String[stringList.size()]))).apply();
    }

    public void putBoolean(String key, boolean value) {
        checkForNullKey(key);
        this.preferences.edit().putBoolean(key, value).apply();
    }

    public void putListBoolean(String key, ArrayList<Boolean> boolList) {
        checkForNullKey(key);
        ArrayList<String> newList = new ArrayList();
        Iterator it = boolList.iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                newList.add("true");
            } else {
                newList.add("false");
            }
        }
        putListString(key, newList);
    }

    public void putObject(String key, Object obj) {
        checkForNullKey(key);
        putString(key, new Gson().toJson(obj));
    }

    public void putListObject(String key, ArrayList<Object> objArray) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList();
        Iterator it = objArray.iterator();
        while (it.hasNext()) {
            objStrings.add(gson.toJson(it.next()));
        }
        putListString(key, objStrings);
    }

    public void remove(String key) {
        this.preferences.edit().remove(key).apply();
    }

    public boolean deleteImage(String path) {
        return new File(path).delete();
    }

    public void clear() {
        this.preferences.edit().clear().apply();
    }

    public Map<String, ?> getAll() {
        return this.preferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static boolean isExternalStorageWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return "mounted".equals(state) || "mounted_ro".equals(state);
    }

    public void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    public void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }
}
