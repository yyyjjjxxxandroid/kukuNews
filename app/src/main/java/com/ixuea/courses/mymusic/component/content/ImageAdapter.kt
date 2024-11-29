package com.ixuea.courses.mymusic.component.content

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.util.ImageUtil
import com.luck.picture.lib.entity.LocalMedia

//用框架提供的QuickViewHolder就不用自己写了

class ImageAdapter :BaseQuickAdapter<Any,QuickViewHolder>(){
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, data: Any?) {
        data?.let {
            val iconView = holder.getView<ImageView>(R.id.icon)
            when (data) {
                is String -> {

                    ImageUtil.show(iconView, data)
                }

                is LocalMedia -> {
                    //图片框架选择的图片
                    ImageUtil.showLocalImage(iconView, data.availablePath)

                    //显示删除按钮
                    holder.setGone(R.id.close, false)
                }

                else -> {
                    iconView.setImageResource(data as Int)
                }
            }
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