package id.elharies.elutility

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.elharies.elutility.component.TempItem
import id.elharies.elutility.compose.component.LoadingScreen
import id.elharies.elutility.ui.theme.ElUtilityTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElUtilityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding).fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var isShowLoading by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowShimmer by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
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

        AnimatedVisibility(isShowShimmer) {
            LazyColumn(modifier = Modifier.height(400.dp)) {
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
                items(count = 3) {
                    TempItem(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (isShowLoading) {
        LoadingScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            isShowLoading = false
        }, 3000L)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElUtilityTheme {
        Greeting()
    }
}