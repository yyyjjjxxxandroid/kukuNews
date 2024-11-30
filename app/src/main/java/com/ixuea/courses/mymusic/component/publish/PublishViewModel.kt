package com.ixuea.courses.mymusic.component.publish

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.ixuea.courses.mymusic.AppContext

import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.component.aliyunoss.AliyunOSSService
import com.ixuea.courses.mymusic.component.aliyunoss.UploadResult
import com.ixuea.courses.mymusic.component.aliyunoss.onProgress
import com.ixuea.courses.mymusic.component.aliyunoss.onSuccess

import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.entity.response.onSuccess
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import com.luck.picture.lib.entity.LocalMedia

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import timber.log.Timber
import java.io.File

/**
 * 发布动态界面ViewModel
 */
class PublishViewModel() : BaseViewModel() {
    private lateinit var medias: List<LocalMedia>
    private var uploadMedias: List<String>? = null

    private val _success = MutableSharedFlow<Boolean>()
    val success: Flow<Boolean> = _success

    private val _data = MutableSharedFlow<List<Any>>()
    val data: Flow<List<Any>> = _data

    protected val _selectPosition = MutableLiveData<Any>()
    val selectPosition: LiveData<Any> = _selectPosition

    private val param: Content = Content()

    fun sendClick(content: String,  medias: List<LocalMedia>) {

        this.medias = medias
        param.content = content

        //判断是否输入了
        if (StringUtils.isBlank(content)) {
            _tip.value = R.string.hint_content
            return
        }

        //判断长度
        if (content.length > 1000) {
            _tip.value = R.string.error_content_length
            return
        }

        //获取选中的图片
        if (medias.isNotEmpty()) {
            //有图片

            //先上传图片
            uploadMedia()
        } else {
            //没有图片

            //直接发布
            save()
        }
    }


//    fun uploads(data: List<LocalMedia>): Flow<UploadResult<List<String>>> {
//        return flow<UploadResult<List<String>>> {
////            emit(UploadResult.OnProgress(0))
////            delay(1000)
////            emit(UploadResult.OnProgress(1))
////            delay(1000)
////            emit(UploadResult.Success())
//
//            //创建结果数组
//            val results = mutableListOf<String>()
//
//            data.forEachIndexed { index, it ->
//                emit(UploadResult.onProgress(index))
//
//                val file = File(if (StringUtils.isNotBlank(it.compressPath)) it.compressPath else it.realPath)
//
//                //文件表单项
//                val fileBody =
//                    file.asRequestBody(MimeTypes.IMAGE_ANY.toMediaType())
//                val multipartBody =
//                    MultipartBody.Part.createFormData("data", file.getName(), fileBody)
//
//                //渠道项
//                val flavorBody =
//                    BuildConfig.FLAVOR.toRequestBody(MimeTypes.MULTIPART_FORM_DATA.toMediaType())
//
//                val targetResult = DefaultNetworkRepository.uploadFile(
//                    multipartBody,
//                    flavorBody,
//                    Constant.VALUE1.toString()
//                        .toRequestBody(MimeTypes.MULTIPART_FORM_DATA.toMediaType())
//                )
//
//                if (!targetResult.isSucceeded) {
//                    emit(UploadResult.Failure(CommonException(targetResult)))
//                    return@flow
//                }
//
//                results.add(targetResult.data!!.id)
//            }
//
//            emit(UploadResult.Success(results))
//        }.flowOn(Dispatchers.IO) //通过flowOn方法切换到io线程
//    }

    private fun uploadMedia() {
        viewModelScope.launch(coroutineExceptionHandler) {
//            uploads(medias)
//                //collect：按顺序依次完整处理 Flow 发出的元素，若前一元素处理耗时，后续元素需等其处理完才开始处理，是顺序处理方式。如上述示例用 collect 替换 collectLatest，元素会依次等待前一元素 2 秒处理结束后再处理自身。
//                //collectLatest：侧重及时响应最新数据，会中断旧元素处理优先处理新元素，适用于实时消息推送展示、动态更新图表等对实时性和最新数据展示要求高的场景，可避免被旧数据处理流程耽搁而快速切换到新数据处理。
//                .collectLatest {
//                    it.onProgress {
//                        Log.d("upload media progress %d", it)
//                        _loading.value =
//                            AppContext.instance.getString(R.string.loading_upload, it + 1)
//                    }
//                    it.onSuccess {
//                        uploadMedias = it
//                        _loading.value = null
//                        save()
//                    }
//                    it.onFailure {
//                        _loading.value = null
//                    }
//                }

//            上传到阿里云
            AliyunOSSService.getInstance(AppContext.instance)
                .upload(medias)
                .collectLatest { it ->
                    it.onProgress {
                        Timber.d("upload media progress %d", it)
                        _loading.value = AppContext.instance.getString(R.string.loading_upload, it + 1)
                    }
                    it.onSuccess {
                        uploadMedias = it
                        _loading.value = null
                        save()
                    }
                }
        }
    }

    private fun save() {

        uploadMedias?.let {
            param.icon = it.joinToString(",")
        }
//
//        (selectPosition.value as? PoiItem)?.let {
//            //地理位置信息
//            //经度
////            param.setLongitude(selectPosition.getLatLonPoint().getLongitude())
////
////            //纬度
////            param.setLatitude(selectPosition.getLatLonPoint().getLatitude())
//
//            //省
//            param.province = "${it.cityName} . ${it.title}"
//        }

        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.createContent(param).onSuccess(viewModel) {
                _success.emit(true)
            }
        }
    }



    fun loadData() {
        setData(ArrayList())
    }

    fun setLocation(data: Any) {
        _selectPosition.value = data
    }

    fun setData(datum: MutableList<Any>) {
        if (datum.size < 9) {
            //添加选择图片按钮
            datum.add(R.drawable.add_fill)
        }

        viewModelScope.launch {
            _data.emit(datum)
        }
    }
}