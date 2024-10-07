package com.ixuea.superui

import android.os.Process

object SuperProcessUtil {
    fun killApp(){
        Process.killProcess(Process.myPid())
    }
}