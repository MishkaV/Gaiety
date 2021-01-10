package view.fragments.mapScreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gaiety.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.maxkeppeler.bottomsheets.calendar.CalendarMode
import com.maxkeppeler.bottomsheets.calendar.CalendarSheet
import com.maxkeppeler.bottomsheets.calendar.SelectionMode
import com.maxkeppeler.bottomsheets.core.BottomSheet
import com.maxkeppeler.bottomsheets.input.InputSheet
import com.maxkeppeler.bottomsheets.input.type.InputEditText
import com.maxkeppeler.bottomsheets.input.type.InputRadioButtons
import model.NetworkRequests
import java.util.*


class MapFragment : Fragment() {
    private val accessToken = "pk.eyJ1IjoibWVkdmVkaWF1cmFsYSIsImEiOiJja2pxeTVjc2YyM20wMnNtanFmaG5qaTgyIn0.v8tE-XOqrKjIYy6-qooV8g"
    lateinit var mapView :com.mapbox.mapboxsdk.maps.MapView
    private val networkRequests  = NetworkRequests()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { Mapbox.getInstance(it, accessToken) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
           mapboxMap.setStyle(Style.MAPBOX_STREETS) {
           }
            networkRequests.eventRequestMap(mapboxMap, "Москва")
        }

        val floatButton = view.findViewById<FloatingActionButton>(R.id.floatingActioButtonMap)
        floatButton.setOnClickListener() {
            onClickFloatingButton()
        }
    }

    public override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }


    private fun onClickFloatingButton() {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        val bottomSheetView = LayoutInflater.from(context)
            .inflate(
                R.layout.cities_map_radiobuttons,
                view?.findViewById(R.id.bottomSheetCityMapContainer)
            )

        val radioGroup = bottomSheetView?.findViewById<RadioGroup>(R.id.radioGroupMap)
        radioGroup?.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {

                when(p1){
                    R.id.radio_button_msc -> {
                        val position = CameraPosition.Builder()
                            .target(LatLng(	55.751417, 37.618107))
                            .zoom(9.0)
                            .build()
                        mapView.getMapAsync { mapboxMap ->
                            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                            }
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
                            networkRequests.eventRequestMap(mapboxMap, "Москва")
                        }
                    }
                    R.id.radio_button_sbp -> {
                        val position = CameraPosition.Builder()
                            .target(LatLng(	59.9386, 30.3141))
                            .zoom(9.0)
                            .build()
                        mapView.getMapAsync { mapboxMap ->
                            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                            }
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
                            networkRequests.eventRequestMap(mapboxMap, "Санкт-Петербург")
                        }
                    }
                    R.id.radio_button_nnd -> {
                        val position = CameraPosition.Builder()
                            .target(LatLng(	56.3287, 44.002))
                            .zoom(9.0)
                            .build()
                        mapView.getMapAsync { mapboxMap ->
                            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                            }
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
                            networkRequests.eventRequestMap(mapboxMap, "Нижний Новгород")
                        }
                    }
                    R.id.radio_button_ekb -> {
                        val position = CameraPosition.Builder()
                            .target(LatLng(	56.8519, 60.6122))
                            .zoom(9.0)
                            .build()
                        mapView.getMapAsync { mapboxMap ->
                            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                            }
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
                            networkRequests.eventRequestMap(mapboxMap, "Екатеринбург")
                        }
                    }
                    R.id.radio_button_knr -> {
                        val position = CameraPosition.Builder()
                            .target(LatLng(	45.0448, 38.976))
                            .zoom(9.0)
                            .build()
                        mapView.getMapAsync { mapboxMap ->
                            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                            }
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
                            networkRequests.eventRequestMap(mapboxMap, "Краснодар")
                        }
                    }
                }
                bottomSheetDialog?.dismiss()
            }
        })

        bottomSheetDialog?.setContentView(bottomSheetView)
        bottomSheetDialog?.show()
    }
}
