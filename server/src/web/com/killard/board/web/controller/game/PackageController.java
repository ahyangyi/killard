package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.controller.BasicController;
import com.killard.board.web.util.FormUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/{bundleId}")
public class PackageController extends BasicController {

    @RequestMapping(method = {RequestMethod.GET})
    public String viewPackage(@PathVariable String bundleId,
                              ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        return "package/view";
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String updatePackage(@PathVariable String bundleId,
                                @RequestParam("locales") String[] locales,
                                @RequestParam("names") String[] names,
                                @RequestParam("descriptions") String[] descriptions,
                                ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = PersistenceHelper.getPersistenceManager().getObjectById(PackageBundleDO.class, key);
        PackageDO pack = bundle.getDraft();
        FormUtils.updateDescriptors(pack, locales, names, descriptions);
        modelMap.put("package", pack);
        return "package/edit";
    }

    @RequestMapping(value = {"/boards"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getBoards(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        modelMap.put("boards", bundle.getRelease().getBoards());
        return "boards";
    }

    @RequestMapping(value = {"/new"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String newPackage(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = new PackageBundleDO(bundleId);
        bundle = pm.makePersistent(bundle);
        modelMap.put("package", bundle.draft());
        pm.makePersistent(bundle);
        return "edit";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void delete(@PathVariable String bundleId,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.deletePersistent(bundle);
        redirect("/game", request, response);
    }

    @RequestMapping(value = "/release", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void release(@PathVariable String bundleId,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        bundle.release();
        redirect("/game", request, response);
    }

    @RequestMapping(value = "/addmanager", method = RequestMethod.POST)
    public String addManager(@PathVariable String bundleId, @RequestParam("manager") String manager,
                             ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getDraft());
        return "package/edit";
    }

    @RequestMapping(value = "/deletemanager", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteManager(@PathVariable String bundleId, @RequestParam("email") String email,
                                ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.makePersistent(bundle);
        modelMap.put("package", bundle.getDraft());
        return "/game/" + bundle.getName();
    }

    @RequestMapping(value = "/newelement", method = RequestMethod.POST)
    public String newElement(@PathVariable String bundleId,
                             @RequestParam("elementId") String elementId,
                             ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, key).getDraft().getKey();

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementDO element = pack.newElement(elementId);
        pm.makePersistent(pack);
        modelMap.put("element", element);
        return "element/edit";
    }

}
