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
        viewModel=ViewModelProvider(this).get(ContentViewModel::class.java)
        //适配器
       adapter= ContentAdapter()
        binding.list.adapter=adapter

        //观察viewmodel里面的flow，如果他的数据变更了，就会触发这个回调
        lifecycleScope.launch {
            viewModel.data.collect(){
                Log.d("contentFragment", "initDatum: ${it.data!![0].title}")
                //将数据设置进去，它只会更新数据发生变化的部分视图，避免了像notifyDataSetChanged那样重新创建和绑定所有视图。
                adapter.submitList(it.data)
            }
        }

//       这个就是传统写法直接请求 lifecycleScope.launch {
//            DefaultNetworkRepository.contents()
//        }
    }

    override fun onResume() {
        super.onResume()
        //arguments本质上是一个Bundle对象，Bundle类用于存储键值对，就像一个简单的存储数据的容器。
        if (isFirstLoad){
            //准备工作做好以后加载数据
            //例如这里请求数据就直接请求，就是mvc
            //mvvm请求数据的逻辑要放到单独一个类中，然后activity和fragment就不再写过多的逻辑，只写与界面相关的逻辑
            viewModel.loadMore()
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