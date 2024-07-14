/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author aldes
 */
public class ImageHelper {

    public static String uploadDirectory = "uploaded";

    public static BufferedImage scaleImage(int width, int height, BufferedImage image) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);

        Graphics2D g2d = (Graphics2D) bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        return bufferedImage;
    }

    public static BufferedImage scaleImageMaxHeight(int maxHeight, File file) throws Exception {
        BufferedImage originalImage = ImageIO.read(file);
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        int newWidth = originalWidth;
        int newHeight = originalHeight;

        if (originalHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        return scaleImage(newWidth, newHeight, originalImage);
    }

    public static String uploadImage(String directory, File file) throws Exception {
        // Generate random name
        String fileName = UUID.randomUUID().toString();

        return uploadImage(directory, file, fileName);
    }

    public static String uploadImage(String directory, File file, String fileName) throws Exception {
        String fileExtension = getFileExtension(file);

        try {
            File destinationDir = new File(directory);

            if (!destinationDir.exists()) {
                destinationDir.mkdirs();
            }

            File destinationFile = new File(destinationDir, fileName + fileExtension);
            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return directory + File.separator + fileName + fileExtension;
        } catch (IOException ex) {
            ex.printStackTrace();

            throw ex;
        }
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOfDot);
    }
}
