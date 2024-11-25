package com.ixuea.courses.mymusic.entity

import android.os.Parcel
import android.os.Parcelable

open class BaseId() :Base(), Parcelable {
    var id: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseId> {
        override fun createFromParcel(parcel: Parcel): BaseId {
            return BaseId(parcel)
        }

        override fun newArray(size: Int): Array<BaseId?> {
            return arrayOfNulls(size)
        }
    }
}