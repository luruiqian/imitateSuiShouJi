package com.cashbook.cashbook.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于捕获全局异常，生成log信息
 * @author xyz
 */
public class CrashHandler implements UncaughtExceptionHandler
{
	public static final String TAG = "CrashHandler";

	// 系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();




	/** 保证只有一个CrashHandler实例 */
	private CrashHandler()
	{
		
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance()
	{
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context)
	{
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		if (!handleException(ex) && mDefaultHandler != null)
		{
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		}
		else
		{
			new Thread() {

				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						Log.e(TAG, "error : ", e);
					}
//					android.os.Process.killProcess(android.os.Process.myPid());



					int currentVersion = android.os.Build.VERSION.SDK_INT;
					if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
						Intent startMain = new Intent(Intent.ACTION_MAIN);
						startMain.addCategory(Intent.CATEGORY_HOME);
						startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startActivity(startMain);
						System.exit(0);
					} else {// android2.1
						ActivityManager am = (ActivityManager)mContext. getSystemService(Context.ACTIVITY_SERVICE);
						am.restartPackage(mContext.getPackageName());
					}




				}

			}.start();
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex)
	{
		if (ex == null)
		{
			return false;
		}
		new Thread()
		{
			@Override
			public void run()
			{
				Looper.prepare();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		saveCrashInfo2File(ex);
		ex.printStackTrace();
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx)
	{
		try
		{
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null)
			{
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields)
		{
			try
			{
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex)
	{

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null)
		{
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		return saveInfoToFile(sb.toString(), false);
	}




	/**
	 * @param data 需要保存的数据
	 * @return 文件路径
	 */
	public static String saveInfoToFile(String data, boolean isAddContent)
	{
		String fileName = null;
		try
		{
			// 格式化文件名
			String time = getDateStr("yyyy-MM-dd_HH-mm-ss");
			fileName = time + ".log";
			String pathName = "haohai";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED))
			{
				String mPath = pathName;
				File dir = new File(mPath);
				File file = new File(dir, fileName);
				if (!dir.exists())
				{
					dir.mkdirs();
				}
				if (!file.exists())
				{
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file, isAddContent);
				if(isAddContent) fos.write("\n".getBytes());
				fos.write(data.getBytes());
				fos.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fileName;
	}



	/**
	 * 按照指定的文本格式返回当前时间
	 *
	 * @param format
	 *            默认格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 *
	 */
	public static String getDateStr(String format) {
		if (format == null || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(format).format(new Date());
	}

}
