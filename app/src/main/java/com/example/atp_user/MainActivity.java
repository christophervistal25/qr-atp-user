package com.example.atp_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.atp_user.Fragments.IdentificationFragment;
import com.example.atp_user.Fragments.QRScannerFragment;
import com.example.atp_user.Fragments.TrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new IdentificationFragment())
                .commit();


        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_idenification:
                    openFragment(IdentificationFragment.newInstance("", ""));
                    return true;
                case R.id.navigation_sms:
                    openFragment(QRScannerFragment.newInstance("", ""));
                    return true;

                case R.id.navigation_track:
                    openFragment(TrackFragment.newInstance("", ""));
                    return true;

            }
            return false;
        });



//        askPermissionForCamera();
//        CodeScannerView scannerView = findViewById(R.id.scanner_view);
//        mCodeScanner = new CodeScanner(getApplicationContext(), scannerView);
//        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
//            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
//        }));

//        Dexter.withContext(this)
//                .withPermission(Manifest.permission.CAMERA)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        CodeScannerView scannerView = findViewById(R.id.scanner_view);
//                        mCodeScanner = new CodeScanner(getApplicationContext(), scannerView);
//                        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
//                            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
//                        }));
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        // check for permanent denial of permission
//                        if (response.isPermanentlyDenied()) {
//                            // navigate user to app settings
//                            showSettingsDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();

    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void askPermissionForCamera() {
        // Have access for camera to run the QR Code Scanner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
//        mCodeScanner.releaseResources();
        super.onPause();
    }

}