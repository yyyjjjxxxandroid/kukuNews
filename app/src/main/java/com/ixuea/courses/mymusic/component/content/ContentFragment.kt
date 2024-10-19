package com.ixuea.courses.mymusic.component.content

import android.os.Bundle
import com.ixuea.courses.mymusic.databinding.FragmentContentBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment
import com.ixuea.courses.mymusic.util.Constant

/*内容界面*/
class ContentFragment: BaseViewModelFragment<FragmentContentBinding>() {
    companion object{
        fun newInstance(categoryId:String?=null):ContentFragment {
            val args = Bundle()
            categoryId?.let {
                args.putString(Constant.ID,it)
            }
            val fragment = ContentFragment()
            fragment.arguments = args
            return fragment
        }
    }

}