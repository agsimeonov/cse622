package edu.buffalo.memlib.swap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.os.Environment;

public final class SwapUtil {
	public static final String FILE_PREPEND = "edu.buffalo.swap.";
	
	public static void deleteFile(long id, boolean internal) {
		Context context = SwapActivity.getSwapContext();
		if (internal) {
			context.deleteFile(FILE_PREPEND + id);
		}
		else {
			File file = new File(context.getExternalFilesDir(null), FILE_PREPEND + id);
			file.delete();
		}
	}

	public static FileOutputStream getFileOutputStream(long id, boolean internal) {
		Context context = SwapActivity.getSwapContext();
		if (internal) {
			try {
				return context.openFileOutput(FILE_PREPEND + id, Context.MODE_PRIVATE);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			if (isExternalStorageWritable()) {
				File file = new File(context.getExternalFilesDir(null), FILE_PREPEND + id);
				try {
					return new FileOutputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
			else {
				return null;
			}
		}
	}

	public static FileInputStream getFileInputStream(long id, boolean internal) {
		Context context = SwapActivity.getSwapContext();
		if (internal) {
			try {
				return context.openFileInput(FILE_PREPEND + id);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			if (isExternalStorageWritable()) {
				File file = new File(context.getExternalFilesDir(null), FILE_PREPEND + id);
				try {
					return new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
			else {
				return null;
			}
		}
	}

	public static ObjectOutputStream getObjectOutputStream(long id, boolean internal) {
		FileOutputStream fos = getFileOutputStream(id, internal);
		if (fos != null) {
			try {
				return new ObjectOutputStream(fos);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			return null;
		}
	}

	public static ObjectInputStream getObjectInputStream(long id, boolean internal) {
		FileInputStream fis = getFileInputStream(id, internal);
		if (fis != null) {
			try {
				return new ObjectInputStream(fis);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}


	public static boolean isExternalStorageWritable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
			return true;
		else
			return false;
	}
}
