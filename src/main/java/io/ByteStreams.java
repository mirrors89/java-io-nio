package io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ByteStreams {
    public static void main(String[] args) throws IOException {
        // 700 × 454 이미지
        BufferedInputStream imageInputStream = new BufferedInputStream(new FileInputStream("src/main/resources/lion.jpeg"));

        InputStream inputStream = resizeImage(imageInputStream, 1000, 700);
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/lion_copy.jpeg");

        byte[] readBuffer = new byte[1024];

        while (inputStream.read(readBuffer, 0, readBuffer.length) != -1)
            outputStream.write(readBuffer);

        inputStream.close();
        outputStream.close();
    }

    public static InputStream resizeImage(InputStream inputStream, int width, int height) throws IOException {

        BufferedImage sourceImage = ImageIO.read(inputStream);

        Image thumbnail = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
                thumbnail.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

        bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedThumbnail, "jpeg", baos);

        return new ByteArrayInputStream(baos.toByteArray());
    }
}
