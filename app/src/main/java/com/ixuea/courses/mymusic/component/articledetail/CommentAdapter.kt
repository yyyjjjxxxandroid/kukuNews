package com.ixuea.courses.mymusic.component.articledetail

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.comment.Comment
import com.ixuea.courses.mymusic.util.ImageUtil
import com.ixuea.superui.date.SuperDateUtil

//评论适配器
class CommentAdapter(val viewModel: ArticleDetailViewModel): BaseQuickAdapter<Comment, QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, data: Comment?) {
        data?.let {data ->
            val iconView = holder.getView<ImageView>(R.id.icon)
            ImageUtil.showAvatar(iconView, data.user!!.icon)
            holder.setText(R.id.nickname,data.user!!.nickname)
            holder.setText(R.id.date, SuperDateUtil.commonFormat(data.createdAt))
            holder.setText(R.id.like_count,java.lang.String.valueOf(data.likesCount))

            //显示点赞状态
            if (data.isLiked()){
                holder.setImageResource(R.id.like,R.drawable.thumb_selected)
                holder.setTextColorRes(R.id.like_count,R.color.primary)
            }else{
                holder.setImageResource(R.id.like,R.drawable.baseline_thumb)
                holder.setTextColorRes(R.id.like_count,R.color.black80)
            }
  var contentView=holder.getView<TextView>(R.id.content)
            holder.setText(R.id.content,data.content!!)
            if (data.commentsCount>0){
                holder.setText(R.id.comments_count,context.getString(R.string.reply_count,data.commentsCount))
            }else{
                holder.setText(R.id.comments_count,R.string.reply)
            }
            //回复按钮的点击
            holder.getView<View>(R.id.comments_count_container).setOnClickListener {
                viewModel.loadReplyComment(data)
            }

        }

    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_comment,parent)
    }
}