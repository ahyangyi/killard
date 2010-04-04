package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.record.ExecutableActions;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
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
@RequestMapping("/{bundleId}/role/{roleId}")
public class RoleController {

    @RequestMapping(method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId, @PathVariable String roleId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key roleKey = KeyFactory.createKey(packageKey, RoleDO.class.getSimpleName(), roleId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", bundle.getDraft());
        modelMap.put("role", role);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "role/view";
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String update(@PathVariable String bundleId, @PathVariable String roleId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key roleKey = KeyFactory.createKey(pack.getKey(), RoleDO.class.getSimpleName(), roleId);

        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);
        FormUtils.updateDescriptors(role, locales, names, descriptions);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("role", role);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "role/view";
    }

    @RequestMapping(value = "/handlers", method = {RequestMethod.GET})
    public String handlers(@PathVariable String bundleId, @PathVariable String roleId,
                           @RequestParam(value = "v", required = false) String packageId,
                           ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key roleKey = KeyFactory.createKey(packageKey, RoleDO.class.getSimpleName(), roleId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);

        modelMap.put("package", bundle.getDraft());
        modelMap.put("role", role);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "role/handlers";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String roleId,
                      @RequestParam(value = "v", required = false) String packageId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key roleKey = KeyFactory.createKey(packageKey, RoleDO.class.getSimpleName(), roleId);

        ResponseUtils.outputImage(request, response, pm.getObjectById(RoleDO.class, roleKey));
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String roleId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();

        Key roleKey = KeyFactory.createKey(pack.getKey(), RoleDO.class.getSimpleName(), roleId);

        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);
        role.setImageData(file.getBytes());

        modelMap.put("role", role);
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        return "role/view";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String roleId,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key roleKey = KeyFactory.createKey(pack.getKey(), RoleDO.class.getSimpleName(), roleId);

        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("role", role);
        return "role/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String roleId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key roleKey = KeyFactory.createKey(pack.getKey(), RoleDO.class.getSimpleName(), roleId);

        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);
        pm.deletePersistent(role);
        PersistenceHelper.commit();

        modelMap.put("package", pack);
        return "package/view";
    }

}
