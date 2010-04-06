package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.web.controller.BasicController;
import com.killard.board.web.util.FormUtils;
import com.killard.board.web.util.QueryUtils;
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
        QueryUtils.fetchCard(bundleId, elementId, packageId, cardId, modelMap);
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
                         @RequestParam(value = "attributes", required = false) String[] attributeKeys,
                         @RequestParam(value = "skills", required = false) String[] skillKeys,
                         ModelMap modelMap) throws Exception {
        QueryUtils.fetchCard(bundleId, elementId, null, cardId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        MetaCardDO card = (MetaCardDO) modelMap.get("card");
        card.setLevel(level);
        card.setMaxHealth(health);
        card.setAttackValue(attack);
        card.setRange(range);
        card.clearSkill();
        card.clearAttribute();
        if (attributeKeys != null) {
            for (String key : attributeKeys) {
                Key attributeKey = KeyFactory.createKey(element.getKey(), AttributeDO.class.getSimpleName(), key);
                card.addAttribute(element.getAttribute(attributeKey));
            }
        }
        if (skillKeys != null) {
            for (String key : skillKeys) {
                Key skillKey = KeyFactory.createKey(element.getKey(), SkillDO.class.getSimpleName(), key);
                card.addSkill(element.getSkill(skillKey));
            }
        }
        FormUtils.updateDescriptors(card, locales, names, descriptions);
        PersistenceHelper.commit();
        return "card/view";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String cardId,
                      @RequestParam(value = "v", required = false) String packageId,
                      ModelMap modelMap,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchCard(bundleId, elementId, packageId, cardId, modelMap);
        MetaCardDO card = (MetaCardDO) modelMap.get("card");
        ResponseUtils.outputImage(request, response, card);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String elementId,
                              @PathVariable String cardId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap, HttpServletRequest request) throws Exception {
        QueryUtils.fetchCard(bundleId, elementId, null, cardId, modelMap);
        MetaCardDO card = (MetaCardDO) modelMap.get("card");
        card.setImageData(file.getBytes());
        PersistenceHelper.commit();
        return "card/view";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                @PathVariable String cardId,
                                ModelMap modelMap) throws Exception {
        QueryUtils.fetchCard(bundleId, elementId, null, cardId, modelMap);
        return "card/delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String cardId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        QueryUtils.fetchCard(bundleId, elementId, null, cardId, modelMap);
        MetaCardDO card = (MetaCardDO) modelMap.get("card");
        pm.deletePersistent(card);
        PersistenceHelper.commit();
        return "element/view";
    }
}
