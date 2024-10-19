package com.ixuea.courses.mymusic.component.me

import android.os.Bundle
import com.ixuea.courses.mymusic.component.shortviedo.ShortVideoFragment

import com.ixuea.courses.mymusic.databinding.FragmentMeBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment

class MeFragment :BaseViewModelFragment<FragmentMeBinding>(){
    companion object{
        fun newInstance(): MeFragment {
            val args = Bundle()

            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}