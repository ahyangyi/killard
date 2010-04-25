package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageStatus;
import com.killard.board.jdo.board.PlayerProfileDO;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
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
@RequestMapping("/custom")
public class GameController extends BasicController {

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            @RequestParam(value = "packageId", required = false) String packageId,
            @RequestParam(value = "templateId", required = false) String templateId,
            ModelMap modelMap) throws Exception {
        if (filter.equalsIgnoreCase("top")) {
            PersistenceManager pm = PersistenceHelper.getPersistenceManager();
            List<PackageDO> packages = new LinkedList<PackageDO>();
            Query query = pm.newQuery(PackageBundleDO.class);
            query.setFilter("status == status");
            query.declareParameters("String status");
            Collection result = (Collection) query.execute(PackageStatus.PUBLIC.name());
            for (Object obj : result) {
                PackageBundleDO bundle = (PackageBundleDO) obj;
                packages.add(bundle.getRelease());
            }
            modelMap.put("packages", packages);
        } else if (filter.equalsIgnoreCase("mine")) {
            PersistenceManager pm = PersistenceHelper.getPersistenceManager();
            Query query = pm.newQuery(PlayerProfileDO.class);
            query.setFilter("identities == id");
            query.declareParameters("String id");
            Collection result = (Collection) query.execute(getUser().getUserId());
            if (!result.isEmpty()) {
                List<PackageBundleDO> bundles = new LinkedList<PackageBundleDO>();
                for (Object obj : result) {
                    PlayerProfileDO player = (PlayerProfileDO) obj;
                    if (player.isCreator()) modelMap.put("created", true);
                    PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, player.getBundleKey());
                    pm.makeTransient(bundle);
                    PersistenceHelper.commit();
                    bundles.add(bundle);
                }
                modelMap.put("bundles", bundles);
                return "mygame";
            }
        } else if (filter.equalsIgnoreCase("new")) {
            if (packageId == null) {
                PersistenceManager pm = PersistenceHelper.getPersistenceManager();
                List<PackageDO> packages = new LinkedList<PackageDO>();
                Query query = pm.newQuery(PackageBundleDO.class);
                query.setFilter("status == \"public\" && clonable == true");
                Collection result = (Collection) query.execute();
                for (Object obj : result) {
                    PackageBundleDO bundle = (PackageBundleDO) obj;
                    packages.add(bundle.getRelease());
                }
                modelMap.put("templates", packages);
                return "newgame";
            } else {
                PersistenceManager pm = PersistenceHelper.getPersistenceManager();
                PackageBundleDO template = null;
                if (templateId != null && templateId.trim().length() > 0) {
                    Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), templateId);
                    template = pm.getObjectById(PackageBundleDO.class, bundleKey);
                }
                PackageBundleDO bundle =
                        template == null ? new PackageBundleDO(packageId) : new PackageBundleDO(template, packageId);
                PlayerProfileDO profile = new PlayerProfileDO(bundle, getUser().getUserId(), getUser().getNickname());
                bundle.addPlayerProfile(profile);
                pm.makePersistent(bundle);
                PersistenceHelper.commit();
                modelMap.put("bundle", bundle);
                modelMap.put("package", bundle.getDraft());
                return "package/edit";
            }
        }
        return "game";
    }
    @RequestMapping(value = "/top", method = {RequestMethod.GET, RequestMethod.POST})
    public String list() {
        return "";
    }
}
