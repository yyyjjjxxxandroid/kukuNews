package com.ixuea.courses.mymusic.component.content

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ixuea.courses.mymusic.databinding.FragmentContentBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import com.ixuea.courses.mymusic.util.Constant
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
        viewModel=ViewModelProvider(this,viewModelFactory).get(ContentViewModel::class.java)
        //适配器
        adapter= ContentAdapter(viewModel)
        binding.list.adapter=adapter
        //观察viewmodel里面的flow，如果他的数据变更了，就会触发这个回调
        lifecycleScope.launch {
            viewModel.data.collect(){
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
//       这个就是传统写法直接请求 lifecycleScope.launch { DefaultNetworkRepository.contents()     }
    }
    private fun processRefreshAndLoadMoreStatus(success:Boolean,onMore:Boolean=false){
        //传入false表刷新失败
        binding.refresh.finishRefresh(500,success,false)
        //next=null,表示没有更多数据了
        binding.refresh.finishLoadMore(500,success,onMore)
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