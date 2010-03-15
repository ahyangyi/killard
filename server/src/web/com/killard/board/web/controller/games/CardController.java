package com.killard.board.web.controller.games;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.parser.ScriptEngine;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
public class CardController extends BasicController {

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

    @RequestMapping(value = "/*/*/*/*/", method = RequestMethod.GET)
    public String viewCard(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        String bundleName = ids[2];
        Long packageId = Long.parseLong(ids[3]);
        String elementSchoolName = ids[4];
        String cardName = ids[5];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleName);
        Key packageKey = KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        modelMap.put("card", card);
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", elementSchool);
        return "card/view";
    }

    @RequestMapping(value = {"/*/*/*/*.png"}, method = RequestMethod.GET)
    public void getCardImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        String bundleName = ids[2];
        Long packageId = Long.parseLong(ids[3]);
        String elementSchoolName = ids[4];
        String cardName = ids[5];
        if (cardName.indexOf(".") > 0) cardName = cardName.substring(0, cardName.indexOf("."));

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleName);
        Key packageKey = KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        if (request.getDateHeader("If-Modified-Since") >= card.getModifiedDate().getTime() - 1000) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        if (card.isRenderable()) {
            response.setContentType("image/" + card.getImageFormat().name());
            response.setDateHeader("Last-Modified", card.getModifiedDate().getTime());
            response.setHeader("Cache-Control", "private");
            try {
                response.getOutputStream().write(card.getImageData());
            } catch (IOException ignored) {
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/*/*/*", method = RequestMethod.POST)
    public String updateCard(@RequestParam("image") MultipartFile file,
                             ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[2]);
        String elementSchoolName = ids[3];
        String cardName = ids[4];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();

        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        card.setImageData(file.getBytes());
        pm.makePersistent(card);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        modelMap.put("card", card);
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("elementSchool", elementSchool);
        return "card/view";
    }

    @RequestMapping(value = "/*/*/*/delete", method = RequestMethod.POST)
    public String deleteCard(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[2]);
        String elementSchoolName = ids[3];
        String cardName = ids[4];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        pm.deletePersistent(card);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", elementSchool);
        return "elementschool/view";
    }

    @RequestMapping(value = "/*/*/*/updateimage", method = RequestMethod.POST)
    public void updateImage(@RequestParam("image") MultipartFile file,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        Image oldImage = ImagesServiceFactory.makeImage(file.getBytes());
        Transform resize = ImagesServiceFactory.makeResize(171, 264);
        Image cardImage = imagesService.applyTransform(resize, oldImage);
        cardImage.getFormat().name();
        card.setImageData(cardImage.getImageData());
        pm.makePersistent(card);

        redirect("/arena", request, response);
    }
}
