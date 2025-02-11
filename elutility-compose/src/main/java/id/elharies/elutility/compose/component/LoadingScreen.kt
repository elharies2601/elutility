package id.elharies.elutility.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset("loading.json"),
        imageAssetsFolder = "assets"
    )
    AlertDialog(
        modifier = modifier.size(200.dp),
        containerColor = Color.Transparent,
        onDismissRequest = {},
        confirmButton = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        text = {
            LottieAnimation(lottieComposition, iterations = LottieConstants.IterateForever)
        }
    )
}

@Preview(name = "LoadingScreen")
@Composable
private fun PreviewLoadingScreen() {
    LoadingScreen()
}