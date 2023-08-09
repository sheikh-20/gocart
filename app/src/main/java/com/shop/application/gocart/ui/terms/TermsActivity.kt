package com.shop.application.gocart.ui.terms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.shop.application.gocart.ui.home.HomeActivity
import com.shop.application.gocart.ui.theme.GocartTheme

class TermsActivity: ComponentActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, TermsActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GocartTheme {
                Text(text = "Hello World")
            }
        }
    }
}