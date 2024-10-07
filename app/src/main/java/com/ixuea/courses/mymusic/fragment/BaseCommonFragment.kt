package com.ixuea.courses.mymusic.fragment

import android.view.View
import androidx.annotation.IdRes

abstract class BaseCommonFragment :BaseDialogFragment(){
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }

}