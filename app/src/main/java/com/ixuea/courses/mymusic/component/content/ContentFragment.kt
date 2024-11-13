package com.ixuea.courses.mymusic.component.content

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Lists
import com.ixuea.courses.mymusic.component.articledetail.ArticleDetailActivity
import com.ixuea.courses.mymusic.databinding.FragmentContentBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment
import com.ixuea.courses.mymusic.util.Constant
import com.ixuea.courses.mymusic.util.ImageUtil
import com.wanglu.photoviewerlibrary.PhotoViewer
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/*内容界面*/
class ContentFragment: BaseViewModelFragment<FragmentContentBinding>() {
    private lateinit var viewModel: ContentViewModel
    private lateinit var adapter: ContentAdapter
    private var isFirstLoad:Boolean=true
    override fun initViews() {
        super.initViews()
        binding.list.apply {
            layoutManager= LinearLayoutManager(hostActivity)
            //代码中分割线
            val decoration=DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }
    override fun initDatum() {
        super.initDatum()
        //这里传的是tab中fragment的id
        val viewModelFactory=ContentViewModelFactory(requireArguments().getString(Constant.ID))
        viewModel= ViewModelProvider(this,viewModelFactory)[ContentViewModel::class.java]
        //用来监听页面出错
        initViewModel(viewModel)
        //适配器
        adapter= ContentAdapter(viewModel)
        binding.list.adapter=adapter
        //观察viewmodel里面的flow，如果他的数据变更了，就会触发这个回调
        lifecycleScope.launch {
            viewModel.data.collect(){ it ->
                //数据处理
                  if (StringUtils.isBlank(viewModel.lastId)){
                      //下拉刷新 没有传lastId所以为空的会走到这里 “替换”
                      adapter.submitList(it.data)
                  }else{
                      it.data?.let {
                          //上拉加载更多 不应该替换了 应该添加
                          adapter.addAll(it)
                      }
                  }
                processRefreshAndLoadMoreStatus(true,it.data?.isEmpty()?:true)
      //将数据设置进去，它只会更新数据发生变化的部分视图，避免了像notifyDataSetChanged那样重新创建和绑定所有视图。adapter.submitList(it.data)
            }
        }
        lifecycleScope.launch {
            //用于确保在Lifecycle对象处于特定状态（这里是STARTED状态）时，协程块内的代码能够被重复执行。其实加了没意义就是试试
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.toArticleDetail.collect{ id->
                    val intent= Intent(requireContext(), ArticleDetailActivity::class.java)
                    intent.putExtra(Constant.ID,id)
                    startActivity(intent)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.previewMedia.collect {
                previewMedias(it)
            }
        }
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.toArticleDetail.collect{
//                    Log.d("ContentFragment","跳转到文章详情界面")
//                }
//            }
//        }
//       这个就是传统写法直接请求 lifecycleScope.launch { DefaultNetworkRepository.contents()     }
    }
    private fun previewMedias(data: ContentViewModel.PreviewMediaPageData) {
        //将List转为ArrayList
        //因为图片框架需要的是ArrayList
        val medias = Lists.newArrayList<String>(data.medias)

        PhotoViewer.setData(medias)
            //设置当前位置
            .setCurrentPage(data.position)
            //他需要容器的目的是显示缩放动画
            .setImgContainer(data.view)
            //设置图片加载回调
            .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                override fun show(iv: ImageView, url: String) {
                    ImageUtil.show(
                        iv,
                        url
                    )
                }
            }) //启动界面
            .start(this)
    }

    private fun processRefreshAndLoadMoreStatus(success:Boolean,onMore:Boolean=false){
        //传入false表刷新失败
        binding.refresh.finishRefresh(500,success,false)
        //next=null,表示没有更多数据了
        binding.refresh.finishLoadMore(500,success,onMore)
    }

    override fun onError() {
        //只要这个界面出错了就要终止刷新
        //如果想知道具体说明出错就重写onTip onResponse等等
        super.onError()
        processRefreshAndLoadMoreStatus(false)
    }

    override fun initListeners() {
        super.initListeners()
        //下拉刷新监听器
        binding.refresh.setOnRefreshListener {
            viewModel.loadMore()
        }
        //上拉加载更多 我们这个应用其实是androidx的依赖包 这个刷新依赖包用的是旧版的support，所以要去gradle.properties配置
//android.enableJetifier=true    如果主要是android x 但是也用到了一些第三方库一些依赖，一些类名会在两个包里面 这个属性可以吧第三方旧版的依赖换成androidx里面的依赖
        binding.refresh.setOnLoadMoreListener {
            //lastOrNull()可以获取到最后一个对象
            viewModel.loadMore(adapter.items.lastOrNull()?.id)
        }
    }

    override fun onResume() {
        super.onResume()
        //arguments本质上是一个Bundle对象，Bundle类用于存储键值对，就像一个简单的存储数据的容器。
        if (isFirstLoad){
            //准备工作做好以后加载数据
            //例如这里请求数据就直接请求，就是mvc
            //mvvm请求数据的逻辑要放到单独一个类中，然后activity和fragment就不再写过多的逻辑，只写与界面相关的逻辑
//            viewModel.loadMore()
            //进入页面自动刷新
            binding.refresh.autoRefresh()
            isFirstLoad=false
        }

    }
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