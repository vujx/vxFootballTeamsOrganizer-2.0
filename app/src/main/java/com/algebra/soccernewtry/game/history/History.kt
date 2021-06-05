package com.algebra.soccernewtry.game.history

import android.os.Parcel
import android.os.Parcelable

data class History(
    var goalRed: Int,
    var goalBlue: Int,
    val assisst: String?,
    val goalGeter: String?,
    val isRed: Boolean,
    val id: Int,
    val autoGoal: String? = "",
    val goalGeterId: Int,
    val assisterId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(goalRed)
        parcel.writeInt(goalBlue)
        parcel.writeString(assisst)
        parcel.writeString(goalGeter)
        parcel.writeByte(if (isRed) 1 else 0)
        parcel.writeInt(id)
        parcel.writeString(autoGoal)
        parcel.writeInt(goalGeterId)
        parcel.writeInt(assisterId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<History> {
        override fun createFromParcel(parcel: Parcel): History {
            return History(parcel)
        }

        override fun newArray(size: Int): Array<History?> {
            return arrayOfNulls(size)
        }
    }
}
