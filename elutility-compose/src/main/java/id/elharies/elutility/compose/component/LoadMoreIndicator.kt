package id.elharies.elutility.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.elharies.elutility.compose.ext.shimmerEffect

@Composable
fun LoadMoreIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    showError: Boolean,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(16.dp),
    ) {
        when {
            isLoading -> Surface(modifier = Modifier.fillMaxWidth().height(42.dp).shimmerEffect()){}
            showError -> {
                OutlinedButton(onClick = onRetry, colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                ), modifier = Modifier.fillMaxWidth()) {
                    Text("Retry")
                }
            }
        }
    }
}

@Preview(name = "LoadMoreIndicator")
@Composable
private fun PreviewLoadMoreIndicator() {
    LoadMoreIndicator(isLoading = false, showError = true, onRetry = {})
}