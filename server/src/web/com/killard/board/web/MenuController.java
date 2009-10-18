package com.killard.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

import javax.jdo.PersistenceManager;
import javax.jdo.Extent;

import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageStatus;

import java.util.List;
import java.util.LinkedList;

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
public class MenuController {

    @RequestMapping(value = "/packages.*", method = RequestMethod.GET)
    public String getPackages(ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        List<PackageDO> packages = new LinkedList<PackageDO>();
        Extent<PackageBundleDO> extent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : extent) {
            if (bundle.getStatus().equals(PackageStatus.PUBLIC.name())) packages.add(bundle.getRelease());
        }
        extent.closeAll();
        modelMap.put("packages", packages);
        return "package/list";
    }

    @RequestMapping(value = "/newpackage.*", method = RequestMethod.POST)
    public String newPackage(@RequestParam("packageName") String packageName,
                             ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = new PackageBundleDO(packageName);
        bundle = pm.makePersistent(bundle);
        modelMap.put("package", bundle.draft());
        pm.makePersistent(bundle);
        return "package/package";
    }

    @RequestMapping(value = "/help.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String help() throws Exception {
        return "help";
    }

    @RequestMapping(value = "/terms.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String terms() throws Exception {
        return "terms";
    }

    @RequestMapping(value = "/privacy.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String privacy() throws Exception {
        return "privacy";
    }

}
