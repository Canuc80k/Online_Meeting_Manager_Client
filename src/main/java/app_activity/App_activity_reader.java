package app_activity;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
 
public class App_activity_reader extends Thread {
     private static final int MAX_TITLE_LENGTH = 1024;	
     private static final int TIME_THREAD_SLEEP = 200;
 	private static final String APP_ACTIVITY_DATA_SPLIT_SIGNAL = "_   _";
     
     private boolean is_reading_app_activity;
     private String app_activity_log;
     
     public App_activity_reader() {
    	 
     }
     
     /*
      * @do update global variable String app_activity_log
      * 	> app_activity_log = String contain data of app activity in LAST RUN
      * 	> app activity data include
      * 		- app title 
      * 		- app processer
      * 		- app running time
      * 	> update in 2 way:
      * 		(1) when app title change (means change tab / change app)
      * 		(2) last update, when thread.join()
      */
     
     @Override
     public void run() {
    	  app_activity_log = "";
          String lastTitle = "", lastProcess = "";
          
          double lastChange = System.currentTimeMillis();
          double time = 0, change;
          
          while (is_reading_app_activity) {
               String currentTitle = getActiveWindowTitle();
               String currentProcess = getActiveWindowProcess();
               if (!lastTitle.equals(currentTitle)) {
                    change = System.currentTimeMillis();
                    time = (change - lastChange) / 1000;
                    lastChange = change;
                    
                    app_activity_log += lastTitle + APP_ACTIVITY_DATA_SPLIT_SIGNAL + lastProcess + APP_ACTIVITY_DATA_SPLIT_SIGNAL + String.valueOf(time) + "\n";
                    
                    lastTitle = currentTitle;
                    lastProcess = currentProcess;
               }
               try {Thread.sleep(TIME_THREAD_SLEEP);} catch (Exception ex){}
          }
          
          change = System.currentTimeMillis();
          time = (change - lastChange) / 1000;
          app_activity_log += lastTitle + APP_ACTIVITY_DATA_SPLIT_SIGNAL + lastProcess + APP_ACTIVITY_DATA_SPLIT_SIGNAL + String.valueOf(time) + "\n";
     }
     
     public void set_running_state(boolean running_state) {
    	 is_reading_app_activity = running_state;
     }
     
     public String get_app_activity_log() {
		return app_activity_log;
    	 
     }
     
     private static String getActiveWindowTitle() {
          char[] buffer = new char[MAX_TITLE_LENGTH * 2];
          HWND foregroundWindow = User32DLL.GetForegroundWindow();
          User32DLL.GetWindowTextW(foregroundWindow, buffer, MAX_TITLE_LENGTH);
          String title = Native.toString(buffer);
          return title;
     }
 
     private static String getActiveWindowProcess() {
          char[] buffer = new char[MAX_TITLE_LENGTH * 2];
          PointerByReference pointer = new PointerByReference();
          HWND foregroundWindow = User32DLL.GetForegroundWindow();
          User32DLL.GetWindowThreadProcessId(foregroundWindow, pointer);
          Pointer process = Kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION 
               | Kernel32.PROCESS_VM_READ, false, pointer.getValue());
          Psapi.GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
          String processName = Native.toString(buffer);
          return processName;
     }
 
     static class Psapi {
          static {
               Native.register("psapi");
          }
 
          public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
     }
 
     static class Kernel32 {
          static {
               Native.register("kernel32");
          }
 
          public static int PROCESS_QUERY_INFORMATION = 0x0400;
          public static int PROCESS_VM_READ = 0x0010;
 
          public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
     }
 
     static class User32DLL {
          static {
               Native.register("user32");
          }
 
          public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
          public static native HWND GetForegroundWindow();
          public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
     }
}