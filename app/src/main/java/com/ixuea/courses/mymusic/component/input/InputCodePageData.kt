package com.ixuea.courses.mymusic.component.input

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

/**
 * 输入验证码界面数据模型
 */
class InputCodePageData() : Parcelable {
    var style = 0
    var phone: String? = null
    var email: String? = null
    var code: String? = null

    constructor(style: Int) : this() {
        this.style = style
    }
//这个构造函数用于从一个 Parcel 对象中反序列化数据来创建 InputCodePageData 类的实例。
    constructor(parcel: Parcel) : this() {
        style = parcel.readInt()
        phone = parcel.readString()
        email = parcel.readString()
        code = parcel.readString()
    }
//用于将对象的各个成员变量的值序列化写入到 Parcel 对象中
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(style)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<InputCodePageData> {
        override fun createFromParcel(parcel: Parcel): InputCodePageData {
            return InputCodePageData(parcel)
        }

        override fun newArray(size: Int): Array<InputCodePageData?> {
            return arrayOfNulls(size)
        }
    }

}