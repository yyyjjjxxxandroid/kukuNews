package com.ixuea.courses.mymusic.component.publish

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.drake.channel.sendEvent
import com.google.common.collect.Lists

import com.ixuea.chat.config.glide.GlideEngine
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.component.content.ImageAdapter
import com.ixuea.courses.mymusic.databinding.ActivityPublishBinding
import com.ixuea.courses.mymusic.util.ImageCompressor
import com.ixuea.superui.decoration.GridDividerItemDecoration
import com.ixuea.superui.util.DensityUtil
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.launch

class PublishActivity : BaseTitleActivity<ActivityPublishBinding>() {
    private lateinit var viewModel: PublishViewModel

    private val adapter by lazy {
        ImageAdapter()
    }
    override fun initViews() {
        super.initViews()
        //设置布局管理器
        val layoutManager= GridLayoutManager(hostActivity,4)
        binding.list.layoutManager=layoutManager
        val itemDecoration=GridDividerItemDecoration(hostActivity,DensityUtil.dip2px(hostActivity,5f).toInt())
        binding.list.addItemDecoration(itemDecoration)
    }
    override fun initDatum() {
        super.initDatum()
         viewModel=ViewModelProvider(this)[PublishViewModel::class.java]
        initViewModel(viewModel)

        binding.list.adapter=adapter

         lifecycleScope.launch {
             viewModel.success.collect{
                 sendEvent(ContentChangedEvent())
                 finish()
             }
         }
        lifecycleScope.launch {
            viewModel.data.collect{

                adapter.submitList(it)
            }
        }

        viewModel.loadData()
    }
    override fun initListeners() {
        super.initListeners()
        binding.content.doAfterTextChanged {
            val result:String=getString(R.string.feed_count,it.toString().length)
            binding.count.text=result
        }
        //右上角发布按钮，在toolbar，so we can give the toolbar a menu

        adapter.setOnItemClickListener{adapter,view,position->
            //判断只有int类型的图片 int类型是添加图片 其它都是预览图片（用图片框架）
            if (adapter.getItem(position) is Int){
                selectImage()
            }

        }
       adapter.addOnItemChildClickListener(R.id.close){adapter,view,position->
           adapter.removeAt(position)
       }
    }
    /**
     * 选择图片
     */
    private fun selectImage() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())//打开加好不是有预览界面吗，这个界面他没有写死 可以让你选择别的图片加载框架
            .setMaxSelectNum(9) // 最大图片选择数量 int
            .setMinSelectNum(1) // 最小选择数量 int
            .setImageSpanCount(3) // 每行显示个数 int
            .setSelectionMode(SelectModeConfig.MULTIPLE) // 多选 or 单选 MULTIPLE or SINGLE
            .isPreviewImage(true) // 是否可预览图片 true or false
            .isDisplayCamera(true) // 是否显示拍照按钮 true or false
            .setCameraImageFormat(PictureMimeType.JPEG) // 拍照保存图片格式后缀,默认jpeg
            //自定义压缩
            .setCompressEngine(object : CompressFileEngine {
                override fun onStartCompress(
                    context: Context,
                    source: java.util.ArrayList<Uri>,
                    call: OnKeyValueResultCallbackListener
                ) {
                    ImageCompressor.compressImagesAsync(context,source,object :ImageCompressor.CompressionCallback{
                        override fun onCompressionComplete(
                            originalFilePath: String,
                            compressedFilePath: String
                        ) {
//                            Log.d("TAG", "onStartCompress: "+originalFilePath+","+compressedFilePath);
//
//                            // 将压缩后的文件路径通过回调返回
                            call.onCallback(originalFilePath , compressedFilePath);
                        }

                        override fun onCompressionError(e: Exception) {
                        }

                    })
                }

            })
//            .setCompressEngine(new CompressFileEngine() {
//                @Override
//                public void onStartCompress(Context context, ArrayList<Uri> arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
//                    compressImagesAsync(context, arrayList, new ImageCompressor.CompressionCallback() {
//                        @Override
//                        public void onCompressionComplete(String originalFilePath, String compressedFilePath) {
//                            Log.d("TAG", "onStartCompress: "+originalFilePath+","+compressedFilePath);
//
//                            // 将压缩后的文件路径通过回调返回
//                            onKeyValueResultCallbackListener.onCallback(originalFilePath , compressedFilePath);
//                        }
//
//                        @Override
//                        public void onCompressionError(Exception e) {
//
//                        }
//                    });
//                }
//            })

            //Luban压缩框架好像有bug，部分图片没有压缩
//            .setCompressEngine(object : CompressFileEngine {
//                override fun onStartCompress(
//                    context: Context?,
//                    source: ArrayList<Uri?>?,
//                    call: OnKeyValueResultCallbackListener?
//                ) {
//                    Luban.with(context).load(source).ignoreBy(100)
//                        .setCompressListener(object : OnNewCompressListener {
//                            override fun onStart() {}
//                            override fun onSuccess(source: String?, compressFile: File) {
//                                if (call != null) {
//                                    call.onCallback(source, compressFile.absolutePath)
//                                }
//                            }
//
//                            override fun onError(source: String?, e: Throwable?) {
//                                if (call != null) {
//                                    call.onCallback(source, null)
//                                }
//                            }
//                        }).launch()
//                }
//            })
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>) {
                    viewModel.setData(Lists.newArrayList(result))//他返回的result不能更改而我们需要更改所以new、一个新的
                }

                override fun onCancel() {}
            })
    }

    /**
     * 返回菜单
     *
     * @param menu
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.publish, menu)
        return true
    }

    /**
     * 按钮点击了
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.publish) {
            viewModel.sendClick(binding.content.text.toString().trim())
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}