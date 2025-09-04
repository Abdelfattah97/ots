package com.github.ots;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class QrTestMain {
    public static void main(String[] args) {

        ByteArrayOutputStream byteStream = QRCode.from("Don't use Chatgpt!")
                .withSize(500, 500)
                .to(ImageType.PNG)
                .stream();
        File file = new File("new file path here");

        String str ;

        String[] arr =new String[1];

        try(FileOutputStream os = new FileOutputStream(file)) {
            os.write(byteStream.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
