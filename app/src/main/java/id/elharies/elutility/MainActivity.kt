package id.elharies.elutility

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.elharies.elutility.component.TempItem
import id.elharies.elutility.compose.component.AnimatedDialog
import id.elharies.elutility.compose.component.LoadingScreen
import id.elharies.elutility.compose.modifier.dashedborder.dashedBorder
import id.elharies.elutility.compose.modifier.fadingedge.bottomFadingEdge
import id.elharies.elutility.compose.modifier.fadingedge.topFadingEdge
import id.elharies.elutility.ui.theme.ElUtilityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElUtilityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    var isShowLoading by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowShimmer by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowAnimateDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
            .padding(4.dp)
            .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(18.dp))
    ) {
        OutlinedButton(onClick = {
            OldActivity.start(context)
        }) {
            Text("Go To Next")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = {
            isShowLoading = !isShowLoading
        }) {
            Text("Show Loading")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = {
            isShowShimmer = !isShowShimmer
        }) {
            Text("Show Loading Shimmer")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = {
            isShowAnimateDialog = !isShowAnimateDialog
        }) {
            Text("Show Animated Dialog")
        }

        AnimatedVisibility(isShowShimmer) {
            LazyColumn(state = listState,
                modifier = Modifier
                    .height(400.dp)
                    .topFadingEdge(color = Color.LightGray, isVisible = listState.canScrollBackward)
                    .bottomFadingEdge(color = MaterialTheme.colorScheme.surface, isVisible = listState.canScrollForward)
            ) {
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
                items(count = 8) {
                    TempItem(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (isShowLoading) {
        LoadingScreen {
            isShowLoading = false
        }
        Handler(Looper.getMainLooper()).postDelayed({
            isShowLoading = false
        }, 3000L)
    }

    if (isShowAnimateDialog) {
        ExampleAnimatedDialog(onDismiss = { isShowAnimateDialog = false })
    }
}

@Composable
private fun ExampleAnimatedDialog(onDismiss: () -> Unit = {}) {
    AnimatedDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(4.dp)
                .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(22.dp))
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElUtilityTheme {
        Greeting()
    }
}