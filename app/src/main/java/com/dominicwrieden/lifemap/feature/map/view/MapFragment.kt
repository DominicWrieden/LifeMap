package com.dominicwrieden.lifemap.feature.map.view

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.BaseFragment
import com.dominicwrieden.lifemap.databinding.FragmentMapBinding
import com.dominicwrieden.lifemap.feature.map.model.MapContentState
import com.dominicwrieden.lifemap.feature.map.model.MapStates
import com.dominicwrieden.lifemap.feature.map.viewmodel.MapViewModel
import com.dominicwrieden.lifemap.util.observeWith
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.data.Geodatabase
import com.esri.arcgisruntime.geometry.CoordinateFormatter
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.LayerViewStatus
import com.esri.arcgisruntime.mapping.view.WrapAroundMode
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import kotlin.random.Random


class MapFragment : BaseFragment() {


    private val viewModel: MapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMapBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO id der Karte über den Intent holen (SafeArgs)

        //TODO über einen callback von der Karte Bescheid bekommen, dass die Items auf die Karte geprintet werden können
        viewModel.mapState.observeWith(this) { mapStates ->
            when (mapStates) {
                is MapStates.Init -> showMap(
                    mapStates.mapFile, savedInstanceState?.getParcelable(
                        SAVED_MAP_VIEW_BOUNDS
                    )
                )
                MapStates.Error -> {
                } //TODO show error
            }

        }
    }

    private fun showMap(mapFile: File, savedMapViewBounds: SavedMapViewBounds?) {
        val geoDatabase = Geodatabase(mapFile.path)

        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud1470224025,none,S080TK8EL9MD003AD149") //TODO auslagern

        map?.wrapAroundMode = WrapAroundMode.ENABLE_WHEN_SUPPORTED

        geoDatabase.loadAsync()
        geoDatabase.addDoneLoadingListener {
            if (geoDatabase.loadStatus === LoadStatus.LOADED) {

                println("TESTTEST: load map")

                map.map = ArcGISMap().apply {
                    geoDatabase.geodatabaseFeatureTables.reversed().forEach {
                        operationalLayers.add(FeatureLayer(it))
                    }

                    savedMapViewBounds?.let {
                        if (it.mapName == mapFile.name) {
                            println("TESTTEST: retrieve map viewpoint")
                            initialViewpoint = Viewpoint(it.latitude, it.longitude, it.scale)
                        }
                    }

                    loadSettings.preferredPolygonFeatureRenderingMode =
                        FeatureLayer.RenderingMode.DYNAMIC

                    maxScale = 500.0 //maximum scale to zoom in
                    minScale = 200000.0 //maximum scale to zoom out
                }
            }
        }

        viewModel.mapContentState.observeWith(this) { mapContentState ->
            when (mapContentState) {
                is MapContentState.ItemList -> {
                    val graphicsOverlay = GraphicsOverlay()
                    val rnd = Random(5)
                    mapContentState.items.forEach {
                        val color = Color.argb(
                            255,
                            rnd.nextInt(256),
                            rnd.nextInt(256),
                            rnd.nextInt(256)
                        )

                        val point = Point(
                            it.location.longitude,
                            it.location.latitude,
                            SpatialReferences.getWgs84()
                        )

                        val marker = SimpleMarkerSymbol(
                            SimpleMarkerSymbol.Style.CIRCLE,
                            color,
                            15f
                        )

                        val graphic = Graphic(point, marker)

                        graphicsOverlay.graphics.add(graphic)
                    }
                    map.graphicsOverlays.clear()
                    map.graphicsOverlays.add(graphicsOverlay)
                }
                MapContentState.Error -> {
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        map.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE)?.let { viewPoint ->
            if (viewModel.mapState.value is MapStates.Init) { //TODO bei jeder Veränderung des ViewPoints diesen speichern, anstatt nur hier

                println("TESTTEST: savedInstance")

                val centerPointLocation = getLocation(
                    viewPoint.targetGeometry.extent.center,
                    SAVED_MAP_VIEW_CENTER_POINT_ACCURACY
                )

                outState.putParcelable(
                    SAVED_MAP_VIEW_BOUNDS,
                    SavedMapViewBounds(
                        mapName = (viewModel.mapState.value as MapStates.Init).mapFile.name,
                        latitude = centerPointLocation.latitude,
                        longitude = centerPointLocation.longitude,
                        scale = viewPoint.targetScale
                    )
                )
            }
        }

        super.onSaveInstanceState(outState)
    }

    /**
     * Get the location from the center point, with a given decimal places
     *
     * 0 decimal accuracy: 100km
     * 1 decimal accuracy: 10km
     * 2 decimal accuracy: 1km
     * 3 decimal accuracy: 100m
     * 4 decimal accuracy: 10m
     * 5 decimal accuracy: 1m
     * 6 decimal accuracy: 0.1m
     *
     */
    private fun getLocation(centerPoint: Point, decimalPlaces: Int): Location {

        //format: 12.3456N 65.4321E
        val coordinateString = CoordinateFormatter.toLatitudeLongitude(
            centerPoint,
            CoordinateFormatter.LatitudeLongitudeFormat.DECIMAL_DEGREES, decimalPlaces
        )

        val regex = Regex("[^0-9 .]")

        val latitudeLongitudeStrings = regex.replace(coordinateString, "").split(" ")


        return Location("").apply {
            latitude = latitudeLongitudeStrings[0].toDouble()
            longitude = latitudeLongitudeStrings[1].toDouble()
        }

    }

    override fun onPause() {
        map?.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        map?.resume()
    }

    override fun onDestroy() {
        map?.dispose()
        super.onDestroy()
    }


    companion object {
        private const val SAVED_MAP_VIEW_BOUNDS = "SAVED_MAP_VIEW_BOUNDS"
        private const val SAVED_MAP_VIEW_CENTER_POINT_ACCURACY = 6 // decimal places
    }


    private fun loadMapWithImagery(
        mapFile: File,
        geoDatabase: Geodatabase,
        savedMapViewBounds: SavedMapViewBounds?
    ) {
        geoDatabase.loadAsync()
        geoDatabase.addDoneLoadingListener {
            if (geoDatabase.loadStatus === LoadStatus.LOADED) {

                val features: ArrayList<FeatureLayer> = arrayListOf()

                map.map = ArcGISMap().apply {
                    geoDatabase.geodatabaseFeatureTables.reversed().forEach {
                        val feature = FeatureLayer(it)

                        features.add(feature.copy())

                        operationalLayers.add(feature)
                        if (geoDatabase.geodatabaseFeatureTables.indexOf(it) == geoDatabase.geodatabaseFeatureTables.lastIndex) {
                            feature.addDoneLoadingListener {
                                if (feature.loadStatus == LoadStatus.LOADED) {
                                    //TODO what was the purpose of this?
                                    //point = feature.fullExtent.center
                                }
                            }
                        }
                    }

                    savedMapViewBounds?.let {
                        if (it.mapName == mapFile.name) {
                            initialViewpoint = Viewpoint(it.latitude, it.longitude, it.scale)
                        }
                    }

                    loadSettings.preferredPolygonFeatureRenderingMode =
                        FeatureLayer.RenderingMode.DYNAMIC

                    maxScale = 500.0 //maximum scale to zoom in
                    minScale = 200000.0 //maximum scale to zoom out
                }

                var first = true
                map.addLayerViewStateChangedListener { event ->
                    if (event.layerViewStatus.all { it == LayerViewStatus.ACTIVE }) {
                        if (first) {
                            first = false

                            val newMap = ArcGISMap(Basemap.createImagery()).apply {
                                initialViewpoint =
                                    map.getCurrentViewpoint(Viewpoint.Type.BOUNDING_GEOMETRY)
                                operationalLayers.addAll(features)
                            }

                            map.map = newMap
                        }

                    }
                }
            }
        }
    }
}

@Parcelize
private data class SavedMapViewBounds(
    val mapName: String,
    val latitude: Double,
    val longitude: Double,
    val scale: Double
) : Parcelable