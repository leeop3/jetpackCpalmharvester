package com.palmharvest.pro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.palmharvest.pro.ui.screens.LoginScreen
import com.palmharvest.pro.ui.screens.MainScreen
import com.palmharvest.pro.ui.theme.PalmHarvestTheme
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        setContent {
            PalmHarvestTheme {
                var user by remember { mutableStateOf<String?>(null) }

                if (user == null) {
                    LoginScreen(
                        onLogin = { email, _ -> user = email },
                        onRegister = { email, _, _ -> user = email }
                    )
                } else {
                    MainScreen(
                        user = user!!,
                        onLogout = { user = null }
                    )
                }
            }
        }
    }
}
