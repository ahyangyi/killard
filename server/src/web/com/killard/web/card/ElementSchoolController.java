package com.killard.web.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.killard.web.BasicController;
import com.killard.web.PersistenceHelper;
import com.killard.web.context.BoardContext;
import com.killard.web.jdo.card.ElementSchoolDO;
import com.killard.web.jdo.card.ElementSchoolDescriptorDO;
import com.killard.web.jdo.card.PackageDO;
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
        return "card/element";
    }

    @RequestMapping(value = "/package/elementschool/add.*", method = RequestMethod.POST)
    public String add(@RequestParam("packageId") long packageId,
                      @RequestParam("elementSchoolName") String elementSchoolName,
                      ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);

        User user = getUser();
        if (pack.getManagers().contains(user)) {
            ElementSchoolDO elementSchool = new ElementSchoolDO(elementSchoolName, pack);
            ElementSchoolDescriptorDO descriptor =
                    new ElementSchoolDescriptorDO(elementSchool, BoardContext.getLocale());
            descriptor.setName(elementSchoolName);
            elementSchool.addDescriptor(descriptor);
            pm.makePersistent(elementSchool);
            PersistenceHelper.doTransaction();
        }

        modelMap.put("package", pack);
        return "card/package";
    }

    @RequestMapping(value = "/package/elementschool/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@RequestParam("packageId") long packageId,
                         @RequestParam("elementSchoolId") long elementSchoolId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey = KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);

        User user = getUser();
        if (pack.getManagers().contains(user)) {
            ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);
            pm.deletePersistent(elementSchool);
        }
        modelMap.put("package", pack);
        return "card/package";
    }

}
