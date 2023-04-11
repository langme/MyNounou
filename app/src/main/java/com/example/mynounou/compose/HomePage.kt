package com.example.mynounou.compose

import android.os.Build
import androidx.annotation.RequiresApi
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
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mynounou.viewmodels.CalendarViewModel
import com.example.mynounou.viewmodels.PlannedDay
import java.time.LocalDate

import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode.Single


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: CalendarViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Surface() {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ) {
                // TODO recap heure mois
                Text(text = LocalDate.now().toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(10f)
            ) {
                // TODO view calendar
                DisplayCalendar(viewModel)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
            ) {
                // TODO view selected day with info
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayCalendar(
    viewModel: CalendarViewModel
){
    val screenState by viewModel.selectionFlow.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val state = rememberSelectableCalendarState(
        initialSelectionMode = Single,
    )

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        if(!isLoading) {
            SelectableCalendar(
                calendarState = state,
                dayContent = { dayState ->
                    EventDay(
                        state = dayState,
                        eventDay = screenState.firstOrNull { it.date == dayState.date },
                        viewModel = viewModel
                    )
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDay(
    state: DayState<DynamicSelectionState>,
    eventDay: PlannedDay?,
    viewModel: CalendarViewModel,
    modifier: Modifier = Modifier,
) {
    val date = state.date
    val selectionState = state.selectionState
    var isClicked by remember {mutableStateOf(false) }
    val isSelected = selectionState.isDateSelected(date)

    var colorBackground = MaterialTheme.colorScheme.primaryContainer
    if (eventDay != null) {
        colorBackground = MaterialTheme.colorScheme.onSecondary
    }
    var elevation : Dp

    if (state.isFromCurrentMonth) elevation = 4.dp  else elevation = 0.dp

    var containerColor: Color

    if (isSelected) {
        containerColor = MaterialTheme.colorScheme.primary
    } else{
        containerColor = contentColorFor(
            backgroundColor = MaterialTheme.colorScheme.inversePrimary)
    }
    var addedEvent by remember {mutableStateOf(false) }

    if (isClicked){
        DialogHourPicker(
            selectedDay = viewModel.getDay(date),
            onClose = {isClicked = false},
            onSave = {
                //viewModel.updateDay(it)
                viewModel.addEventDay(it)
                addedEvent = true
                isClicked = false },
            onRemoveEvent = {
                isClicked =  false
                addedEvent = false
                viewModel.removeEventDay(it)
            }
        )
    }

    var noColorEvent = MaterialTheme.colorScheme.inversePrimary
    var colorEvent = Color.Red

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        border = if (state.isCurrentDay) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else BorderStroke(1.dp, MaterialTheme.colorScheme.inversePrimary),
        colors = CardDefaults.cardColors(
            containerColor =  containerColor,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground)
                .clickable() {
                    isClicked = true
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = MaterialTheme.colorScheme.primary
            )
            if (addedEvent || eventDay != null) {
                Canvas(
                    modifier = Modifier.size(18.dp),
                    onDraw = {
                        drawCircle(color = colorEvent)
                    })
            } else{
                Canvas(
                    modifier = Modifier.size(18.dp),
                    onDraw = { drawCircle(color = noColorEvent) })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogHourPicker(
    selectedDay : PlannedDay,
    onClose:()-> Unit,
    onSave:(PlannedDay) -> Unit,
    onRemoveEvent: (PlannedDay) -> Unit
){
    val context = LocalContext.current
    val calendar = android.icu.util.Calendar.getInstance()
    val hour = calendar[android.icu.util.Calendar.HOUR_OF_DAY]
    val minute = calendar[android.icu.util.Calendar.MINUTE]

    val timeStart = remember { mutableStateOf(selectedDay.plannedPeriod.startHour) }
    val timeEnd = remember { mutableStateOf(selectedDay.plannedPeriod.endHour) }


    val timeStartPickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            Log.i("", "Start change hour")
            timeStart.value = "$hour:$minute"
        }
        , hour, minute, true
    )
    val timeEndPickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            Log.i("", "End change hour")
            timeEnd.value = "$hour:$minute"
        },
        hour, minute, true
    )

    Dialog(onDismissRequest =  onClose) {
        Surface(
            modifier = Modifier
                .height(400.dp)
                .width(400.dp),
            shape = RoundedCornerShape(4.dp),
            color = MaterialTheme.colorScheme.onSurface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .weight(2f)
                ) {
                    Text(
                        text = "Choisir les horraires",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .padding(6.dp)) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Start Hour: ${timeStart.value}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(8.dp))
                            Button(modifier = Modifier.weight(1f),
                                onClick = {
                                    timeStartPickerDialog.show()
                                }) {
                                Text(text = "Start")
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "End Hour: ${timeEnd.value}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .weight(2f)
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
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(6.dp)) {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Button(onClick = {
                                selectedDay.plannedPeriod.startHour = timeStart.value
                                selectedDay.plannedPeriod.endHour = timeEnd.value
                                onSave(selectedDay)
                            },modifier = Modifier
                                .width(120.dp)
                        ) {
                            Text(text = "Valider")
                        }
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Button(onClick = onClose,
                            modifier = Modifier
                                .width(120.dp)
                        ) {
                            Text(text = "retour")
                        }
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Button(onClick = {onRemoveEvent(selectedDay)},
                            modifier = Modifier
                                .width(120.dp)
                        ) {
                            Text(text = "Delete")
                        }
                    }
                }
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