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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
                val persons by viewModel.persons.collectAsState()
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
                            items(persons) { person ->
                               Card(
                                   elevation = 4.dp,
                                   modifier = Modifier.padding(8.dp)
                               ) {
                                   Column (
                                       modifier = Modifier
                                           .animateContentSize(
                                               animationSpec = spring(
                                                   dampingRatio = Spring.DampingRatioMediumBouncy,
                                                   stiffness = Spring.StiffnessLow
                                               )
                                           )
                                   ){
                                       Row {
                                           Text(person.RusRegistr,
                                               color = Color.Blue,
                                               fontFamily = FontFamily.Serif,
                                               fontWeight = FontWeight.Bold,
                                               fontSize = 18.sp,
                                               modifier = Modifier
                                                   .weight(2f)
                                                   .background(
                                                       shape = RoundedCornerShape(4.dp),
                                                       color = Color.LightGray
                                                   )
                                           )
                                           Text(person.lastName,
                                               fontFamily = FontFamily.SansSerif,
                                               fontSize = 20.sp,
                                               fontWeight = FontWeight.Bold,
                                               fontStyle = FontStyle.Italic,
                                               modifier = Modifier
                                                   .graphicsLayer {
                                                       clip = true
                                                       shape = RoundedCornerShape(24.dp)
                                                   }
                                                   .background(
                                                       shape = RoundedCornerShape(4.dp),
                                                       color = Color.Yellow
                                                   )
                                                   .padding(horizontal = 8.dp)
                                                   .weight(4f))
                                           Spacer(Modifier.weight(1f))



                                       }
                                   }

                               }

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
            imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null,
        )
    }
}