package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.controller.BasicController;
import com.killard.board.web.util.FormUtils;
import com.killard.board.web.util.QueryUtils;
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
public class PackageController extends BasicController {

    @RequestMapping(value = "/{bundleId}", method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId,
                       ModelMap modelMap) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        return "package/view";
    }

    @RequestMapping(value = "/{bundleId}", method = {RequestMethod.POST})
    public String update(@PathVariable String bundleId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageDO pack = (PackageDO) modelMap.get("package");
        FormUtils.updateDescriptors(pack, locales, names, descriptions);
        return "package/edit";
    }

    @RequestMapping(value = "/{bundleId}/boards", method = {RequestMethod.GET, RequestMethod.POST})
    public String getBoards(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        modelMap.put("boards", bundle.getRelease().getBoards());
        return "boards";
    }

    @RequestMapping(value = "/{bundleId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        return "package/delete";
    }

    @RequestMapping(value = "/{bundleId}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void delete(@PathVariable String bundleId,
                       ModelMap modelMap,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageBundleDO bundle = (PackageBundleDO) modelMap.get("bundle");
        pm.deletePersistent(bundle);
        redirect("/game", request, response);
    }

    @RequestMapping(value = "/{bundleId}/release", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void release(@PathVariable String bundleId, ModelMap modelMap,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageBundleDO bundle = (PackageBundleDO) modelMap.get("bundle");
        bundle.release();
        redirect("/game", request, response);
    }

    @RequestMapping(value = "/{bundleId}/addmanager", method = RequestMethod.POST)
    public String addManager(@PathVariable String bundleId, @RequestParam("manager") String manager,
                             ModelMap modelMap) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageBundleDO bundle = (PackageBundleDO) modelMap.get("bundle");
        return "package/edit";
    }

    @RequestMapping(value = "/{bundleId}/deletemanager", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteManager(@PathVariable String bundleId, @RequestParam("id") String id,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageBundleDO bundle = (PackageBundleDO) modelMap.get("bundle");
        return "package/edit";
    }

    @RequestMapping(value = {"/{bundleId}/newelement"}, method = RequestMethod.GET)
    public String requestNewElement(@PathVariable String bundleId,
                                    ModelMap modelMap) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        return "package/newelement";
    }

    @RequestMapping(value = "/{bundleId}/newelement", method = RequestMethod.POST)
    public void newElement(@PathVariable String bundleId,
                           @RequestParam String elementId,
                           ModelMap modelMap,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageDO pack = (PackageDO) modelMap.get("package");
        pack.newElement(elementId);
        redirect("/game/" + bundleId + "/element/" + elementId, request, response);
    }

}
