/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.helper;

import java.io.File;
import main.model.Model;
import main.util.ImageHelper;

/**
 *
 * @author aldes
 */
public class HandleImageUpload {
    private Model model;
    private File imageFile;
    private ImageUploadedEvent event;

    public HandleImageUpload(Model model, File imageFile, ImageUploadedEvent event) {
        this.model = model;
        this.imageFile = imageFile;
        this.event = event;
    }
    
    public void upload() throws Exception{
        String className = model.getClass().getSimpleName().toLowerCase();
        String uploadDir = ImageHelper.uploadDirectory + File.separator + className;
        try {
            String uploadedFile = ImageHelper.uploadImage(uploadDir, imageFile);
            event.onUploaded(uploadedFile);
        } catch (Exception e) {
            e.printStackTrace();
            
            throw e;
        }
    }
}
