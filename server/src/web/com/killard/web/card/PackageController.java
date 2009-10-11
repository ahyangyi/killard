package com.killard.web.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.jdo.AttributeHandler;
import com.killard.jdo.PersistenceHelper;
import com.killard.jdo.card.PackageDO;
import com.killard.jdo.card.descriptor.PackageDescriptorDO;
import com.killard.jdo.card.RuleDO;
import com.killard.jdo.context.BoardContext;
import com.killard.web.BasicController;
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
            if (pack.isPublished() && pack.isOpen()) {
                packages.add(pack);
            }
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "card/packages";
    }

    @RequestMapping(value = "/package/my.*", method = RequestMethod.GET)
    public String getMyPackages(ModelMap modelMap) throws Exception {
        User user = getUser();
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            if (pack.getManagers().contains(user)) {
                packages.add(pack);
            }
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "card/packages";
    }

    @RequestMapping(value = "/package/custom.*", method = RequestMethod.GET)
    public String getCustomPackages(ModelMap modelMap) throws Exception {
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            if (pack.isPublished() && ! pack.isOpen()) {
                packages.add(pack);
            }
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "card/packages";
    }

    @RequestMapping(value = "/package/private.*", method = RequestMethod.GET)
    public String getPrivatePackages(ModelMap modelMap) throws Exception {
        User user = getUser();
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageDO> extent = PersistenceHelper.getPersistenceManager().getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            if (pack.isOpen() || pack.isPublished()) continue;
            if (pack.getManagers().contains(user) || pack.getPlayers().contains(user)) {
                packages.add(pack);
            }
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "card/packages";
    }

    @RequestMapping(value = "/package.*", method = RequestMethod.GET)
    public String getPackage(@RequestParam("packageId") long packageId,
                             ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        modelMap.put("package", PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, key));
        return "card/package";
    }

    @RequestMapping(value = "/package/add.*", method = RequestMethod.POST)
    public String addPackage(@RequestParam("packageName") String packageName,
                             ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO pack = new PackageDO(packageName, getUser());
        pm.makePersistent(pack);

        RuleDO rule = new RuleDO(pack, new ArrayList<AttributeHandler>(), new ArrayList<AttributeHandler>(), new ArrayList<AttributeHandler>());
        pack.setRule(rule);

        PackageDescriptorDO descriptor = new PackageDescriptorDO(pack, BoardContext.getLocale());
        descriptor.setName(packageName);
        pack.addDescriptor(descriptor);
        pm.makePersistent(pack);

        modelMap.put("package", pack);
        return "card/package";
    }

    @RequestMapping(value = "/package/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void removePackage(@RequestParam("packageId") long packageId,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        pm.deletePersistent(pm.getObjectById(PackageDO.class, key));
        redirect("/game/package/list", request, response);
    }

    @RequestMapping(value = "/package/manager/add.*", method = RequestMethod.POST)
    public String addManager(@RequestParam("packageId") long packageId,
                             @RequestParam("manager") String manager,
                             ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, key);
        pack.addManager(new User(manager, UserServiceFactory.getUserService().getCurrentUser().getAuthDomain()));
        PersistenceHelper.getPersistenceManager().makePersistent(pack);
        modelMap.put("package", pack);
        return "card/package";
    }

    @RequestMapping(value = "/package/manager/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String removeManager(@RequestParam("packageId") long packageId,
                                @RequestParam("email") String email,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = pm.getObjectById(PackageDO.class, key);
        User delete = null;
        for (User user : pack.getManagers()) {
            if (user.getEmail().equals(email)) delete = user;
        }
        if (delete != null) pack.removeManager(delete);
        pm.makePersistent(pack);
        modelMap.put("package", pack);
        return "card/package";
    }

    @RequestMapping(value = "/package/player/add.*", method = RequestMethod.POST)
    public String addPlayer(@RequestParam("packageId") long packageId,
                             @RequestParam("player") String player,
                             ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = PersistenceHelper.getPersistenceManager().getObjectById(PackageDO.class, key);
        pack.addPlayer(new User(player, UserServiceFactory.getUserService().getCurrentUser().getAuthDomain()));
        PersistenceHelper.getPersistenceManager().makePersistent(pack);
        modelMap.put("package", pack);
        return "card/package";
    }

    @RequestMapping(value = "/package/player/delete.*", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String removePlayer(@RequestParam("packageId") long packageId,
                                @RequestParam("email") String email,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        PackageDO pack = pm.getObjectById(PackageDO.class, key);
        User delete = null;
        for (User user : pack.getPlayers()) {
            if (user.getEmail().equals(email)) delete = user;
        }
        if (delete != null) pack.removeManager(delete);
        pm.makePersistent(pack);
        modelMap.put("package", pack);
        return "card/package";
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
        return "card/package";
    }

}
