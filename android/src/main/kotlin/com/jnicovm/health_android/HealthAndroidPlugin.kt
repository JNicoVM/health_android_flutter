package com.jnicovm.health_android

import androidx.annotation.NonNull

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import java.lang.Error

/** HealthAndroidPlugin */
class HealthAndroidPlugin: FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {
  private val CHANNEL = "health_android"
  private val GOOGLE_FIT_CODE = 1000

  private lateinit var channel : MethodChannel
  private lateinit var activity: Activity
  private lateinit var context: Context

  private var fitnessOptions: FitnessOptions = FitnessOptions.builder()
          .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
          .build()


  private fun hasPermissions(): Boolean{
    return  GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(activity), fitnessOptions)
  }

  private fun getSteps():String{
    return "I get the stesps"
  }

  private fun requestPermission(): String {
    val account = GoogleSignIn.getAccountForExtension(activity, fitnessOptions)
    return try {
      GoogleSignIn.requestPermissions(activity, GOOGLE_FIT_CODE, account, fitnessOptions)
      "requestLaunched"
    }catch (e : Error){
      "";
    }

  }

  override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(binding.flutterEngine.dartExecutor, CHANNEL)
    channel.setMethodCallHandler(this)
    this.context = binding.applicationContext
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {}

  override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
    if (call.method == "hasPermission") {
      val hasPermmision = hasPermissions()
      if (hasPermmision) {
        result.success(hasPermmision)
      } else {
        result.error("UNAVAILABLE", " get permission is not avaliable.", null)
      }
    } else if (call.method == "getSteps") {
      val getSteps = getSteps()
      if (getSteps != "") {
        result.success(getSteps)
      } else {
        result.error("UNAVAILABLE", " get steps is not avaliable.", null)
      }
    } else if (call.method == "requestPermission") {
      val requestPermission = requestPermission()
      if (requestPermission == "requestLaunched") {
        result.success(requestPermission)
      } else {
        result.error("UNAVAILABLE", " launche request is not avaliable", null)
      }
    }else{
      result.notImplemented()
    }
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {}

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    onAttachedToActivity(binding)
  }

  override fun onDetachedFromActivity() {
    channel.setMethodCallHandler(null)
  }
}