package id.elutility.core.ext

import android.content.Intent
import android.os.Parcelable
import androidx.core.content.IntentCompat
import java.io.Serializable

inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? {
    return IntentCompat.getSerializableExtra(this, key, T::class.java)
}

inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? {
    return IntentCompat.getParcelableExtra(this, key, T::class.java)
}

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(key: String): ArrayList<T>? {
    return IntentCompat.getParcelableArrayListExtra(this, key, T::class.java)
}