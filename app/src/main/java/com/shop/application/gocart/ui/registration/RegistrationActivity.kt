package com.shop.application.gocart.ui.registration

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.application.gocart.ui.home.HomeActivity
import com.shop.application.gocart.ui.registration.RegistrationScreen
import com.shop.application.gocart.ui.theme.GocartTheme
import com.shop.application.gocart.ui.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationActivity : ComponentActivity() {
    companion object {
        private const val TAG = "RegistrationActivity"
    }

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        lifecycle.coroutineScope.launch {
//            viewModel.uiState.collect {
//                if (it.token.isNotEmpty()) {
//                    finish()
//                    HomeActivity.startActivity(this@RegistrationActivity)
//                }
//            }
//        }

        setContent {
            GocartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistrationApp()
                }
            }
        }
    }
}