package com.killard.board.web.controller.game;

import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
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
public class IndexController {

    @RequestMapping(value = {"/game", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String browser() throws Exception {
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
