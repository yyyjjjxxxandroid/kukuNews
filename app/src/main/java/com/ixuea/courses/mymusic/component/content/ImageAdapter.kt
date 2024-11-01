package com.ixuea.courses.mymusic.component.content

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.util.ImageUtil

//用框架提供的QuickViewHolder就不用自己写了

class ImageAdapter :BaseQuickAdapter<String,QuickViewHolder>(){
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, data: String?) {
        data?.let {
            val iconView=holder.getView<ImageView>(R.id.icon)
            ImageUtil.show(iconView,data)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_image,parent)
    }

}