package com.killard.board.web.controller.game;

import com.google.appengine.api.images.Image;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.parser.Block;
import com.killard.board.parser.Function;
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
import java.util.ArrayList;

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
@RequestMapping("/{bundleId}/element/{elementId}")
public class ElementController extends BasicController {

    @RequestMapping(method = RequestMethod.GET)
    public String view(@PathVariable String bundleId, @PathVariable String elementId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, packageId, elementId, modelMap);
        return "element/edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@PathVariable String bundleId, @PathVariable String elementId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        FormUtils.updateDescriptors(element, locales, names, descriptions);

        return "element/edit";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String elementId,
                      @RequestParam(value = "v", required = false) String packageId,
                      ModelMap modelMap,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchElement(bundleId, packageId, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        ResponseUtils.outputImage(request, response, element);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String elementId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        element.setImageFormat(Image.Format.PNG);
        element.setImageData(file.getBytes());
        PersistenceHelper.commit();

        return "element/edit";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        return "element/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String elementId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        pm.deletePersistent(element);
        PersistenceHelper.commit();

        return "package/edit";
    }

    @RequestMapping(value = "/newcard", method = RequestMethod.GET)
    public String requestNewCard(@PathVariable String bundleId, @PathVariable String elementId,
                                 ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        return "element/newcard";
    }

    @RequestMapping(value = "/newcard", method = RequestMethod.POST)
    public void newCard(@PathVariable String bundleId, @PathVariable String elementId,
                        @RequestParam String cardId,
                        ModelMap modelMap,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        element.newCard(cardId);
        redirect("/game/" + bundleId + "/element/" + elementId + "/card/" + cardId, request, response);
    }

    @RequestMapping(value = "/newattribute", method = RequestMethod.GET)
    public String requestNewAttribute(@PathVariable String bundleId, @PathVariable String elementId,
                                      ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        return "element/newattribute";
    }

    @RequestMapping(value = "/newattribute", method = RequestMethod.POST)
    public void newAttribute(@PathVariable String bundleId, @PathVariable String elementId,
                             @RequestParam String attributeId,
                             ModelMap modelMap,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        element.newAttribute(attributeId);
        redirect("/game/" + bundleId + "/element/" + elementId + "/attribute/" + attributeId, request, response);
    }

    @RequestMapping(value = "/newskill", method = RequestMethod.GET)
    public String requestNewSkill(@PathVariable String bundleId, @PathVariable String elementId,
                                  ModelMap modelMap) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        return "element/newskill";
    }

    @RequestMapping(value = "/newskill", method = RequestMethod.POST)
    public void newSkill(@PathVariable String bundleId, @PathVariable String elementId,
                         @RequestParam String skillId,
                         ModelMap modelMap,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchElement(bundleId, null, elementId, modelMap);
        ElementDO element = (ElementDO) modelMap.get("element");
        element.newSkill(skillId, new ArrayList<String>(), 0, new Function("execute", null, new Block()));
        redirect("/game/" + bundleId + "/element/" + elementId + "/skill/" + skillId, request, response);
    }

}
