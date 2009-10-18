package com.killard.board.web.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RuleDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.web.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @RequestMapping(value = "/package/public.*", method = RequestMethod.GET)
    public String getPackages(ModelMap modelMap) throws Exception {
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            packages.add(pack);
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "board/packages";
    }

    @RequestMapping(value = "/package/my.*", method = RequestMethod.GET)
    public String getMyPackages(ModelMap modelMap) throws Exception {
        User user = getUser();
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            packages.add(pack);
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "board/packages";
    }

    @RequestMapping(value = "/package/custom.*", method = RequestMethod.GET)
    public String getCustomPackages(ModelMap modelMap) throws Exception {
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            packages.add(pack);
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "board/packages";
    }

    @RequestMapping(value = "/package/private.*", method = RequestMethod.GET)
    public String getPrivatePackages(ModelMap modelMap) throws Exception {
        User user = getUser();
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "board/packages";
    }

    @RequestMapping(value = "/package.*", method = RequestMethod.GET)
    public String getPackage(@RequestParam("packageId") long packageId,
                             ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        modelMap.put("package", PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, key));
        return "board/package";
    }

    @RequestMapping(value = "/package/add.*", method = RequestMethod.POST)
    public String addPackage(@RequestParam("packageName") String packageName,
                             ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = new PackageBundleDO(packageName);
        bundle = pm.makePersistent(bundle);
        modelMap.put("package", bundle.draft());
        pm.makePersistent(bundle);
        return "board/package";
    }

    @RequestMapping(value = "/package/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void removePackage(@RequestParam("packageId") long packageId,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        pm.deletePersistent(pm.getObjectById(PackageDO.class, key));
        redirect("/record/package/list", request, response);
    }

    @RequestMapping(value = "/package/manager/add.*", method = RequestMethod.POST)
    public String addManager(@RequestParam("packageId") long packageId,
                             @RequestParam("manager") String manager,
                             ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, key);
        PersistenceHelper.getPersistenceManager().makePersistent(pack);
        modelMap.put("package", pack);
        return "board/package";
    }

    @RequestMapping(value = "/package/manager/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String removeManager(@RequestParam("packageId") long packageId,
                                @RequestParam("email") String email,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = pm.getObjectById(PackageDO.class, key);
        pm.makePersistent(pack);
        modelMap.put("package", pack);
        return "board/package";
    }

    @RequestMapping(value = "/package/player/add.*", method = RequestMethod.POST)
    public String addPlayer(@RequestParam("packageId") long packageId,
                            @RequestParam("player") String player,
                            ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, key);
        PersistenceHelper.getPersistenceManager().makePersistent(pack);
        modelMap.put("package", pack);
        return "board/package";
    }

    @RequestMapping(value = "/package/player/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String removePlayer(@RequestParam("packageId") long packageId,
                               @RequestParam("email") String email,
                               ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = pm.getObjectById(PackageDO.class, key);
        pm.makePersistent(pack);
        modelMap.put("package", pack);
        return "board/package";
    }

    @RequestMapping(value = "/package/rule.*", method = RequestMethod.POST)
    public String rule(@RequestParam("packageId") long packageId,
                       @RequestParam("definition") String definition,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = pm.getObjectById(PackageDO.class, key);
        RuleDO oldRule = pack.getRule();
        oldRule.setDefinition(definition);
        pm.makePersistent(pack);
        modelMap.put("package", pack);
        return "board/package";
    }

}
