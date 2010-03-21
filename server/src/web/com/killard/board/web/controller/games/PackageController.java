package com.killard.board.web.controller.games;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageStatus;
import com.killard.board.jdo.board.RuleDO;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
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
public class PackageController extends BasicController {

    @RequestMapping(value = {"/games", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String browser() throws Exception {
        return "games";
    }

    @RequestMapping(value = {"/packages"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getPackages(ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageBundleDO> extent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : extent) {
            if (bundle.getStatus().equals(PackageStatus.PUBLIC.name())) packages.add(bundle.getRelease());
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "packages";
    }

    @RequestMapping(value = {"/newpackage"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String newPackage(@RequestParam("packageName") String packageName,
                             ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = new PackageBundleDO(packageName);
        bundle = pm.makePersistent(bundle);
        modelMap.put("package", bundle.draft());
        pm.makePersistent(bundle);
        return "edit";
    }

    @RequestMapping(value = {"/*/boards"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getBoards(ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory
                .createKey(PackageBundleDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        modelMap.put("boards",  bundle.getRelease().getBoards());
        return "boards";
    }

    @RequestMapping(value = {"/*"}, method = {RequestMethod.GET})
    public String viewPackage(ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory
                .createKey(PackageBundleDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        return "package/view";
    }

    @RequestMapping(value = {"/*"}, method = {RequestMethod.POST})
    public String updatePackage(ModelMap modelMap, HttpServletRequest request) throws Exception {
        Key key = KeyFactory
                .createKey(PackageBundleDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = PersistenceHelper.getPersistenceManager().getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getDraft());
        return "package/edit";
    }

    @RequestMapping(value = "/*/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.deletePersistent(bundle);
        redirect("/games", request, response);
    }

    @RequestMapping(value = "/*/release", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void release(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.makePersistent(bundle.release());
        redirect("/games", request, response);
    }

    @RequestMapping(value = "/*/addmanager", method = RequestMethod.POST)
    public String addManager(@RequestParam("manager") String manager,
                             ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.makePersistent(bundle);
        modelMap.put("package", bundle.getRelease());
        return "package/edit";
    }

    @RequestMapping(value = "/*/deletemanager", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteManager(@RequestParam("email") String email,
                                ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.makePersistent(bundle);
        modelMap.put("package", bundle.getRelease());
        return "/games/" + bundle.getName();
    }

    @RequestMapping(value = "/*/newelementschool", method = RequestMethod.POST)
    public String newElementSchool(@RequestParam("elementSchoolName") String elementSchoolName,
                                   ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory
                .createKey(PackageBundleDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementSchoolDO elementSchool = pack.newElementSchool(elementSchoolName);
        pm.makePersistent(pack);
        modelMap.put("elementSchool", elementSchool);
        return "elementschool/edit";
    }

    @RequestMapping(value = "/games/*/rule", method = RequestMethod.POST)
    public String rule(@RequestParam("definition") String definition,
                       ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), getPackageBundleId(request.getRequestURI()));
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        RuleDO oldRule = bundle.getDraft().getRule();
        pm.makePersistent(bundle);
        modelMap.put("package", bundle.getDraft());
        return "package/edit";
    }

}
