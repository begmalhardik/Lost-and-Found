package com.example.lostandfound.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen() {

    val context = LocalContext.current

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var currentLocation by remember {
        mutableStateOf(
            LatLng(-37.8136, 144.9631) // Default Melbourne
        )
    }

    // Request permission
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    // Get current location
    LaunchedEffect(permissionState.status) {

        if (permissionState.status.isGranted) {

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                location?.let {
                    currentLocation = LatLng(
                        it.latitude,
                        it.longitude
                    )
                }
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation,
            8f
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        // Current location marker
        Marker(
            state = MarkerState(position = currentLocation),
            title = "You are here"
        )

        val locations = listOf(

            LatLng(-37.8136, 144.6631),
            LatLng(-37.8600, 144.9800),
            LatLng(-37.9000, 144.7000),
            LatLng(-38.0000, 144.5000),
            LatLng(-38.0800, 144.3500),
            LatLng(-38.1499, 144.3617)
        )

        locations.forEachIndexed { index, latLng ->
            Marker(
                state = MarkerState(position = latLng),
                title = "Lost Item ${index + 1}",
                snippet = "Between melbourne and geelong"
            )
        }
    }
}