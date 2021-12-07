package com.example.cameraserverspringgradle.Service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Arrays;

@Service
public class Recorder {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // DO NOT DELETE THIS LINE!
    }

    private final VideoCapture videoCapture;
    private boolean isRecording;
    private boolean cameraReleased;

    public Recorder() {
        this.videoCapture = new VideoCapture(0);
        this.videoCapture.set(Videoio.CAP_PROP_FPS, 30);
        this.videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        this.videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        this.isRecording = false;
        this.cameraReleased = true;
    }

    @Async
    public void startRecording(final String fileName) {

        while (!this.cameraReleased) {
            // カメラがリリースされるのを待つ
            this.stopRecording();
        }
        new Thread(() -> {
            final Size frameSize = new Size(480, 640);
            final var videoWriter = new VideoWriter(fileName, VideoWriter.fourcc('m', 'p', '4', 'v'), 30, frameSize, true);

            this.isRecording = true;
            this.cameraReleased = false;
            Mat frame = new Mat();
            while (this.isRecording) {
                if (this.videoCapture.read(frame)) {
                    Mat rotated = new Mat();
                    Core.rotate(frame, rotated, Core.ROTATE_90_COUNTERCLOCKWISE);
                    videoWriter.write(rotated);
                    rotated.release();
                }
            }
            frame.release();
            videoWriter.release();
            this.cameraReleased = true;
        }).start();
    }

    public void stopRecording() {
        this.isRecording = false;
    }

    public boolean getStatus() {
        return this.isRecording;
    }
}
