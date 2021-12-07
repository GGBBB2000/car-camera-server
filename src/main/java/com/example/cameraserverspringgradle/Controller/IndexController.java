package com.example.cameraserverspringgradle.Controller;

import com.example.cameraserverspringgradle.Service.Recorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;


@RestController
public class IndexController {
    private final Recorder recorder;

    @Autowired
    public IndexController(Recorder recorder) {
        this.recorder = recorder;
    }

    @RequestMapping("/recorder/start")
    public void start() {
        final var calender = Calendar.getInstance();
        final var format = new SimpleDateFormat("yyyyMMdd-HH_mm_ss");
        recorder.startRecording(format.format(calender.getTime()) + ".mov");
    }

    @RequestMapping("/recorder/stop")
    public void stop() {
        recorder.stopRecording();
    }

    @RequestMapping("/recorder/is_recording")
    boolean getRecordingStatus() {
       return recorder.getStatus();
    }
}
