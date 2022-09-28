package com.example.contacttext

import android.app.Application
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import dagger.hilt.android.HiltAndroidApp
import java.lang.reflect.Method


@HiltAndroidApp
class ContactApplication : Application() {
    companion object{
      lateinit  var instance:ContactApplication
    }
    override fun onCreate() {
        instance=this
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m: Method = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onCreate()
    }
}