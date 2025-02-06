package id.elutility.core.ext

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat
import java.io.Serializable

inline fun <reified T: Serializable> Bundle.getSerializable(key: String): T? {
    return BundleCompat.getSerializable(this, key, T::class.java)
}

inline fun <reified T : Parcelable> Bundle.getParcelable(key: String): T? {
    return BundleCompat.getParcelable(this, key, T::class.java)
}

inline fun <reified T : Parcelable> Bundle.getParcelableArrayList(key: String): ArrayList<T>? {
    return BundleCompat.getParcelableArrayList(this, key, T::class.java)
}