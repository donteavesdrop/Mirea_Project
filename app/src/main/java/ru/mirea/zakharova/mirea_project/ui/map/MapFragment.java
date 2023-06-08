package ru.mirea.zakharova.mirea_project.ui.map;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.zakharova.mirea_project.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {
    private MapView mapView = null;
    private TextView textView;
    private boolean isWork = false;
    private FragmentMapBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = binding.mapView;
        textView = binding.text1;
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));

        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        int loc1PermissionStatus = requireContext().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2PermissionStatus = requireContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (loc1PermissionStatus == PackageManager.PERMISSION_GRANTED && loc2PermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (isWork) {
            MyLocationNewOverlay locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
            locationNewOverlay.enableMyLocation();
            mapView.getOverlays().add(locationNewOverlay);

            locationNewOverlay.runOnFirstFix(() -> {
                try {
                    getActivity().runOnUiThread(() -> {
                        IMapController mapController = mapView.getController();
                        mapController.setZoom(5.0);
                        GeoPoint startPoint = new GeoPoint(55.75356, 37.62139);
                        mapController.setCenter(startPoint);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        CompassOverlay compassOverlay = new CompassOverlay(requireContext(), new InternalCompassOrientationProvider(requireContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(mapView.getContext().getResources().getDisplayMetrics().widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(55.710246, 37.5991637));
        marker.setOnMarkerClickListener((marker1, mapView) -> {
            Toast.makeText(mapView.getContext(), "Moscow - Yotaspace", Toast.LENGTH_SHORT).show();
            textView.setText("A wood-finished concert venue with comfortable sofas, where musicians from all over the world perform.");
            return true;
        });
        mapView.getOverlays().add(marker);
        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker.setTitle("Moscow - Yotaspace");

        Marker marker2 = new Marker(mapView);
        marker2.setPosition(new GeoPoint(59.9139778, 30.3239248));
        marker2.setOnMarkerClickListener((marker21, mapView) -> {
            Toast.makeText(mapView.getContext(), "St. Petersburg - Cosmonaut", Toast.LENGTH_SHORT).show();
            textView.setText("A modern two-level rock-club with a dance floor for 1500 people, as well as a VIP-zone and a restaurant on the second floor.");
            return true;
        });
        mapView.getOverlays().add(marker2);
        marker2.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker2.setTitle("St. Petersburg - Cosmonaut");

    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
}
