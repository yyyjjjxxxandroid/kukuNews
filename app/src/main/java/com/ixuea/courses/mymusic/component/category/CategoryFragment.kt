package com.ixuea.courses.mymusic.component.category

import android.os.Bundle
import com.ixuea.courses.mymusic.component.shortviedo.ShortVideoFragment
import com.ixuea.courses.mymusic.databinding.FragmentCategoryBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment

class CategoryFragment :BaseViewModelFragment<FragmentCategoryBinding>(){
    companion object{
        fun newInstance(): CategoryFragment {
            val args = Bundle()

            val fragment = CategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}