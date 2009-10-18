package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.web.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;

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

    @RequestMapping(value = "/package/elementschool.*", method = RequestMethod.GET)
    public String edit(@RequestParam("packageId") long packageId,
                       @RequestParam("elementSchoolId") long elementSchoolId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey = KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        modelMap.put("package", pack);
        modelMap.put("elementSchool", elementSchool);
        return "board/element";
    }

    @RequestMapping(value = "/package/elementschool/add.*", method = RequestMethod.POST)
    public String add(@RequestParam("packageId") long packageId,
                      @RequestParam("elementSchoolName") String elementSchoolName,
                      ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pack.newElementSchool(elementSchoolName);
        pm.makePersistent(pack);
        modelMap.put("elementSchool", elementSchool);
        return "card/element";
    }

    @RequestMapping(value = "/package/elementschool/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@RequestParam("packageId") long packageId,
                         @RequestParam("elementSchoolId") long elementSchoolId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey = KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);
        pm.deletePersistent(elementSchool);
        
        modelMap.put("package", pack);
        return "card/package";
    }

}
