package com.agamy.closeyourmouth.presentation.auth.login.phone

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agamy.closeyourmouth.R
import com.google.firebase.auth.PhoneAuthCredential
import androidx.hilt.navigation.compose.hiltViewModel
import com.agamy.closeyourmouth.domain.datastore.UserPreferences
import com.agamy.closeyourmouth.presentation.navigation.Routes


@Composable
fun LogInScreen(
    viewModel: PhoneNumberViewModel = hiltViewModel(),
    navController: NavController
) {
    //login screen implementation
    //login with phone number

    val state by viewModel.state.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }


    //head to toe layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.size(38.dp))
        Image(
            painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
            contentDescription = "Back Arrow",
            modifier = Modifier
                .fillMaxWidth()
                .size(24.dp)
                .padding(horizontal = 18.dp),
            alignment = Alignment.TopStart
        )

        Spacer(modifier = Modifier.size(48.dp))
        HintText()
        Spacer(modifier = Modifier.size(32.dp))
        InPutPhoneNumber(
            phoneNumber = phoneNumber,
            onPhoneChange = { phoneNumber = it })
        ContinueButten(navController = navController, phoneNumber, viewModel, state)
    }
}

@Composable
fun HintText() {

    Text(
        "Enter Your Phone Number",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 26.sp,
        fontWeight = Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

    Text(
        "Please confirm your country code and enter\n your phone number",
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black,
        textAlign = TextAlign.Center
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InPutPhoneNumber(
    phoneNumber: String,
    onPhoneChange: (String) -> Unit
) {
    val countries = listOf(
        Country("Egypt", "+20", R.drawable.egypt),
        Country("Saudi Arabia", "+966", R.drawable.saudiarabia),
        Country("UAE", "+971", R.drawable.unitedarabemirates),
        Country("Kuwait", "+965", R.drawable.kuwait)
    )
    var selectedCountry by remember { mutableStateOf(countries.first()) }
    //var phoneNumber by remember { mutableStateOf(phoneNumber1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Enter your phone number",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        //for all inPout filed (flag_code cuntry code  phone number)
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {

            //fow flag and cuntry code
            //clip to rounded corner tack shape and background color
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp)) // زوايا دائرية
                    .background(Color(0xFFF7F7FC))   // الخلفية فقط
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CountryPicker(
                    selectedCountry = selectedCountry,
                    onCountrySelected = { selectedCountry = it },
                    countries = countries
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = selectedCountry.code,
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = phoneNumber,
                onValueChange = { onPhoneChange(it) },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Phone Number", color = Color.LightGray)
                },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color(0xFFF7F7FC),
                    focusedIndicatorColor = Color(0xFFF7F7FC),
                    unfocusedContainerColor = Color(0xFFF7F7FC),
                    focusedContainerColor = Color(0xFFF7F7FC),

                    )
            )
        }
    }


}


// Country Picker Composable
// لعرض قائمة منسدلة لاختيار الدولة
//  يعرض العلم ورمز الدولة فقط
@Composable
fun CountryPicker(
    selectedCountry: Country?, onCountrySelected: (Country) -> Unit, countries: List<Country>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Image(
            painter = painterResource(
                id = selectedCountry?.flagRes ?: R.drawable.egypt
            ),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable { expanded = true })

        // القائمة المنسدلة لاختيار الدولة
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            countries.forEach { country ->
                DropdownMenuItem(onClick = {
                    onCountrySelected(country)
                    expanded = false
                }, text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(country.flagRes),
                            contentDescription = null,
                            modifier = Modifier.size(35.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(country.code) // يظهر فقط الرمز
                    }
                })
            }
        }
    }
}

@Composable
fun ContinueButten(
    navController: NavController,
    phoneNumber: String,
    viewModel: PhoneNumberViewModel,
    state: PhoneNumberState
) {
    Spacer(modifier = Modifier.height(50.dp))
    val context = LocalContext.current

    Button(
        onClick = {
            if (phoneNumber.isNotBlank()) {
                viewModel.handleIntent(
                    PhoneNumberIntent.SendCode(
                        "+20" + phoneNumber.trim(),
                        context as Activity
                    )
                )
            }
        },
        enabled = state !is PhoneNumberState.Loading,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        colors = ButtonColors(
            containerColor = Color(0xFF002DE3),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF002DE3),
            disabledContentColor = Color.White,
        ),
        shape = RoundedCornerShape(32.dp)
    ) {
        Text(
            text = if (state is PhoneNumberState.Loading)
                "Sending..."
            else
                "Send OTP"
        )
    }

    val userPrefs = UserPreferences(context)
    when (val currentState = state) {
        is PhoneNumberState.Loading -> {
            CircularProgressIndicator()
        }

        is PhoneNumberState.CodeSent -> {
            Text(
                text = "Code sent successfully!",
                color = MaterialTheme.colorScheme.primary
            )

            // ✅ التنقل إلى شاشة OTP وتمرير verificationId
            LaunchedEffect(currentState.verificationId) {
                navController.navigate(Routes.otp(currentState.verificationId))
                userPrefs.saveUser(phoneNumber)

            }
        }

        is PhoneNumberState.SuccessAuto -> {
            Text(
                text = "Auto verification success!",
                color = MaterialTheme.colorScheme.primary
            )
        }

        is PhoneNumberState.Error -> {
            Text(
                text = currentState.message,
                color = MaterialTheme.colorScheme.error
            )
        }

        else -> Unit
    }
}

