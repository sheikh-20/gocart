package com.shop.application.gocart.ui.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shop.application.gocart.api.request.NumberVerify
import com.shop.application.gocart.api.response.NumVerifyResponse
import com.shop.application.gocart.ui.theme.GocartTheme
import com.shop.application.gocart.ui.viewmodel.RegistrationViewModel

@Composable
fun RegistrationApp(modifier: Modifier = Modifier, viewModel: RegistrationViewModel = hiltViewModel()) {

    val uiState by viewModel.registrationUiState.collectAsState()

    GocartTheme {
        RegistrationScreen(
            countryValue = viewModel.country.toString(),
            onCountryValueChange = viewModel::updateCountryField,
            phoneNumberValue = viewModel.phoneNumber.toString(),
            onPhoneNumberValueChange = viewModel::updatePhoneNumberField,
            onVerifyClick = {
                viewModel.numberVerify(
                    NumberVerify(country = viewModel.country, phoneNumber = viewModel.phoneNumber)
                )
            },
            verifyResponse = viewModel.numberVerifyResponse,
            onVerifyReset = { viewModel.updateNumberVerifyResponseField(NumVerifyResponse()) },
            onOtpClick = {
                viewModel.sendOTP(
                    NumberVerify(country = viewModel.country, phoneNumber = viewModel.phoneNumber)
                )
            },
            onOtpVerify = viewModel::verifyOTP,
            onBusinessInfoClick = viewModel::updateBusinessInfo,
            uiState = uiState
        )
    }
}