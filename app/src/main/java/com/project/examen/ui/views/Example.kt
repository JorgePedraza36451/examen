package com.project.examen.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.project.examen.R
import com.project.examen.ui.event.CharacterEvent
import com.project.examen.ui.model.CharacterUiModel
import com.project.examen.ui.model.UiState
import com.project.examen.ui.theme.BlueText
import com.project.examen.ui.theme.GreenButton
import com.project.examen.ui.viewmodel.CharacterViewModel
import com.project.examen.utils.isScrolledToTheEnd

@Composable
fun ExampleScreen(
    navigationController: NavHostController,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    BaseContentView(
        title = "Rick and Morty App",
        requireFloatingButton = false,
        onBackPressed = { navigationController.popBackStack() },
    ){modifier ->

        val lifecycle = LocalLifecycleOwner.current.lifecycle

        val uiState by produceState<UiState>(
            initialValue = UiState.Loading,
            key1 = lifecycle,
            key2 = viewModel
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    value = it
                }
            }
        }

        LaunchedEffect(key1 = true) {
            viewModel.getServiceCharacter()
        }

        when(uiState) {
            is UiState.Error -> Log.e("Error", "Error")
            UiState.Loading -> Log.e("Loading", "Loading")
            is UiState.Success -> {
                val action = uiState as? UiState.Success
                action?.list?.let {
                    ContentApp(modifier = modifier, viewModel = viewModel, data = it)
                }
            }
        }
    }
}

@Composable
fun ContentApp(
    modifier: Modifier,
    viewModel: CharacterViewModel,
    data: List<CharacterUiModel>
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = modifier){
        itemsIndexed(data){index,character ->
            CardCharacter(character)
        }
        if (listState.isScrolledToTheEnd()) {
            viewModel.onEvent(CharacterEvent.GetCharacter)
        }


    }
}

@Composable
fun CardCharacter(character: CharacterUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        var showDetails by remember {
            mutableStateOf(false)
        }

        Column(Modifier.fillMaxWidth().padding(20.dp)){

            Row(Modifier.fillMaxWidth()){

                AsyncImage(
                    model = character.image,
                    contentDescription = "character image",
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(id = R.drawable.ic_character)
                )
                Column(
                    Modifier.weight(2f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = character.name ?: stringResource(id = R.string.home_name),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ))

                    Button(
                        onClick = { showDetails = !showDetails },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreenButton, contentColor = Color.Black)
                    ) {
                        Text(text = if(showDetails) stringResource(id = R.string.home_hide_detail) else stringResource(id = R.string.home_see_details),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ))
                    }
                }
            }
            if (showDetails) {
                DetailsItem(R.string.home_status, character.status)
                DetailsItem(R.string.home_species, character.species)
                DetailsItem(R.string.home_type, character.type)
                DetailsItem(R.string.home_gender, character.gender)
                DetailsItem(R.string.home_origin, character.origin)
                DetailsItem(R.string.home_location, character.location)
            }

        }



    }
}

@Composable
fun DetailsItem(
    detail: Int,
    content: String?,
) {
    if (content != null) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${stringResource(id = detail)}: ", style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold, color = BlueText
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = content, style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black
                )
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}


