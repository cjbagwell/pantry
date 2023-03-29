package com.example.pantry;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.camera.CameraSourceConfig;
import com.google.mlkit.vision.camera.CameraXSource;
import com.google.mlkit.vision.camera.DetectionTaskCallback;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class AddIngredientActivity extends AppCompatActivity implements PantryCommunicatorCallback, AddIngredientPopUpDialog.PopUpDialogListener {

    CameraSourceConfig cameraSourceConfig;
    TextView textView;
    BarcodeScanner barcodeScanner;
    BarcodeScannerOptions barcodeScannerOptions;
    PreviewView previewView;
    PantryManager manager;
    CameraXSource cameraXSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        // Create variables
        manager = new PantryManager(this);
        // Find views
        previewView = findViewById(R.id.preview_view);
        barcodeScannerOptions = new BarcodeScannerOptions
                .Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();

        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);

        cameraSourceConfig = new CameraSourceConfig.Builder(this, barcodeScanner, new DetectionTaskCallback<List<Barcode>>() {
            @Override
            public void onDetectionTaskReceived(@NonNull Task<List<Barcode>> task) {
                task = barcodeScanner.process(InputImage.fromBitmap(previewView.getBitmap(), 0));
                task.addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        if (barcodes.isEmpty()) {
                            return;
                        }
                        barcodeScanner.close();
                        cameraXSource.stop();
                        PantryCommunicator communicator = new PantryCommunicator();//.setInstance(AddIngredientActivity.this);
                        communicator.setInstance(AddIngredientActivity.this);
//                        communicator.execute("049000000443"); // Cocacola bottle upc for testing
//                        communicator.execute("078742351865"); // Milk upc for testing
                        communicator.execute(barcodes.get(0).getRawValue());
                        Toast.makeText(AddIngredientActivity.this, "We are sending a result", Toast.LENGTH_SHORT);
                    }
                });
            }//onTaskDetectionReceived
        })//cameraSourceConfig.Builder
                .setRequestedPreviewSize(720, 480)
                .setFacing(CameraSourceConfig.CAMERA_FACING_BACK)
                .build();
        cameraXSource = new CameraXSource(cameraSourceConfig, previewView);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            textView.setText("Permission to use camera not granted");
            return;
        }
        try {
            cameraXSource.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//onCreate

    @Override
    public void setResult(Ingredient result) {
        // If the ingredient is not in the database, restart the camera and present a message
        if (result == null) {
            Toast.makeText(this, "Ingredient does not exist in the online database :(", Toast.LENGTH_LONG).show();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);
            cameraXSource.start();
            return;
        }
        Toast.makeText(this, "We got a result", Toast.LENGTH_LONG).show();
        openPopUpDialog(result);
    }

    public void openPopUpDialog(Ingredient ingredient) {
        Bundle bundle = new Bundle();
        bundle.putString("barcode", ingredient.getBarcode());

        AddIngredientPopUpDialog popUpDialog = new AddIngredientPopUpDialog();
        popUpDialog.setArguments(bundle);
        popUpDialog.show(getSupportFragmentManager(), "pop up dialog");
    }

    @Override
    public void getPopUpResult(String barcode, int mQuantityToChange) {
        manager.addToPantry(barcode, mQuantityToChange);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);
        cameraXSource.start();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    @Override
    protected void onDestroy() {
        cameraXSource.close();
        cameraXSource.stop();
        barcodeScanner.close();

        super.onDestroy();
    }
    @Override
    public void onBackPressed(){
        barcodeScanner.close();
        super.onBackPressed();
    }

}