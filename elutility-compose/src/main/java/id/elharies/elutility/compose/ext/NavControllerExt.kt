package id.elharies.elutility.compose.ext

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

private var lastTimeClick = 0L

fun SnapshotStateList<NavKey>.safeNavigateSingleTop(key: NavKey, delay: Long = 500L) {
    val now = System.currentTimeMillis()
    if (now - lastTimeClick > delay) {
        if (lastOrNull() != key) add(key)
        lastTimeClick = now
    }
}

fun SnapshotStateList<NavKey>.navigateClear(key: NavKey) {
    clear()
    add(key)
}
