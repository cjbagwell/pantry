package com.example.pantry;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;

import androidx.annotation.GuardedBy;
import androidx.arch.core.executor.TaskExecutor;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.odml.image.ByteBufferMlImageBuilder;
import com.google.android.odml.image.MlImage;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.common.InputImage;

import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

// NOTE: The example code that I got this from implements interface VisionImageProcessor
public  abstract class VisionProcessorBase<T>{

    protected static final String MANUAL_TESTING_LOG = "LogTagForTest";
    private static final String TAG = "VisionProcessorBase";

//    private final ActivityManager activityManager; //TODO: implement this for debugging
    private final ScopedExecutor executor;

    // Whether this processor is already shut down
    private boolean isShutdown;

    // Used to calculate latency, running in the same thread, no sync needed.
    private int numRuns = 0;
    private long totalFrameMs = 0;
    private long maxFrameMs = 0;
    private long minFrameMs = Long.MAX_VALUE;
    private long totalDetectorMs = 0;
    private long maxDetectorMs = 0;
    private long minDetectorMs = Long.MAX_VALUE;

    // To keep the latest images and its metadata.
    @GuardedBy("this")
    private ByteBuffer latestImage;
    @GuardedBy("this")
    private FrameMetadata latestImageMetaData;
    // To keep the images and metadata in process.
    @GuardedBy("this")
    private ByteBuffer processingImage;

    @GuardedBy("this")
    private FrameMetadata processingMetaData;

    public VisionProcessorBase(Context context) {
//        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        executor = new ScopedExecutor(TaskExecutors.MAIN_THREAD);
        // TODO: implement fps timer and temp monitor if desired
//        fpsTimer.scheduleAtFixedRate(
//                new TimerTask() {
//                    @Override
//                    public void run() {
//                        framesPerSecond = frameProcessedInOneSecondInterval;
//                        frameProcessedInOneSecondInterval = 0;
//                    }
//                },
//                /* delay= */ 0,
//                /* period= */ 1000);
//        temperatureMonitor = new TemperatureMonitor(context);
    }

//    void processBitmap(Bitmap bitmap, GraphicOverlay graphicOverlay){
//
//    };

    // -----------------Code for processing live preview frame from Camera1 API-----------------------
    public synchronized void processByteBuffer(
            ByteBuffer data, final FrameMetadata frameMetadata, final GraphicOverlay graphicOverlay) {
        latestImage = data;
        latestImageMetaData = frameMetadata;
        if (processingImage == null && processingMetaData == null) {
            processLatestImage(graphicOverlay);
        }
    }

    private synchronized void processLatestImage(final GraphicOverlay graphicOverlay) {
        processingImage = latestImage;
        processingMetaData = latestImageMetaData;
        latestImage = null;
        latestImageMetaData = null;
        if (processingImage != null && processingMetaData != null && !isShutdown) {
            processImage(processingImage, processingMetaData, graphicOverlay);
        }
    }

    private void processImage(
            ByteBuffer data, final FrameMetadata frameMetadata, final GraphicOverlay graphicOverlay) {
        long frameStartMs = SystemClock.elapsedRealtime();

        // If live viewport is on (that is the underneath surface view takes care of the camera preview
        // drawing), skip the unnecessary bitmap creation that used for the manual preview drawing.
        Bitmap bitmap =
                PreferenceUtils.isCameraLiveViewportEnabled(graphicOverlay.getContext())
                        ? null
                        : BitmapUtils.getBitmap(data, frameMetadata);

        if (isMlImageEnabled(graphicOverlay.getContext())) {
            MlImage mlImage =
                    new ByteBufferMlImageBuilder(
                            data,
                            frameMetadata.getWidth(),
                            frameMetadata.getHeight(),
                            MlImage.IMAGE_FORMAT_NV21)
                            .setRotation(frameMetadata.getRotation())
                            .build();

            requestDetectInImage(mlImage, graphicOverlay, bitmap, /* shouldShowFps= */ true, frameStartMs)
                    .addOnSuccessListener(executor, results -> processLatestImage(graphicOverlay));

            // This is optional. Java Garbage collection can also close it eventually.
            mlImage.close();
            return;
        }

        requestDetectInImage(
                InputImage.fromByteBuffer(
                        data,
                        frameMetadata.getWidth(),
                        frameMetadata.getHeight(),
                        frameMetadata.getRotation(),
                        InputImage.IMAGE_FORMAT_NV21),
                graphicOverlay,
                bitmap,
                /* shouldShowFps= */ true,
                frameStartMs)
                .addOnSuccessListener(executor, results -> processLatestImage(graphicOverlay));
    }



    /** Stops the underlying machine learning model and release resources. */
    void stop(){

    };
}
