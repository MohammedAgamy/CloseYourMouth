package com.agamy.closeyourmouth.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agamy.closeyourmouth.R

@Composable
fun LogInScreen() {
    //login screen implementation
    //login with phone number


    Column(
        modifier = Modifier.fillMaxSize(),
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
        InPutPhoneNumber()
    }
}

@Composable
fun HintText() {

    Text(
        "Enter Your Phone Number",
        fontSize = 26.sp,
        fontWeight = Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

    Text(
        "Please confirm your country code and enter\n your phone number",
        fontSize = 16.sp,
        color = Color.Black,
        textAlign = TextAlign.Center
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InPutPhoneNumber() {
    val countries = listOf(
        Country("Egypt", "+20", R.drawable.egypt),
        Country("Saudi Arabia", "+966", R.drawable.saudiarabia),
        Country("UAE", "+971", R.drawable.unitedarabemirates),
        Country("Kuwait", "+965", R.drawable.kuwait)
    )
    var selectedCountry by remember { mutableStateOf(countries.first()) }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Enter your phone number",
            fontSize = 20.sp,
            fontWeight = Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {

            CountryPicker(
                selectedCountry = selectedCountry,
                onCountrySelected = { selectedCountry = it } ,
                countries = countries
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = selectedCountry.code ,
                fontSize = 18.sp,
                fontWeight = Bold,
                modifier = Modifier.padding(end = 8.dp)
            )

            TextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Phone Number")
                },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val fullNumber = selectedCountry.code + phone
                println("Full Phone: $fullNumber")
            }
        ) {
            Text("Continue")
        }
    }

}


data class Country(
    val name: String,
    val code: String,        // +20 , +966 , +971 ...
    val flagRes: Int         // العلم من drawable
)

@Composable
fun CountryPicker(
    selectedCountry: Country?,
    onCountrySelected: (Country) -> Unit ,
    countries: List<Country>
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
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.forEach { country ->
                DropdownMenuItem(
                    onClick = {
                        onCountrySelected(country)
                        expanded = false
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(country.flagRes),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(country.code) // يظهر فقط الرمز
                        }
                    }
                )
            }
        }
    }
}