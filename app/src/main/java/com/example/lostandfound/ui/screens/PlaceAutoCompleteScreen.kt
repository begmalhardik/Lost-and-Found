package com.example.lostandfound.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lostandfound.viewmodel.CreateAdvertViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest

@Composable
fun PlaceAutoCompleteScreen(
    navController: NavController,
    viewModel: CreateAdvertViewModel
) {

    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }

//    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

//    val placesClient = remember { Places.createClient(context) }

    val places = listOf(
        "Melbourne",
        "Geelong",
        "Werribee",
        "Footscray",
        "Ballarat",
        "Sunshine"
    )

    val filteredPlaces = places.filter {
        it.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->

                searchQuery = query

//                val request =
//                    FindAutocompletePredictionsRequest.builder()
//                        .setQuery(query)
//                        .build()
//
//                placesClient
//                    .findAutocompletePredictions(request)
//                    .addOnSuccessListener { response ->
//
//                        predictions = response.autocompletePredictions
//                    }
            },
            label = {
                Text("Search Location")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(filteredPlaces/*predictions*/) { place ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {

                            viewModel.onLocationSelected(
                                place,
                                0.0,
                                0.0
                            )

                            navController.popBackStack()

//                        val placeId = prediction.placeId
//
//                        val placeFields = listOf(
//                            Place.Field.NAME,
//                            Place.Field.LAT_LNG,
//                            Place.Field.ADDRESS
//                        )
//
//                        val request =
//                            FetchPlaceRequest.builder(
//                                placeId,
//                                placeFields
//                            ).build()
//
//                        placesClient.fetchPlace(request)
//                            .addOnSuccessListener { response ->
//
//                                val place = response.place
//
//                                viewModel.onLocationSelected(
//                                    place.address ?: "",
//                                    place.latLng?.latitude ?: 0.0,
//                                    place.latLng?.longitude ?: 0.0
//                                )
//
//                                navController.popBackStack()
//                            }

                        }
                ) {

                    Text(
                        text = place, // prediction.getFullText(null).toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

}
