package com.ixuea.courses.mymusic.component.articledetail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.component.content.ContentViewModel
import com.ixuea.courses.mymusic.component.content.ContentViewModelFactory
import com.ixuea.courses.mymusic.databinding.ActivityArticleDetailBinding
import com.ixuea.courses.mymusic.util.Constant
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/**
 * 文章详情界面
* */
class ArticleDetailActivity : BaseViewModelActivity<ActivityArticleDetailBinding>() {
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
         adapter=CommentAdapter(viewModel)
        binding.list.adapter=adapter
        lifecycleScope.launch {
            //数据处理
            viewModel.data.collect(){
                showData(it)
            }
        }
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

    private fun showData(it: Content) {
        Log.d("yyyjkjj", "showData: ${it.title}")
    }
}