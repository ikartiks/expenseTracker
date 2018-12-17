package ikartiks.expensetracker

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class ActivityBase : AppCompatActivity(){


    var screenWidth: Int = 0
    var screenHeight:Int = 0
    var statusBarHeight:Int = 0
    var navigationBarHeight:Int = 0

//    public void showLoader(){
//        GifImageView gif=(GifImageView) findViewById(R.id.Loading);
//        gif.setVisibility(View.VISIBLE);
//    }
//    public void hideLoader(){
//        GifImageView gif=(GifImageView) findViewById(R.id.Loading);
//        gif.setVisibility(View.GONE);
//    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)


        val resources = this.resources
        var resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        } else {
            statusBarHeight = 0
        }
        resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId)
        } else {
            navigationBarHeight = 0
        }

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y

    }

    fun isConnected(): Boolean {

        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }

    fun hideSoftInput(binder: IBinder) {
        //myEditText.getWindowToken()
        val mgrs = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgrs.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun showSoftInput(view: View) {
        val mgrs = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgrs.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun showCustomMessage(message: String) {

        val adb = AlertDialog.Builder(this)
        adb.setTitle(this.resources.getString(R.string.app_name))
        adb.setMessage(message)
        //adb.setPositiveButton("OK",({ dialog, which -> dialog.dismiss() }))
        adb.setPositiveButton("OK",{ dialog, which -> dialog.dismiss() })
        //adb.setPositiveButton("OK"){ dialog, which -> dialog.dismiss() }

//        adb.setPositiveButton("OK", object : DialogInterface.OnClickListener{
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//               dialog?.dismiss()
//            }
//
//        });


//        adb.setPositiveButton("OK") { dialog, which -> {
//            dialog.dismiss()
//            print("asd")
//            }
//        }

//        adb.setPositiveButton("OK") { dialog, which ->
//            run {
//                dialog.dismiss()
//                print("asd")
//            }
//        }

        adb.create().show()
    }

    fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun isTablet(): Boolean {

        val xlarge = this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
        val large = this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge || large
    }

    fun isPackagePresent(targetPackage: String): Boolean {
        val packages: List<ApplicationInfo>
        val pm: PackageManager = this.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == targetPackage)
                return true
        }
        return false
    }

    fun resolveActivity(intent: Intent): Boolean {

        val pm = this.packageManager
        val info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return info != null
    }

    fun resolveAttribute(attr: Int): Int {
        val typedvalueattr = TypedValue()
        this.theme.resolveAttribute(attr, typedvalueattr, true)
        return typedvalueattr.resourceId
    }

    fun resolveAttributeColor(attr: Int): Int {
        val typedvalueattr = TypedValue()
        this.theme.resolveAttribute(attr, typedvalueattr, true)
        return typedvalueattr.data
    }

    fun putBoolean(key: String, value: Boolean) {

        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String, defaultValue: String): String? {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)

    }

    fun remove(key: String) {
        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.commit()
    }


    fun containsPreference(key: String): Boolean {

        val preferencesName = this.resources.getString(R.string.app_name)
        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.contains(key)
    }

    fun log(message: String) {

        Log.e(ActivityBase::class.qualifiedName+" "+this.localClassName, "" + message)
    }

//    fun putArray(key: String, value: ArrayList<String>) {
//        val preferencesName = this.resources.getString(R.string.app_name)
//        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putInt(key, value.size)
//
//        for (i in value.indices) {
//            editor.putString(key + i, value[i])
//        }
//
//        editor.commit()
//    }
//
//    fun getArray(key: String): ArrayList<String> {
//
//        val preferencesName = this.resources.getString(R.string.app_name)
//        val sharedPreferences = this.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
//        val value = ArrayList<String>()
//        val size = sharedPreferences.getInt(key, 0)
//
//        for (i in 0 until size) {
//            value.add(sharedPreferences.getString(key + i, ""))
//        }
//        return value
//    }
}