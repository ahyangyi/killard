package com.killard.web.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Composite;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.killard.web.BasicController;
import com.killard.web.PersistenceHelper;
import com.killard.jdo.card.CardDO;
import com.killard.jdo.card.ElementSchoolDO;
import com.killard.jdo.card.PackageDO;
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
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/image/card/*.*", method = RequestMethod.GET)
    public void cardImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] args = request.getRequestURI().split("/");
        String imageName = args[args.length - 1];
        String[] fileName = imageName.split("_");
        long packageId = Long.parseLong(fileName[0]);
        long elementSchoolId = Long.parseLong(fileName[1]);
        long cardId = Long.parseLong(fileName[2].split("\\.")[0]);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, CardDO.class.getSimpleName(), cardId);
        CardDO card = pm.getObjectById(CardDO.class, cardKey);
        response.setContentType("image/png");
        if (card.getDescriptor() != null && card.getDescriptor().getImageData() != null) {
            byte[] data = card.getDescriptor().getImageData();
            ImagesService imagesService = ImagesServiceFactory.getImagesService();
            Image oldImage = ImagesServiceFactory.makeImage(data);
            Transform resize = ImagesServiceFactory.makeResize(171, 264);
            Image cardImage = imagesService.applyTransform(resize, oldImage);

            Composite cardComp = ImagesServiceFactory.makeComposite(cardImage, 0, 0, 1f, Composite.Anchor.TOP_LEFT);
            Composite levelComp =
                    ImagesServiceFactory
                            .makeComposite(makeNumberImage(card.getLevel(), request), 0, 0, 1f, Composite.Anchor.TOP_RIGHT);
            Composite healthComp =
                    ImagesServiceFactory
                            .makeComposite(makeNumberImage(card.getHealth(), request), 0, 0, 1f, Composite.Anchor.BOTTOM_LEFT);
            Composite attackComp =
                    ImagesServiceFactory.makeComposite(makeNumberImage(card.getAttackValue(), request), 0, 0, 1f,
                            Composite.Anchor.BOTTOM_RIGHT);

            List<Composite> composites = new ArrayList<Composite>(4);
            composites.add(cardComp);
            composites.add(levelComp);
            composites.add(healthComp);
            composites.add(attackComp);

            Image finalImage = imagesService.composite(composites, 171, 264, 0, ImagesService.OutputEncoding.PNG);

            response.getOutputStream().write(finalImage.getImageData());
        } else {
            throw new IOException("This card has no image.");
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
