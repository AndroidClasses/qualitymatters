package com.artemzin.qualitymatters.developer_settings;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.nshmura.strictmodenotifier.StrictModeNotifier;

public class StrictModeNotifierProxyImpl implements StrictModeNotifierProxy {

  @NonNull
  private final Application application;

  public StrictModeNotifierProxyImpl(@NonNull final Application application) {
    this.application = application;
  }

  @Override
  public void apply() {
//    AndroidDevMetrics.initWith(application);
    //setup this library
    StrictModeNotifier.install(application);
    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .permitDiskReads()
            .permitDiskWrites()
            .penaltyLog() // Must!
            .build();
    StrictMode.setThreadPolicy(threadPolicy);

    StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog() // Must!
            .build();
    StrictMode.setVmPolicy(vmPolicy);
  }
}
