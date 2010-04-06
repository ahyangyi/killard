package com.killard.board.web.controller.game;

import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.parser.ScriptEngine;
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
import java.util.Arrays;

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
@RequestMapping("/{bundleId}/element/{elementId}/skill/{skillId}")
public class SkillController extends BasicController {

    private static final ScriptEngine engine = new ScriptEngine();

    @RequestMapping(method = RequestMethod.GET)
    public String view(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String skillId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        QueryUtils.fetchSkill(bundleId, packageId, elementId, skillId, modelMap);
        return "skill/edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@PathVariable String bundleId, @PathVariable String elementId,
                         @PathVariable String skillId,
                         @RequestParam("targets") String[] targets,
                         @RequestParam("cost") int cost,
                         @RequestParam("function") String function,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        QueryUtils.fetchSkill(bundleId, null, elementId, skillId, modelMap);
        SkillDO skill = (SkillDO) modelMap.get("skill");
        skill.setCost(cost);
        skill.setTargets(Arrays.asList(targets));
        skill.setFunction(engine.parseFunction("function(){" + function + "}"));
        FormUtils.updateDescriptors(skill, locales, names, descriptions);
        PersistenceHelper.commit();
        return "skill/edit";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String skillId,
                      @RequestParam(value = "v", required = false) String packageId,
                      ModelMap modelMap,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchSkill(bundleId, packageId, elementId, skillId, modelMap);
        SkillDO skill = (SkillDO) modelMap.get("skill");
        ResponseUtils.outputImage(request, response, skill);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String elementId,
                              @PathVariable String skillId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap) throws Exception {
        QueryUtils.fetchSkill(bundleId, null, elementId, skillId, modelMap);
        SkillDO skill = (SkillDO) modelMap.get("skill");
        skill.setImageData(file.getBytes());
        PersistenceHelper.commit();
        return "skill/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                @PathVariable String skillId,
                                ModelMap modelMap) throws Exception {
        QueryUtils.fetchSkill(bundleId, null, elementId, skillId, modelMap);
        return "skill/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String elementId,
                         @PathVariable String skillId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        QueryUtils.fetchSkill(bundleId, null, elementId, skillId, modelMap);
        SkillDO skill = (SkillDO) modelMap.get("skill");

        pm.deletePersistent(skill);
        PersistenceHelper.commit();

        return "element/edit";
    }
}
