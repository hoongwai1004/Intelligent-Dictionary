package com.cat300.ty_ch.intelligentdictionary;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cat300.ty_ch.intelligentdictionary.helper.GraphicOverlay;
import com.cat300.ty_ch.intelligentdictionary.helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CameraActivity extends AppCompatActivity {

    CameraView cameraView;
    android.app.AlertDialog waitingDialog;
    GraphicOverlay graphicOverlay;
    Button button_capture;

    RecyclerView recyclerView;
    TextSelectionAdapter textSelectionAdapter;
    private List<String> textSelectionData = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        waitingDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setMessage("Please wait...")
                .setContext(this)
                .build();
        cameraView = (CameraView)findViewById(R.id.camera_view);
        graphicOverlay = (GraphicOverlay)findViewById(R.id.graphic_overlay);

        button_capture = (Button)findViewById(R.id.button_capture);
        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSelectionData.clear();
                textSelectionAdapter.notifyDataSetChanged();
                cameraView.start();
                cameraView.captureImage();
                graphicOverlay.clear();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.text_selection_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        textSelectionAdapter = new TextSelectionAdapter(textSelectionData, this);
        recyclerView.setAdapter(textSelectionAdapter);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                waitingDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                recognizeText(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void recognizeText(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionCloudTextRecognizerOptions options =
                new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("en"))
                .build();
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getCloudTextRecognizer(options);
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                textSelectionData.clear();
                textSelectionAdapter.notifyDataSetChanged();
                drawTextResult(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ERROR", e.getMessage());
                return;
            }
        });
    }

    private void drawTextResult(final FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();
        if(blocks.size() == 0) {
            Toast.makeText(this, "No Text Found", Toast.LENGTH_SHORT).show();
            return;
        }
        graphicOverlay.clear();
        for(int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                final List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for(int k = 0; k < elements.size(); k++) {
                    final TextGraphic textGraphic = new TextGraphic(graphicOverlay, elements.get(k));
                    //Log.d("SUCCESS", elements.get(k).getText());
                    textSelectionData.add(elements.get(k).getText());
                    //Log.d("COPIED", textSelectionData.get(k));
                    graphicOverlay.add(textGraphic);
                }
            }
        }
        waitingDialog.dismiss();
    }
}
