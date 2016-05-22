package ua.softserveinc.tc.controller;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Gender;
import ua.softserveinc.tc.service.ChildService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.sql.Blob;

/**
 * Created by Admin on 22.05.2016.
 */

@Controller
public class ImagesController {
    @Autowired
    private ChildService childService;

    @RequestMapping(value = "/uploadImage/{kidId}", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long kidId){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Child kid = childService.findById(kidId);
                kid.setImage(bytes);
                childService.update(kid);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return "redirect:/" + MyKidsConst.KID_PROFILE_VIEW + "?id=" + kidId;
    }

    @RequestMapping(value = "/images/{kidId}",
            produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public byte[] getProfilePic(@PathVariable Long kidId) throws IOException{
        Child kid = childService.findById(kidId);
        if(kid.getImage()!= null) return kid.getImage();

        String path;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if(kid.getGender() == Gender.FEMALE) {
             path = classloader.getResource("images/default-girl.jpg").getFile();
        }
        else{
            path = classloader.getResource("images/default-boy.jpg").getFile();
        }

        File imgPath = new File(path);

        BufferedImage bufferedImage = ImageIO.read(imgPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        ImageIO.write(bufferedImage, "jpg", baos);
        baos.flush();
        String base64String= Base64.encode(baos.toByteArray());
        baos.close();

        return Base64.decode(base64String);

    }
}
