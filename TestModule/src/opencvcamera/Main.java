package opencvcamera;

import java.awt.*;

public class Main {


    // https://www.youtube.com/watch?v=NUQc7-dYIxA&list=PLsjTcuj_fDEYXKcZ1KCZWILnVsQDFJZrn&index=2
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("load success");


        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CameraFrame cameraFrame = new CameraFrame();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        cameraFrame.startCamera();
                    }
                }).start();

            }
        });
    }

}