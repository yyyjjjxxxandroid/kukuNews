package com.ixuea.courses.mymusic.component.shortviedo

import android.os.Bundle
import com.ixuea.courses.mymusic.databinding.FragmentShortVideoBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment

class ShortVideoFragment :BaseViewModelFragment<FragmentShortVideoBinding>(){
    companion object{
        fun newInstance(): ShortVideoFragment {
            val args = Bundle()

            val fragment = ShortVideoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}