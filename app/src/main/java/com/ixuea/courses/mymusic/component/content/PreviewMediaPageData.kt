package com.ixuea.courses.mymusic.component.content

import androidx.recyclerview.widget.RecyclerView
import com.ixuea.courses.mymusic.entity.Base

class PreviewMediaPageData (
    var view:RecyclerView,
    var medias:List<String>,
    var position:Int=0
):Base(){
}