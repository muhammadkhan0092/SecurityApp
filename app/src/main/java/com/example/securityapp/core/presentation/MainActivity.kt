package com.example.securityapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.securityapp.app.App
import com.example.securityapp.ui.theme.SecurityAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecurityAppTheme {
                App()
            }
        }
    }
}
//fun dummyCode(){
                 //  uninstallPackage(LocalContext.current,"com.example.hazir")
//               // stopLockTask()
//                RequestManageStoragePermission()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
    //        if (!Settings.canDrawOverlays(this)) {
//            Log.d("KHAN","Requesting overlay permission")
//            val intent = Intent(
//                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                Uri.parse("package:$packageName")
//            )
//            startActivity(intent)
//        } else {
//            Log.d("KHAN","Starting overlay service")
//            val serviceIntent = Intent(this, OverlayService::class.java)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(serviceIntent)
//            } else {
//                startService(serviceIntent)
//            }
//        }
    //deleteAllGalleryFiles()
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SecurityAppTheme {
        Greeting("Android")
    }
}