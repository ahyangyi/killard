package com.killard.board.web.controller.game;

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

import javax.jdo.Extent;
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
public class IndexController extends BasicController {

    @RequestMapping(value = {"/game", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String browser(
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            ModelMap modelMap) throws Exception {
        if (filter.equalsIgnoreCase("mine")) {
            PersistenceManager pm = PersistenceHelper.getPersistenceManager();
            Query query = pm.newQuery(PlayerProfileDO.class);
            query.setFilter("identities == id");
            query.declareParameters("String id");
            Collection result = (Collection) query.execute(getUser().getUserId());
            if (!result.isEmpty()) {
                List<PackageBundleDO> bundles = new LinkedList<PackageBundleDO>();
                for (Object obj : result) {
                    PlayerProfileDO player = (PlayerProfileDO) obj;
                    PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, player.getBundleKey());
                    bundles.add(bundle);
                }
                modelMap.put("bundles", bundles);
                return "mygame";
            }
        }
        return "game";
    }

    @RequestMapping(value = "/query/top", method = {RequestMethod.GET, RequestMethod.POST})
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

}
