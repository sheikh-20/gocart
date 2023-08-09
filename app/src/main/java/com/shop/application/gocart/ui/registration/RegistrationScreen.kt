package com.shop.application.gocart.ui.registration

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocalMall
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.shop.application.gocart.R
import com.shop.application.gocart.api.request.BusinessInfo
import com.shop.application.gocart.api.request.OtpVerify
import com.shop.application.gocart.api.response.NumVerifyResponse
import com.shop.application.gocart.ui.terms.TermsActivity
import com.shop.application.gocart.ui.viewmodel.RegistrationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "RegistrationScreen"
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier,
                       countryValue: String = "",
                       onCountryValueChange: (String) -> Unit = {},
                       phoneNumberValue: String = "",
                       onPhoneNumberValueChange: (String) -> Unit = {},
                       onVerifyClick: () -> Unit = {},
                       verifyResponse: NumVerifyResponse = NumVerifyResponse(),
                       onVerifyReset: () -> Unit = {},
                       onOtpClick: () -> Unit = {},
                       onOtpVerify: (otpVerify: OtpVerify, OtpState, activity: Activity?) -> Unit = { _, _, _ -> },
                       onBusinessInfoClick: (businessInfo: BusinessInfo) -> Unit = {},
                       uiState: RegistrationUiState = RegistrationUiState.Default
                       ) {

    val bgColor = listOf<Color>(Color(0xFFC2C7F4), Color(0xFF474D86))

    val appIconGradient = listOf<Color>(
        Color.Red,
        Color.Black,
    )

    var screenState by remember { mutableStateOf(RegistrationStates.ShowEmpty) }
    
    val modifierOne = modifier
        .fillMaxSize()
        .background(brush = Brush.linearGradient(colors = bgColor))
        .wrapContentSize(align = Alignment.Center)

    val modifierTwo = modifier
        .fillMaxSize()
        .background(brush = Brush.linearGradient(colors = bgColor))
        .wrapContentSize(align = Alignment.TopCenter)
        .padding(top = 50.dp)

    LaunchedEffect(key1 = null) {
        delay(3_000L)
        screenState = RegistrationStates.ShowNews
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                ),
            )
    ) {
        Column(modifier = if (screenState == RegistrationStates.ShowEmpty) modifierOne else modifierTwo, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(imageVector = Icons.Outlined.LocalMall,
                contentDescription = null,
                modifier = modifier
                    .size(120.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                brush = Brush.horizontalGradient(colors = appIconGradient),
                                blendMode = BlendMode.SrcAtop
                            )
                        }
                    },
                contentScale = ContentScale.Crop
            )

            if (screenState == RegistrationStates.ShowEmpty) {
                Text(text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,)
            }
        }

        if (screenState != RegistrationStates.ShowEmpty) {
            CustomBottomSheet(
                state = screenState,
                changeState =  { screenState = it },
                countryValue = countryValue,
                onCountryValueChange = onCountryValueChange,
                phoneNumberValue = phoneNumberValue,
                onPhoneNumberValueChange = onPhoneNumberValueChange,
                onVerifyClick = onVerifyClick,
                verifyResponse = verifyResponse,
                onVerifyReset = onVerifyReset,
                onOtpClick = onOtpClick,
                onOtpVerify = onOtpVerify,
                onBusinessInfoClick = onBusinessInfoClick,
                uiState = uiState
                )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun CustomBottomSheet(modifier: Modifier = Modifier,
                              state: RegistrationStates = RegistrationStates.ShowEmpty,
                              changeState: (state: RegistrationStates) -> Unit = {},
                              countryValue: String = "",
                              onCountryValueChange: (String) -> Unit = {},
                              phoneNumberValue: String = "",
                              onPhoneNumberValueChange: (String) -> Unit = {},
                              onVerifyClick: () -> Unit = {},
                              verifyResponse: NumVerifyResponse = NumVerifyResponse(),
                              onVerifyReset: () -> Unit = {},
                              onOtpClick: () -> Unit = {},
                              onOtpVerify: (otpVerify: OtpVerify, OtpState, activity: Activity?) -> Unit = { _, _, _ -> },
                              onBusinessInfoClick: (businessInfo: BusinessInfo) -> Unit = {},
                              uiState: RegistrationUiState = RegistrationUiState.Default
                              ) {

    val bgColor = listOf<Color>(Color(0xFFEFEFEF), Color(0xFFFFFFFF))

    Card(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.BottomCenter)
        .background(
            brush = Brush.linearGradient(colors = bgColor),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ) {

        Column(modifier = modifier.padding(10.dp)) {

            Spacer(modifier = modifier.height(6.dp))

            when (state) {
                RegistrationStates.ShowNews -> {

                    val pagerState = rememberPagerState()

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {

                        repeat(3) {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = null,
                                modifier = if (pagerState.currentPage == it) modifier.size(15.dp) else modifier.size(10.dp),
                                tint = if (pagerState.currentPage == it) Color(0xFF474D86) else Color.LightGray
                            )
                        }
                    }

                    HorizontalPager(count = 3, state = pagerState) {
                        when (it) {
                            0 -> {
                                PagerOneContent(modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)) {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                            1 -> {
                                PagerTwoContent(modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)) {
                                    pagerState.animateScrollToPage(2)
                                }
                            }
                            2 -> {
                                PagerThreeContent(modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)) {
                                    changeState(RegistrationStates.ShowRegister)
                                }
                            }
                        }
                    }
                }
                RegistrationStates.ShowRegister -> {
                    SignupSheet(
                        countryValue = countryValue,
                        onCountryValueChange = onCountryValueChange,
                        phoneNumberValue = phoneNumberValue,
                        onPhoneNumberValueChange = onPhoneNumberValueChange,
                        verifyResponse = verifyResponse,
                        onChangeState = changeState,
                        onOtpClick = onOtpClick,
                        uiState = uiState
                        )
                }
                RegistrationStates.ShowLogin -> {
                    LoginSheet(
                        countryValue = countryValue,
                        onCountryValueChange = onCountryValueChange,
                        phoneNumberValue = phoneNumberValue,
                        onPhoneNumberValueChange = onPhoneNumberValueChange,
                        onVerifyClick = onVerifyClick,
                        verifyResponse = verifyResponse,
                        onChangeState = changeState,
                        onVerifyReset = onVerifyReset,
                        onOtpClick = onOtpClick
                    )
                }
                RegistrationStates.ShowRegisterOTP -> {
                    SignupSheetOTP(
                        phoneNumberValue = phoneNumberValue,
                        onResendOtpClick = onOtpClick,
                        onSignupClicked = { onOtpVerify(it, OtpState.Signup, null) },
                        onChangeState = changeState
                    )
                }
                RegistrationStates.ShowLoginOTP -> {
                    LoginSheetOTP(
                        phoneNumberValue = phoneNumberValue,
                        onResendOtpClick = onOtpClick,
                        onLoginClicked = onOtpVerify,
                        uiState = uiState
                    )
                }
                RegistrationStates.ShowBusinessInfo -> {
                    BusinessInfoScreen(onBusinessInfoClick = onBusinessInfoClick)
                }
                else -> {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PagerOneContent(modifier: Modifier = Modifier, onClick: suspend () -> Unit = {}) {

    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(vertical = 10.dp)) {
        Text(text = "The e-commerce platform",
            fontSize = 30.sp,
            modifier = modifier,
            )
        
        Text(text = "Unleash your business potential with Gocart",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
            )
        
        FloatingActionButton(onClick = { scope.launch { onClick() } },
            modifier = modifier.padding(20.dp),
            shape = RoundedCornerShape(50)
            ) {
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PagerTwoContent(modifier: Modifier = Modifier, onClick: suspend () -> Unit = {}) {

    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(vertical = 10.dp)) {
        Text(text = "Create your online business",
            fontSize = 30.sp,
            modifier = modifier,
        )

        Text(text = "You can create your online business instantly",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
        )

        FloatingActionButton(onClick = { scope.launch { onClick() } },
            modifier = modifier.padding(20.dp),
            shape = RoundedCornerShape(50)
        ) {
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PagerThreeContent(modifier: Modifier = Modifier, onClick: suspend () -> Unit = {}) {

    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(vertical = 10.dp)) {
        Text(text = "Get started with Gocart",
            fontSize = 30.sp,
            modifier = modifier,
        )

        Text(text = "Create account and  start online business",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
        )

        FloatingActionButton(onClick = { scope.launch { onClick() } },
            modifier = modifier.padding(20.dp),
            shape = RoundedCornerShape(50)
        ) {
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SignupSheet(modifier: Modifier = Modifier,
                        countryValue: String = "",
                        onCountryValueChange: (String) -> Unit = {},
                        phoneNumberValue: String = "",
                        onPhoneNumberValueChange: (String) -> Unit = {},
                        verifyResponse: NumVerifyResponse = NumVerifyResponse(),
                        onChangeState: (state: RegistrationStates) -> Unit = {},
                        onOtpClick: () -> Unit = {},
                        uiState: RegistrationUiState = RegistrationUiState.Default
                        ) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var buttonClicked by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var numberState by remember { mutableStateOf(false) }

    when (uiState) {
        is RegistrationUiState.Default -> {
            loading = false
            numberState = false
        }
        is RegistrationUiState.Loading -> {
            loading = true
        }
        is RegistrationUiState.Success<*> -> {
            loading = false
            numberState = (uiState.result as NumVerifyResponse).status == true
        }
        is RegistrationUiState.Failure -> {
            loading = false
            Toast.makeText(context, "${uiState.message}", Toast.LENGTH_LONG).show()
        }
    }

    Column(modifier = modifier
        .padding(10.dp)
        .animateContentSize(
            animationSpec =
            spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
        )) {

        Text(text = "Sign Up",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF474D86)
            )

        Text(text = "Phone number", style = MaterialTheme.typography.bodyLarge)

        Row(modifier = modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = countryValue,
                onValueChange = onCountryValueChange,
                label = { Text(text = "Country") },
                modifier = modifier.weight(1f),
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFEFEFEF),
                    unfocusedLabelColor = Color(0xFF6B6565)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Right) })
                )

            Spacer(modifier = modifier.width(10.dp))

            OutlinedTextField(
                value = phoneNumberValue,
                onValueChange = onPhoneNumberValueChange,
                label = { if (!numberState) Text(text = "Mobile number") else Text(text = "Number already exists") },
                modifier = modifier.weight(2f),
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFEFEFEF),
                    unfocusedLabelColor = Color(0xFF6B6565)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                isError = numberState,
                trailingIcon = {
                    if (loading) {
                        CircularProgressIndicator()
                    }
                }
            )
        }

        TextButton(onClick = { TermsActivity.startActivity(context as Activity) }) {
            Text(text = "TERMS AND CONDITIONS",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF474D86),
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .padding(10.dp)
            )
        }

        Button(onClick = {
                         buttonClicked = true
            onOtpClick()
            onChangeState(RegistrationStates.ShowRegisterOTP)
        },
            shape = RoundedCornerShape(if (buttonClicked) 50 else 30),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .heightIn(min = 50.dp),

            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = numberState
            ) {
            Text(text = "GET OTP")
        }

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Already have an account?")
            TextButton(onClick = { onChangeState(RegistrationStates.ShowLogin) }) {
                Text(text = "Login", color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun LoginSheet(modifier: Modifier = Modifier,
                       countryValue: String = "",
                       onCountryValueChange: (String) -> Unit = {},
                       phoneNumberValue: String = "",
                       onPhoneNumberValueChange: (String) -> Unit = {},
                       onVerifyClick: () -> Unit = {},
                       verifyResponse: NumVerifyResponse = NumVerifyResponse(),
                       onChangeState: (state: RegistrationStates) -> Unit = {},
                       onVerifyReset: () -> Unit = {},
                       onOtpClick: () -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    if (countryValue.length == 2 && phoneNumberValue.length == 10) {
        onVerifyClick()
    } else {
        onVerifyReset()
    }

    Column(modifier = modifier.padding(10.dp)) {
        Text(text = "Login",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF474D86)
        )

        Text(text = "Phone number", style = MaterialTheme.typography.bodyLarge)

        Row(modifier = modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = countryValue,
                onValueChange = onCountryValueChange,
                label = { Text(text = "Country") },
                modifier = modifier.weight(1f),
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFEFEFEF),
                    unfocusedLabelColor = Color(0xFF6B6565)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Right) })
            )

            Spacer(modifier = modifier.width(10.dp))

            OutlinedTextField(
                value = phoneNumberValue,
                onValueChange = onPhoneNumberValueChange,
                label = { if (verifyResponse.status != false) Text(text = "Mobile number") else Text(text = "Number does not exist") },
                modifier = modifier.weight(2f),
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFEFEFEF),
                    unfocusedLabelColor = Color(0xFF6B6565)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                isError = verifyResponse.status == false
            )
        }

        Text(text = "TERMS AND CONDITIONS",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF474D86),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(10.dp)
        )
        Button(onClick = {
            onOtpClick()
            onChangeState(RegistrationStates.ShowLoginOTP)
        },
            shape = RoundedCornerShape(30),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .heightIn(min = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = verifyResponse.status ?: false
        ) {
            Text(text = "GET OTP")
        }

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account?")
            TextButton(onClick = { onChangeState(RegistrationStates.ShowRegister) }) {
                Text(text = "Sign up", color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SignupSheetOTP(modifier: Modifier = Modifier,
                           phoneNumberValue: String = "",
                           onResendOtpClick: () -> Unit = {},
                           onSignupClicked: (otpVerify: OtpVerify) -> Unit = {},
                           onChangeState: (state: RegistrationStates) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    var isChecked by remember { mutableStateOf(false) }
    var smsCode by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Sign Up",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF474D86)
        )

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {

            Text(text = "6-digit code sent to", style = MaterialTheme.typography.bodyLarge)

            Text(text = phoneNumberValue,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF474D86)
                )

            Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
        }

        Row(modifier = modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = smsCode,
                onValueChange = {
                    if (it.length <= 6) {
                        smsCode = it
                    }
                },
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(6) {
                            val char = when {
                                it >= smsCode.length -> ""
                                else -> smsCode[it].toString()
                            }

                            val isFocused = smsCode.length == it

                            isChecked = smsCode.length == 6

                            androidx.compose.material.Text(
                                text = char,
                                modifier = modifier
                                    .width(40.dp)
                                    .border(
                                        if (isFocused) 2.dp
                                        else 1.dp,
                                        if (isFocused) Color(0xFF474D86)
                                        else Color.LightGray, RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                style = androidx.compose.material.MaterialTheme.typography.h4,
                                textAlign = TextAlign.Center)

                            Spacer(modifier = modifier.width(8.dp))
                        }
                    }
                },
            )
        }

        OutlinedButton(onClick = onResendOtpClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(10.dp)
                .heightIn(min = 20.dp),
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFC2C7F4), ),
            border = BorderStroke(width = 0.dp, color = Color.Transparent)
            ) {
            Text(text = "RESEND OTP",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 12.sp
                )
        }

        Button(onClick = {
                         onSignupClicked(OtpVerify(country = 91, phoneNumber = phoneNumberValue.toLong(), otp = smsCode.toLong(), type = "signup"))
                        onChangeState(RegistrationStates.ShowBusinessInfo)
        },
            shape = RoundedCornerShape(30),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .heightIn(min = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = true
        ) {
            Text(text = "SIGN UP")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun LoginSheetOTP(modifier: Modifier = Modifier,
                          phoneNumberValue: String = "",
                          onResendOtpClick: () -> Unit = {},
                          onLoginClicked: (otpVerify: OtpVerify, OtpState, activity: Activity) -> Unit = { _, _, _ -> },
                          uiState: RegistrationUiState = RegistrationUiState.Loading
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var isChecked by remember { mutableStateOf(false) }

    var smsCode by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Login",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF474D86)
        )

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {

            Text(text = "6-digit code sent to", style = MaterialTheme.typography.bodyLarge)

            Text(text = phoneNumberValue,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF474D86)
            )

            Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
        }

        Row(modifier = modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = smsCode,
                onValueChange = {
                    if (it.length <= 6) {
                        smsCode = it
                    }
                },
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(6) {
                            val char = when {
                                it >= smsCode.length -> ""
                                else -> smsCode[it].toString()
                            }

                            val isFocused = smsCode.length == it

                            isChecked = smsCode.length == 6

                            androidx.compose.material.Text(
                                text = char,
                                modifier = modifier
                                    .width(40.dp)
                                    .border(
                                        if (isFocused) 2.dp
                                        else 1.dp,
                                        if (isFocused) Color(0xFF474D86)
                                        else Color.LightGray, RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                style = androidx.compose.material.MaterialTheme.typography.h4,
                                textAlign = TextAlign.Center)

                            Spacer(modifier = modifier.width(8.dp))
                        }
                    }
                },
            )
        }

        OutlinedButton(onClick = onResendOtpClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(10.dp)
                .heightIn(min = 20.dp),
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFC2C7F4), ),
            border = BorderStroke(width = 0.dp, color = Color.Transparent)
        ) {
            Text(text = "RESEND OTP",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 12.sp
            )
        }

        Button(onClick = {
            onLoginClicked(
                OtpVerify(country = 91, phoneNumber = phoneNumberValue.toLong(), otp = smsCode.toString().toLong(), type = "login"),
                OtpState.Login,
                context as Activity,)
//            HomeActivity.startActivity(context as Activity)
        },
            shape = RoundedCornerShape(30),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .heightIn(min = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = true
        ) {
            Text(text = "LOGIN")
        }

//        when (uiState.response?.code) {
//            200 -> {
//                HomeActivity.startActivity(context as Activity)
//            }
//            400 -> {
//                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_LONG).show()
//            }
//            else -> {
//                Log.e(TAG, "Response code -> ${uiState.response?.code}")
//            }
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun BusinessInfoScreen(modifier: Modifier = Modifier, onBusinessInfoClick: (businessInfo: BusinessInfo) -> Unit = {}) {

    val focusManager = LocalFocusManager.current

    var usernameInput by remember { mutableStateOf("") }
    var businessInfoInput by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Let’s begin to set you up!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF474D86)
        )

        Text(text = "Please add your business information to get started",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.fillMaxWidth()
        )

        Column(modifier = modifier.padding(vertical = 8.dp),
               verticalArrangement = Arrangement.spacedBy(8.dp)) {

            OutlinedTextField(
                value = usernameInput,
                onValueChange = { usernameInput = it },
                modifier = modifier.fillMaxWidth(),
                label = { Text(text = "Your name") },
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFEFEFEF),
                    unfocusedLabelColor = Color(0xFF6B6565)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) }),
                isError = false
            )

            OutlinedTextField(
                value = businessInfoInput,
                onValueChange = { businessInfoInput = it },
                modifier = modifier.fillMaxWidth(),
                label = { Text(text = "Business name") },
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFEFEFEF),
                    unfocusedLabelColor = Color(0xFF6B6565)),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                isError = false
            )
        }

        Button(onClick = { onBusinessInfoClick(BusinessInfo(usernameInput, businessInfoInput)) },
            shape = RoundedCornerShape(30),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .heightIn(min = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = true
        ) {
            Text(text = "CONTINUE")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen()
}

enum class RegistrationStates {
    ShowEmpty,
    ShowNews,
    ShowRegister,
    ShowRegisterOTP,
    ShowLogin,
    ShowLoginOTP,
    ShowBusinessInfo
}

enum class OtpState {
    Login, Signup
}