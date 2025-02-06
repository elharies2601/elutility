package id.elutility.core.ext

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.requestPermission(
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit
) {
    val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) onGranted() else onDenied()
    }

    if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
        onGranted()
    } else {
        launcher.launch(permission)
    }
}

fun Fragment.requestMultiplePermission(
    permissions: Array<String>,
    onAllGranted: () -> Unit,
    onAnyDenied: (deniedPermissions: List<String>) -> Unit
) {
    val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        val denied = results.filterValues { !it }.keys.toList()
        if (denied.isEmpty()) onAllGranted() else onAnyDenied(denied)
    }

    val deniedPermissions = permissions.filter {
        ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
    }

    if (deniedPermissions.isEmpty()) {
        onAllGranted()
    } else {
        launcher.launch(deniedPermissions.toTypedArray())
    }
}