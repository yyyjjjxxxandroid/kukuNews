package com.ixuea.courses.mymusic.component.content

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.databinding.ItemContentBinding
import com.ixuea.courses.mymusic.util.ImageUtil
import com.ixuea.superui.date.SuperDateUtil
import com.ixuea.superui.decoration.GridDividerItemDecoration
import com.ixuea.superui.util.DensityUtil
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils


/*内容适配器*/
class ContentAdapter(val viewModel: ContentViewModel) : BaseQuickAdapter<Content, ContentAdapter.ViewHolder>() {

    /*
    * 绑定数据
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int, data: Content?) {
    holder.bindData(data!!)

    }

    /*
    * 创建ViewHolder对象
    * 使用ItemContentBinding.inflate方法从布局文件中创建视图绑定对象。
      LayoutInflater.from(context)获取一个布局填充器实例，这个填充器可以根据布局文件创建实际的视图对象。
      parent作为新创建视图的父视图组传入。
      false表示不立即将新创建的视图添加到父视图组中。
    * */
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemContentBinding.inflate(LayoutInflater.from(context),parent,false))
    }
//定义viewHolder，这个ViewHolder类继承自RecyclerView.ViewHolder类。构造函数中，将binding.root传递给父类的构造函数。内部类想访问要加上inner
  inner class ViewHolder(val binding: ItemContentBinding) : RecyclerView.ViewHolder (binding.root){
        //显示数据
        fun bindData(data: Content) {
            if (StringUtils.isNotBlank(data.title)){
                binding.content.text=data.title
            }else{
                binding.content.text=data.content
            }

            binding.nickname.text=data.user!!.nickname

            binding.commentsCount.text=binding.commentsCount.context.getString(R.string.comments_count,data.commentsCount)

            binding.date.text= SuperDateUtil.commonFormat(data.createdAt)

            binding.videoContainer.visibility= View.GONE
            //bug 视频头上弹出图片 原因：列表会复用
            binding.list.visibility= View.GONE

            if (StringUtils.isNotBlank(data.uri)){
                //视屏和视频时长
                binding.videoContainer.visibility= View.VISIBLE

                ImageUtil.showImage(binding.icon,data.icons?.get(0))
                binding.duration.text= SuperDateUtil.s2ms(data.duration) }
            //这么写有性能影响，每次滚动的时候，都会去这么设置，此处可以优化后续来处理 可以把适配器缓存起来
            else if (CollectionUtils.isNotEmpty(data.icons)){
                //有图片

                binding.list.visibility= View.VISIBLE
                //动态计算列数
                var spanCount=2
                if (data.icons!!.size>=3){
                    //大于等于3张显示3列
                    spanCount=3
                }
                val layoutManager=GridLayoutManager(binding.list.context,spanCount)
                binding.list.layoutManager=layoutManager
                //分割线
                if (binding.list.itemDecorationCount>0){
                    binding.list.removeItemDecorationAt(0)
                }
                val itemDecoration=GridDividerItemDecoration(binding.list.context,
                    DensityUtil.dip2px(binding.list.context,5f).toInt())
                binding.list.addItemDecoration(itemDecoration)
                var adapter=ImageAdapter()
                adapter.setOnItemClickListener{adapter,view,position->
                    viewModel.previewMedias(binding.list,data.icons!!,position)
                }
                binding.list.adapter=adapter
                adapter.submitList(data.icons!!)

            }
        }

    }
}