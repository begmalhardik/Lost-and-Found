package com.example.lostandfound.ui.screens

import android.Manifest
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lostandfound.viewmodel.AdvertListViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.Priority

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: AdvertListViewModel
) {

    val context = LocalContext.current

    val adverts by viewModel.adverts.collectAsState()

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var currentLocation by remember {
        mutableStateOf(
            LatLng(-37.8136, 144.9631) // Default Melbourne
        )
    }

    // Radius in KM
    var radiusKm by remember { mutableStateOf(50f) }

    // Request permission
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    // Get current location
    LaunchedEffect(permissionState.status) {

        if (permissionState.status.isGranted) {

            fusedLocationClient
                .getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).addOnSuccessListener { location ->

                location?.let {
                    currentLocation = LatLng(
                        it.latitude,
                        it.longitude
                    )
                }
            }
        }
    }

    // FILTER ITEMS WITHIN RADIUS
    val filteredAdverts = adverts.filter { advert ->

        if (
            advert.latitude != null &&
            advert.longitude != null
        ) {

            val results = FloatArray(1)

            Location.distanceBetween(
                currentLocation.latitude,
                currentLocation.longitude,
                advert.latitude,
                advert.longitude,
                results
            )

            val distanceKm = results[0] / 1000

            distanceKm <= radiusKm

        } else {
            false
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation,
            8f
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Showing items within ${radiusKm.toInt()} KM",
            modifier = Modifier.padding(16.dp)
        )

        Slider(
            value = radiusKm,
            onValueChange = {
                radiusKm = it
            },
            valueRange = 1f..100f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {

            // User marker
            Marker(
                state = MarkerState(
                    position = currentLocation
                ),
                title = "Your Location"
            )

            // DATABASE MARKERS
            filteredAdverts.forEach { advert ->

                Marker(
                    state = MarkerState(
                        position = LatLng(
                            advert.latitude!!,
                            advert.longitude!!
                        )
                    ),
                    title = advert.title,
                    snippet = advert.location
                )
            }
        }
    }
}