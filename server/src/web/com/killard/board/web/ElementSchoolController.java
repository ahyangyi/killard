package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.web.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

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
public class ElementSchoolController extends BasicController {

    @RequestMapping(value = "/package/*/*/view.*", method = RequestMethod.GET)
    public String view(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);

        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        modelMap.put("elementSchool", elementSchool);
        return "package/elementschool";
    }

    @RequestMapping(value = "/package/*/*/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);
        pm.deletePersistent(elementSchool);
        
        modelMap.put("package", pack);
        return "package/package";
    }

    @RequestMapping(value = "/package/*/*/newcard.*", method = RequestMethod.POST)
    public String newCard(@RequestParam("cardName") String cardName,
                          ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);

        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);
        MetaCardDO card = elementSchool.newCard(cardName);
        pm.makePersistent(elementSchool);

        modelMap.put("card", card);
        return "package/card";
    }

}
