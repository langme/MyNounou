package com.example.mynounou.compose

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarDay
import com.himanshoe.kalendar.model.KalendarType
import android.app.TimePickerDialog
import androidx.compose.ui.platform.LocalContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    navController: NavHostController
){
    var addDate by remember { mutableStateOf(false) }
    if (addDate) {
        DialogHourPicker(onClose = {addDate = false})
    } else {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.weight(3f)) {
                    Calendar(modifier = Modifier.fillMaxSize(),
                        onDaySelected = {
                            Log.i("date", "date: $it")
                            addDate = true
                        })
                }

                Row(modifier = Modifier.weight(1f)) {
                    Text(text = "HomePage")
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(modifier : Modifier, onDaySelected: (KalendarDay) -> Unit){
    val CalendarThemeColor = KalendarThemeColor(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        dayBackgroundColor = MaterialTheme.colorScheme.inversePrimary,
        headerTextColor = MaterialTheme.colorScheme.primary
    )
    val listCalendarThemeColor = mutableListOf<KalendarThemeColor>()
    for (i in 1..12) {
        listCalendarThemeColor.add(CalendarThemeColor)
    }

    Kalendar(
        modifier = modifier,
        kalendarType = KalendarType.Firey,
        kalendarThemeColors = listCalendarThemeColor,
        onCurrentDayClick =
            { day, _ ->
                onDaySelected(day)
            }
        )

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogHourPicker(onClose:()->Unit){
    Dialog(onDismissRequest =  onClose ) {
        Surface(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp),
            shape = RoundedCornerShape(4.dp),
            color = MaterialTheme.colorScheme.onSurface
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(modifier = Modifier.align(Alignment.TopCenter),
                    text = "Choisir les horraires",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                HoursPicker()
                Button(onClick = onClose,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(140.dp)
                        .padding(4.dp)
                ) {
                    Text(text = "Validate")
                }
            }
        }
    }
}

@Composable
fun HoursPicker(){
    val context = LocalContext.current
    val calendar = android.icu.util.Calendar.getInstance()
    val hour = calendar[android.icu.util.Calendar.HOUR_OF_DAY]
    val minute = calendar[android.icu.util.Calendar.MINUTE]

    val timeStart = remember { mutableStateOf("") }
    val timeEnd = remember { mutableStateOf("") }
    val timeStartPickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            timeStart.value = "$hour:$minute"
        }, hour, minute, true
    )
    val timeEndPickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            timeStart.value = "$hour:$minute"
        }, hour, minute, true
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Start Hour: ${timeStart.value}",
                modifier = Modifier.weight(2f)
                    .padding(8.dp))
            Button(modifier = Modifier.weight(1f),
                onClick = {
                timeStartPickerDialog.show()
            }) {
                Text(text = "Start")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "End Hour: ${timeEnd.value}",
                modifier = Modifier.weight(2f)
                    .padding(8.dp))
            Button(modifier = Modifier.weight(1f),
                onClick = {
                timeEndPickerDialog.show()
            }) {
                Text(text = "End")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    var navController = rememberNavController()
    HomePage (navController)
}