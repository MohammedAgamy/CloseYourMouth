package com.agamy.closeyourmouth.presentation.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Preview
@Composable
fun OtpScreen() {
    var otpCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Verify Your Phone Number",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Enter the 6-digit code sent to your number",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // OTP input field
        OtpTextField(
            otpText = otpCode,
            onOtpTextChange = { value, _ ->
                otpCode = value
            }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { /* TODO: Verify OTP here */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF002DE3)
            )
        ) {
            Text(text = "Verify", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun OtpTextField(
    otpText: String,
    onOtpTextChange: (String, Boolean) -> Unit,
    charCount: Int = 6
) {
    val context = LocalContext.current
    // Create a list of FocusRequesters for each OTP box
    val focusRequesters = List(charCount) { FocusRequester() }
    // Get the current FocusManager
    val focusManager = LocalFocusManager.current

    // Create a coroutine scope for focus operations
    val coroutineScope = rememberCoroutineScope()

    // Auto focus on first field when screen appears
    LaunchedEffect(Unit) {
        delay(300) // small delay to ensure UI is ready
        focusRequesters.first().requestFocus()
    }

    //var otp by remember { mutableStateOf("") }

    LaunchedEffect(otpText) {
        if (otpText.length == charCount) {
            coroutineScope {
                launch {
                   // viewModel.verifyOtp(otp)
                    Toast.makeText(context, otpText , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Create OTP input boxes
        // Iterate through each character position
        // and create an OutlinedTextField for each
        // handling focus and input changes

        for (i in 0 until charCount) {
            val value = otpText.getOrNull(i)?.toString() ?: ""
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    // Allow only single character input
                    if (newValue.length <= 1) {
                        // Update the OTP text
                        val newOtp = otpText.toMutableList()
                        // If newValue is not empty, set the character at position i
                        if (newValue.isNotEmpty()) {
                            // handle character input
                            if (i < otpText.length) newOtp[i] = newValue.first()
                            // if position i is beyond current length, add the character
                            else newOtp.add(newValue.first())
                            // notify OTP change
                            onOtpTextChange(newOtp.joinToString(""), newOtp.size == charCount)

                            // move focus to next box
                            if (i < charCount - 1) {
                                focusRequesters[i + 1].requestFocus()
                            } else {
                                focusManager.clearFocus()
                            }
                        } else {
                            // handle backspace → move focus to previous
                            if (i > 0) {
                                // move focus to previous box
                                // use coroutine to avoid focus conflicts
                                /*
                                في Jetpack Compose،
                                لما تحتاج تعمل حاجة صغيرة فيها تأخير زمني أو تنفيذ غير فوري (delay مثلاً أو انتظار حدث)،
                                 بتستخدم CoroutineScope عشان تشغّل العملية دي بشكل آمن داخل الـ Composable.
                                 */
                                coroutineScope.launch {
                                    delay(100)
                                    focusRequesters[i - 1].requestFocus()
                                }
                            }
                            // remove character at position i
                            val temp = otpText.toMutableList()
                            if (i < temp.size) temp[i] = ' '
                            onOtpTextChange(temp.joinToString("").trim(), false)
                        }
                    }
                },
                modifier = Modifier
                    .width(48.dp)
                    .height(56.dp)
                    .focusRequester(focusRequesters[i]),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF002DE3),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    cursorColor = Color(0xFF002DE3)
                )
            )
        }
    }
}

/*
rememberCoroutineScope() بيربط الكوروتين بعمر الـ Composable نفسه.
بمعنى آخر، لو الـ Composable اتلغى أو خرج من الشاشة (unmounted)، كل الكوروتينات اللي شغالة جواه بتتلغي تلقائياً.
وده يمنع الـ Memory leaks والـ crashes اللي ممكن تحصل لو حاولت تغيّر الـ focus بعد اختفاء الـ UI.

ليه ما استخدمناش LaunchedEffect
LaunchedEffect بيتنفذ مرة واحدة أو عند تغير مفتاح معين (key).
rememberCoroutineScope بيسمحلك تشغّل كوروتينات في أي وقت أثناء التفاعل (event)، زي هنا داخل onValueChange.
إحنا محتاجين نتحكم في التوقيت كل مرة المستخدم يكتب رقم، مش مرة واحدة عند تحميل الشاشة، فـ rememberCoroutineScope هو الأنسب.
 */