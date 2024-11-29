package com.ixuea.courses.mymusic.component.publish

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.drake.channel.sendEvent
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.databinding.ActivityPublishBinding
import kotlinx.coroutines.launch

class PublishActivity : BaseTitleActivity<ActivityPublishBinding>() {
    private lateinit var viewModel: PublishViewModel
    override fun initDatum() {
        super.initDatum()
         viewModel=ViewModelProvider(this)[PublishViewModel::class.java]
        initViewModel(viewModel)
         lifecycleScope.launch {
             viewModel.success.collect{
                 sendEvent(ContentChangedEvent())
                 finish()
             }
         }
    }
    override fun initListeners() {
        super.initListeners()
        binding.content.doAfterTextChanged {
            val result:String=getString(R.string.feed_count,it.toString().length)
            binding.count.text=result
        }
        //右上角发布按钮，在toolbar，so we can give the toolbar a menu

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