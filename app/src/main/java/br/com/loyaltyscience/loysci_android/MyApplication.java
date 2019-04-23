package br.com.loyaltyscience.loysci_android;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import br.com.loyaltyscience.loysci_android.util.Prefs;
import io.fabric.sdk.android.Fabric;



/**
 * Created by alejandra on 19/6/17.
 */

public final class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        Prefs.init(this);

    }
}
