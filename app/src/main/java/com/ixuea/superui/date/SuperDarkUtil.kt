package com.ixuea.superui.date

import android.content.Context
import android.content.res.Configuration

object SuperDarkUtil {
    fun isDark(context: Context): Boolean {
        return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}