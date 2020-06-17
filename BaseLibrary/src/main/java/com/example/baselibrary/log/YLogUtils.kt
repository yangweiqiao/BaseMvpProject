package com.example.baselibrary.log

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.example.baselibrary.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.addLogAdapter


object YLogUtils {
    fun initLog() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        Logger.t("我的日志")
    }

    fun d(msg: Any) {
        Logger.d(msg)
    }

    fun e(msg: Any) {
        Logger.e(msg.toString())
    }

    fun i(msg: Any) {
        Logger.i(msg.toString())
    }

    fun v(msg: Any) {
        Logger.v(msg.toString())
    }

    fun json(msg: String) {
        Logger.json(msg)
    }

    fun xml(msg: String) {
        Logger.xml(msg)
    }
}