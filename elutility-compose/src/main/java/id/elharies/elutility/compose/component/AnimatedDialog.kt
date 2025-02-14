package id.elharies.elutility.compose.component

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedDialog(
    inAnimDuration: Int = 720,
    outAnimDuration: Int = 450,
    properties: DialogProperties = DialogProperties(),
    onDismiss: () -> Unit = {},
    content: @Composable (triggerDismiss: () -> Unit) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var isDialogVisible by remember { mutableStateOf(false) }
    val animationSpec = tween<Float>(if (isDialogVisible) inAnimDuration else outAnimDuration)

    val dialogAlpha by animateFloatAsState(
        targetValue = if (isDialogVisible) 1f else 0f,
        animationSpec = animationSpec
    )

    val dialogRotationX by animateFloatAsState(
        targetValue = if (isDialogVisible) 0f else -90f,
        animationSpec = animationSpec
    )

    val dialogScale by animateFloatAsState(
        targetValue = if (isDialogVisible) 1f else 0f,
        animationSpec = animationSpec
    )

    val dismissWithAnimation: () -> Unit = {
        scope.launch {
            isDialogVisible = false
            delay(outAnimDuration.toLong())
            onDismiss()
        }
    }

    LaunchedEffect(Unit) {
        isDialogVisible = true
    }

    Dialog(
        onDismissRequest = dismissWithAnimation,
        properties = properties,
    ) {
        Box(
            modifier = Modifier.alpha(dialogAlpha).scale(dialogScale).graphicsLayer {
                rotationX = dialogRotationX
            },
        ) {
            content(dismissWithAnimation)
        }
    }
}

@Preview(name = "AnimatedDialog", showBackground = true, showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PreviewAnimatedDialog() {
    AnimatedDialog {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "👏 Clap Alert Dialog",
                color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surface),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Please show your appreciation by hitting the clap button.",
                color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surface),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = it,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Clap")
            }
        }
    }
}