package com.dominicwrieden.lifemap.feature.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.BaseFragment
import com.dominicwrieden.lifemap.databinding.FragmentMapBinding
import com.dominicwrieden.lifemap.feature.map.viewmodel.MapViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment() {

    private val viewModel: MapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentMapBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        return binding.root
    }

/* TODO 2022.02.25: ArcGis needs to be fixed.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO id der Karte über den Intent holen (SafeArgs)

        //TODO über einen callback von der Karte Bescheid bekommen, dass die Items auf die Karte geprintet werden können
        viewModel.mapState.observeWith(this) { mapStates ->
            when (mapStates) {
                is MapStates.Init -> showMap(
                    mapStates.mapFile
                )
                MapStates.Error -> {
                } //TODO show error
            }

        }
    }


     override fun onPause() {
        super.onPause()
        map.pause()
    }


     override fun onResume() {
        super.onResume()
         map.unpause()
    }

    private fun showMap(mapFile: File) {

        //create a new GraphicsLayer

        //create a new GraphicsLayer
        val gLayer = GraphicsLayer(GraphicsLayer.RenderingMode.STATIC)
        //and added it to the MapView
        //and added it to the MapView
        map.addLayer(gLayer)
        map.enableWrapAround(true);

        map.setMapOptions(MapOptions(MapOptions.MapType.GRAY))


        val geoDatabase = Geodatabase(mapFile.path)

        //get the GeodatabaseFeatureTable from the Geodatabase

        //get the GeodatabaseFeatureTable from the Geodatabase
        val gdbFeatureTables: List<GeodatabaseFeatureTable> = geoDatabase.getGeodatabaseTables()
        val length = gdbFeatureTables.size

        //forwards
        //draw the background first

        //forwards
        //draw the background first
        var flurstueckCount = 0
        for (count in 0 until length) {
            val ft: FeatureTable = gdbFeatureTables[count]
            val fl = FeatureLayer(ft)
            val tableName = ft.tableName.toLowerCase()
            if (tableName.contains("flurstuecke") || tableName.contains("flurstücke") || tableName.contains(
                    "flurstcke"
                )
            ) {
                flurstueckCount = count
            }
        }

        if (flurstueckCount > length / 2) {
            for (count in length - 1 downTo 0) {
                val ft: FeatureTable = gdbFeatureTables[count]
                val fl = FeatureLayer(ft)
                if (ft.tableName.toLowerCase().contains("background")) {
                    map.addLayer(fl)
                }
            }
            for (count in length - 1 downTo 0) {
                val ft: FeatureTable = gdbFeatureTables[count]
                val fl = FeatureLayer(ft)
                if (!ft.tableName.toLowerCase().contains("background")) {
                    map.addLayer(fl)
                }
            }
        } else {
            for (count in 0 until length) {
                val ft: FeatureTable = gdbFeatureTables[count]
                val fl = FeatureLayer(ft)
                if (ft.tableName.toLowerCase() == "background") {
                    map.addLayer(fl)
                }
            }
            for (count in 0 until length) {
                val ft: FeatureTable = gdbFeatureTables[count]
                val fl = FeatureLayer(ft)
                if (ft.tableName.toLowerCase() != "background") {
                    map.addLayer(fl)
                }
            }
        }

 */


/*
                println("TESTTEST: load map")

                    geoDatabase.geodatabaseTables.reversed().forEach {
                        map.addLayer(FeatureLayer(it))
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
        map?.apply {
            click
        }
        }
        */
}
