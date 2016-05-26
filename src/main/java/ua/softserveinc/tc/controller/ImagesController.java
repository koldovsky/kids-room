package ua.softserveinc.tc.controller;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.InternalError;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Gender;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.security.Principal;
import java.sql.Blob;

/**
 * Created by Nestor on 22.05.2016.
 *
 * Controller handles all images-related work
 * Such as: request for images, uploads of new images
 */

@Controller
public class ImagesController {
    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    /**
     * Uploading a new profile picture for a Child
     * @param file a MultipartFile that is being uploaded
     * @param kidId ID of a Child
     * @return redirects back to kid's profile view
     */
    @RequestMapping(value = "/uploadImage/{kidId}", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String kidId){
        Long id = Long.parseLong(kidId);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Child kid = childService.findById(id);
                if(kid == null){
                    throw new ResourceNotFoundException();
                }
                kid.setImage(bytes);
                childService.update(kid);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return "redirect:/" + MyKidsConst.KID_PROFILE_VIEW + "?id=" + kidId;
    }

    /**
     * Returns profile pictures to client
     * @param kidId
     * @param principal
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/images/{kidId}",
            produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public byte[] getProfilePic(@PathVariable String kidId, Principal principal)
            throws IOException{
        Long id;
        try {
            id = Long.parseLong(kidId);
        } catch(Exception e){
            //invalid parameter
            throw new ResourceNotFoundException();
        }

        Child kid = childService.findById(Long.parseLong(kidId));
        if(kid == null){
            throw new ResourceNotFoundException();
        }

        User current = userService.getUserByEmail(principal.getName());
        if(current.getRole() != Role.MANAGER && !current.equals(kid.getParentId())) {
            throw new AccessDeniedException("Have to be manager or parent");
        }
        if(kid.getImage()!= null) return kid.getImage();

        String path;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if(kid.getGender() == Gender.FEMALE)
             path = classloader.getResource("images/default-girl.jpg").getFile();
        else
            path = classloader.getResource("images/default-boy.jpg").getFile();

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
