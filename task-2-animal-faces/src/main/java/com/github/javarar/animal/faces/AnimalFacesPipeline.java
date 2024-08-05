package com.github.javarar.animal.faces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class AnimalFacesPipeline {

    private final String resourcePath = "C:\\Users\\babki\\Desktop\\train\\cat";

    private final String resultPath = "C:\\Users\\babki\\Desktop\\rotated-photos";

    //Время выполнения в один поток - 68427ms
    public void download() throws IOException {
        File[] photos = new File(resourcePath).listFiles();
        for (File photo : photos) {
            rotateAndSave(photo);
        }
    }

    //Время выполнения на CachedThreadPool - 15063ms
    public void downloadAsync(ExecutorService executor) throws InterruptedException {
        File[] photos = new File(resourcePath).listFiles();
        if (photos == null) return;
        final CountDownLatch latch = new CountDownLatch(photos.length);
        for (File l : photos) {
            executor.submit(() -> {
                try {
                    rotateAndSave(l);
                } catch (IOException e) {
                    System.out.println("Ошибка обработки изображения");
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    private void rotateAndSave(File photo) throws IOException {
        final BufferedImage image = ImageIO.read(photo);
        BufferedImage rotatedImage = rotate(image, 90);
        var name = resultPath + "\\rotated_" + photo.getName();
        ImageIO.write(rotatedImage, "jpg", new File(name));
    }

    public static BufferedImage rotate(BufferedImage bimg, double angle) {
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        return rotated;
    }

}
