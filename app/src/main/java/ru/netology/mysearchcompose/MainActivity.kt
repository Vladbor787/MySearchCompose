package ru.netology.mysearchcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.netology.mysearchcompose.ui.theme.MySearchComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySearchComposeTheme {
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val aircrafts by viewModel.aircrafts.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
                Column(

                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = viewModel::onSearchTextChange,
                        label = { Text("Начни поиск") },
                        maxLines = 1,
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        textStyle = TextStyle(color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        placeholder = { Text(text = "Search") })
                    if (isSearching) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier

                                .fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(aircrafts) {
                                AircraftItem(aircraft = it)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun AircraftItemButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null,
        )
    }
}
@Composable
fun AircraftInfoItem(acRusReg: String, acType: String,acForReg:String,modifier: Modifier = Modifier) {
    Column {
        Row {
            Text(
                text = acRusReg,
                style = MaterialTheme.typography.h5,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(top = 4.dp)
            )
            Spacer(Modifier.widthIn(16.dp))
            Text(
                text = acForReg,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = modifier.padding(top = 4.dp)
            )
        }

        Text(
            text = acType,
            style = MaterialTheme.typography.body1
        )
    }
}
@Composable
fun AircraftItem(aircraft: Aircraft, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        elevation = 4.dp,
        modifier = modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AircraftInfoItem(aircraft.acRusReg,aircraft.acType,aircraft.acForReg)
                Spacer(Modifier.weight(1f))
                AircraftItemButton(
                    expanded = expanded,
                ) { expanded = !expanded }
            }
            if (expanded) {
                AircraftMoreInfo(aircraft.acSerialNum,aircraft.acEffNum)

            }
        }
    }
}

@Composable
fun AircraftMoreInfo(acSerNum: String,acEffNum: String, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .background(color = Color.Yellow)
            .padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp),

    ){
        Row {
            Text(
                text = stringResource(R.string.serNumber),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = acSerNum,
                style = MaterialTheme.typography.h5
            )
        }
        Row {
            Text(
                text = stringResource(R.string.effNumber),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = acEffNum,
                style = MaterialTheme.typography.h5
            )
        }


    }
}
