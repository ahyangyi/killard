package com.killard.board.web.controller.game;

import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.RoleDO;
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
@RequestMapping("/{bundleId}/role/{roleId}")
public class RoleController {

    @RequestMapping(method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId, @PathVariable String roleId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        QueryUtils.fetchRole(bundleId, null, roleId, modelMap);
        return "role/edit";
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String update(@PathVariable String bundleId, @PathVariable String roleId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        QueryUtils.fetchRole(bundleId, null, roleId, modelMap);
        RoleDO role = (RoleDO) modelMap.get("role");
        FormUtils.updateDescriptors(role, locales, names, descriptions);
        return "role/edit";
    }

    @RequestMapping(value = "/handlers", method = {RequestMethod.GET})
    public String handlers(@PathVariable String bundleId, @PathVariable String roleId,
                           @RequestParam(value = "v", required = false) String packageId,
                           ModelMap modelMap) throws Exception {
        QueryUtils.fetchRole(bundleId, packageId, roleId, modelMap);
        return "role/handlers";
    }

    @RequestMapping(value = "/handlers", method = {RequestMethod.POST})
    public String updateHandlers(@PathVariable String bundleId, @PathVariable String roleId,
                           @RequestParam(value = "validator_actionClass", required = false)
                           String[] validator_actionClass,
                           @RequestParam(value = "validator_selfTargeted", required = false)
                           boolean[] validator_selfTargeted,
                           @RequestParam(value = "validator_function", required = false) String[] validator_function,
                           @RequestParam(value = "before_actionClass", required = false) String[] before_actionClass,
                           @RequestParam(value = "before_selfTargeted", required = false) boolean[] before_selfTargeted,
                           @RequestParam(value = "before_function", required = false) String[] before_function,
                           @RequestParam(value = "after_actionClass", required = false) String[] after_actionClass,
                           @RequestParam(value = "after_selfTargeted", required = false) boolean[] after_selfTargeted,
                           @RequestParam(value = "after_function", required = false) String[] after_function,
                           ModelMap modelMap) throws Exception {
        QueryUtils.fetchRole(bundleId, null, roleId, modelMap);
        RoleDO role = (RoleDO) modelMap.get("role");
        List<AttributeHandler> validator =
                FormUtils.buildHandlers(validator_actionClass, validator_selfTargeted, validator_function);
        List<AttributeHandler> before =
                FormUtils.buildHandlers(before_actionClass, before_selfTargeted, before_function);
        List<AttributeHandler> after =
                FormUtils.buildHandlers(after_actionClass, after_selfTargeted, after_function);
        role.setValidators(validator);
        role.setBefore(before);
        role.setAfter(after);
        PersistenceHelper.commit();
        return "role/handlers";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String roleId,
                      @RequestParam(value = "v", required = false) String packageId,
                      ModelMap modelMap,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchRole(bundleId, packageId, roleId, modelMap);
        RoleDO role = (RoleDO) modelMap.get("role");
        ResponseUtils.outputImage(request, response, role);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String roleId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap) throws Exception {
        QueryUtils.fetchRole(bundleId, null, roleId, modelMap);
        RoleDO role = (RoleDO) modelMap.get("role");
        role.setImageData(file.getBytes());
        PersistenceHelper.commit();
        return "role/edit";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String roleId,
                                ModelMap modelMap) throws Exception {
        QueryUtils.fetchRole(bundleId, null, roleId, modelMap);
        return "role/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String roleId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        QueryUtils.fetchRole(bundleId, null, roleId, modelMap);
        RoleDO role = (RoleDO) modelMap.get("role");
        pm.deletePersistent(role);
        PersistenceHelper.commit();

        return "package/edit";
    }

}
