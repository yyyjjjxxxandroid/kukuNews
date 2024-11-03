package com.ixuea.superui.extension

import android.widget.Toast
import com.ixuea.courses.mymusic.AppContext

/**
 * Int 扩展toast
 */

/**
 * 短toast
 */
fun Int.shortToast() {
    Toast.makeText(AppContext.instance, this, Toast.LENGTH_SHORT).show()
}

/**
 * 长toast
 */
fun Int.longToast() {
    Toast.makeText(AppContext.instance, this, Toast.LENGTH_LONG).show()
}