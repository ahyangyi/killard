package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@Controller
public class ImageController extends BasicController {

    @RequestMapping(value = "/package/*/*/*/image.png", method = RequestMethod.GET)
    public void cardImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[2]);
        String elementSchoolName = ids[3];
        String cardName = ids[4];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        response.setContentType("image/png");
        if (card.isRenderable()) {
            byte[] data = card.getImageData();
//            ImagesService imagesService = ImagesServiceFactory.getImagesService();
//            Image oldImage = ImagesServiceFactory.makeImage(data);
//            Transform resize = ImagesServiceFactory.makeResize(171, 264);
//            Image cardImage = imagesService.applyTransform(resize, oldImage);

            try {
                response.getOutputStream().write(data);
            } catch (IOException ignored) {
            }
        } else {
            throw new IOException("This board has no image.");
        }
    }

    public Image makeNumberImage(int number, HttpServletRequest request) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(request.getRealPath("/WEB-INF/card/" + String.valueOf(number) + ".png"));
        byte[] data = new byte[256];
        int length;
        try {
            while ((length = in.read(data)) > 0) {
                buf.write(data, 0, length);
            }
        } finally {
            in.close();
        }
        return ImagesServiceFactory.makeImage(buf.toByteArray());
    }

}
