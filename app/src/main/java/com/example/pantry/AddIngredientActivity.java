package com.example.pantry;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.camera.CameraSourceConfig;
import com.google.mlkit.vision.camera.CameraXSource;
import com.google.mlkit.vision.camera.DetectionTaskCallback;
import com.google.mlkit.vision.common.InputImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddIngredientActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSourceConfig cameraSourceConfig;
    TextView textView;
    TextView pantryItemPreview;
    ImageView imageView;
    BarcodeScanner barcodeScanner;
    InputImage inputImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // Find views
        surfaceView = findViewById(R.id.cameraPreview);
        textView = findViewById(R.id.textView);
        pantryItemPreview = findViewById(R.id.pantryItemPreview);
        imageView = findViewById(R.id.imageView);

        BarcodeScannerOptions options = new BarcodeScannerOptions
                .Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();
//        inputImage = null;

        barcodeScanner = BarcodeScanning.getClient();

        cameraSourceConfig = new CameraSourceConfig.Builder(this, barcodeScanner, new DetectionTaskCallback<List<Barcode>>() {
            @Override
            public void onDetectionTaskReceived(@NonNull Task<List<Barcode>> task) {
                List<Barcode> barcodes = task.getResult();
                if(barcodes.isEmpty()){
                    return;
                }
                for(int i=0; i< barcodes.size(); i++){
                    Barcode curBarcode = barcodes.get(i);
                    Toast.makeText(AddIngredientActivity.this, curBarcode.getRawValue(), Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setRequestedPreviewSize(720, 480)
                .build();
        CameraXSource cameraXSource = new CameraXSource(cameraSourceConfig);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder){
                if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    textView.setText("Permission to use camera not granted");
                    return;
                }
                try{
                    cameraXSource.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {cameraXSource.stop();}
        });
    }
}