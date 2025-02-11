package id.elharies.elutility.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.WindowClose

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    title: String = "Something went wrong",
    desc: String = "Please try again later",
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            FontAwesomeIcons.Regular.WindowClose,
            contentDescription = "Icon error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(54.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(title, style = MaterialTheme.typography.titleLarge.copy(color = Color.Black))
        Spacer(modifier = Modifier.height(4.dp))
        Text(desc, style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black))
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(name = "ErrorScreen")
@Composable
private fun PreviewErrorScreen() {
    ErrorScreen()
}