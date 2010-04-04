package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.controller.BasicController;
import com.killard.board.web.util.FormUtils;
import com.killard.board.web.util.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/{bundleId}/element/{elementId}/card/{cardId}")
public class CardController extends BasicController {

    @RequestMapping(method = RequestMethod.GET)
    public String view(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String cardId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key cardKey = KeyFactory.createKey(elementKey, MetaCardDO.class.getSimpleName(), cardId);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);

        modelMap.put("card", card);
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", card.getElement());
        return "card/view";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String cardId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         @RequestParam("level") int level,
                         @RequestParam("health") int health,
                         @RequestParam("attack") int attack,
                         @RequestParam("range") int range,
                         ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();

        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key cardKey = KeyFactory.createKey(elementKey, MetaCardDO.class.getSimpleName(), cardId);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        FormUtils.updateDescriptors(card, locales, names, descriptions);
        card.setLevel(level);
        card.setMaxHealth(health);
        card.setAttackValue(attack);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("card", card);
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "card/view";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String cardId,
                      @RequestParam(value = "v", required = false) String packageId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key cardKey = KeyFactory.createKey(elementKey, MetaCardDO.class.getSimpleName(), cardId);

        ResponseUtils.outputImage(request, response, pm.getObjectById(MetaCardDO.class, cardKey));
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String elementId,
                              @PathVariable String cardId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key cardKey = KeyFactory.createKey(elementKey, MetaCardDO.class.getSimpleName(), cardId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        card.setImageData(file.getBytes());

        modelMap.put("card", card);
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "card/view";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                @PathVariable String cardId,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key cardKey = KeyFactory.createKey(elementKey, MetaCardDO.class.getSimpleName(), cardId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        modelMap.put("card", card);
        return "card/delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String cardId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key cardKey = KeyFactory.createKey(elementKey, MetaCardDO.class.getSimpleName(), cardId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);

        pm.deletePersistent(card);
        PersistenceHelper.commit();

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }
}
