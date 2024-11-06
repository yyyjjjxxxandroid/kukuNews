package com.ixuea.courses.mymusic.component.articledetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.QuickAdapterHelper
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.UserDetailActivity.UserDetailActivity
import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.component.content.ContentViewModel
import com.ixuea.courses.mymusic.component.content.ContentViewModelFactory
import com.ixuea.courses.mymusic.databinding.ActivityArticleDetailBinding
import com.ixuea.courses.mymusic.util.Constant
import com.ixuea.courses.mymusic.util.ImageUtil
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/**
 * 文章详情界面
* */
class ArticleDetailActivity : BaseViewModelActivity<ActivityArticleDetailBinding>() {
    private lateinit var articleDetailHeaderAdapter: ArticleDetailHeaderAdapter
    private lateinit var helper: QuickAdapterHelper
    private lateinit var viewModel: ArticleDetailViewModel
private lateinit var layoutManager: LinearLayoutManager
private lateinit var adapter: CommentAdapter
    override fun initViews() {
        super.initViews()
        layoutManager= LinearLayoutManager(this)
        binding.list.layoutManager=layoutManager
    }
    override fun initDatum() {
        super.initDatum()
        val viewModelFactory= ArticleDetailViewModelFactory(intent.getStringExtra(Constant.ID)!!)
        viewModel= ViewModelProvider(this,viewModelFactory).get(ArticleDetailViewModel::class.java)
        //用来监听页面出错
        initViewModel(viewModel)
        //适配器
         adapter=CommentAdapter(viewModel)
        //添加帮助类
         helper=QuickAdapterHelper.Builder(adapter)
            .build()
        //头部
         articleDetailHeaderAdapter= ArticleDetailHeaderAdapter().apply {
             //点击头像跳转  用_就没有波浪号
             addOnItemChildClickListener(R.id.user_container){adapter,view,position->
                 startActivityExtraId(UserDetailActivity::class.java,viewModel.content.user!!.id!!)
             }


         }
        helper.addBeforeAdapter(articleDetailHeaderAdapter)
        binding.list.adapter=helper.adapter
        //头部的数据
        lifecycleScope.launch {
            //数据处理
            viewModel.data.collect(){
                showData(it)
            }
        }
        //评论的数据
        lifecycleScope.launch {
            viewModel.comments.collect{data->
                if (data.page==1){
                    //第一页
                    adapter.submitList(data.data)
                }else{
                    //上拉加载更多
                    adapter.addAll(data.data!!)
                }

            }
        }
        viewModel.loadData()
    }

    private fun showData(data: Content) {
       articleDetailHeaderAdapter.setItem(data,null)
        //用户信息
        ImageUtil.showAvatar(binding.icon, data.user!!.icon)
        binding.nickname.text = data.user!!.nickname

        //点赞
        if (data.isLike()){
            binding.like.setImageResource(R.drawable.thumb_selected)
        }else{
            binding.like.setImageResource(R.drawable.baseline_thumb)
        }
    }

    override fun initListeners() {
        super.initListeners()
        //返回按钮
        binding.back.setOnClickListener {
            finish()
        }
        //监听列表滚动
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //获取第一个可见item的position
                val firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition()
                //获取recyclerView的滚动距离
                val scrollY=recyclerView.computeVerticalScrollOffset()
                //大体一个值
                binding.userContainer.visibility=if (scrollY>=350) View.VISIBLE else View.INVISIBLE
            }
        })
    }
}