package com.killard.board.web.util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.SkillDO;
import org.springframework.ui.ModelMap;

import javax.jdo.PersistenceManager;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public enum QueryUtils {

    instance;

    public static void fetchPackage(String bundleId, String packageId, ModelMap modelMap) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack;
        if (packageId == null) pack = bundle.getDraft();
        else {
            Key packageKey = KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
            pack = pm.getObjectById(PackageDO.class, packageKey);
        }
        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
    }

    public static void fetchRole(String bundleId, String packageId, String roleId, ModelMap modelMap) {
        fetchPackage(bundleId, packageId, modelMap);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO pack = (PackageDO) modelMap.get("package");
        Key roleKey = KeyFactory.createKey(pack.getKey(), RoleDO.class.getSimpleName(), roleId);
        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);
        modelMap.put("role", role);
    }

    public static void fetchElement(String bundleId, String packageId, String elementId, ModelMap modelMap) {
        fetchPackage(bundleId, packageId, modelMap);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageDO pack = (PackageDO) modelMap.get("package");
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        modelMap.put("element", element);
    }

    public static void fetchCard(String bundleId, String packageId, String elementId, String cardId,
                                  ModelMap modelMap) {
        fetchElement(bundleId, packageId, elementId, modelMap);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        ElementDO element = (ElementDO) modelMap.get("element");
        Key cardKey = KeyFactory.createKey(element.getKey(), MetaCardDO.class.getSimpleName(), cardId);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        modelMap.put("card", card);
    }

    public static void fetchSkill(String bundleId, String packageId, String elementId, String skillId,
                                  ModelMap modelMap) {
        fetchElement(bundleId, packageId, elementId, modelMap);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        ElementDO element = (ElementDO) modelMap.get("element");
        Key skillKey = KeyFactory.createKey(element.getKey(), SkillDO.class.getSimpleName(), skillId);
        SkillDO skill = pm.getObjectById(SkillDO.class, skillKey);
        modelMap.put("skill", skill);
    }

    public static void fetchAttribute(String bundleId, String packageId, String elementId, String attributeId,
                                  ModelMap modelMap) {
        fetchElement(bundleId, packageId, elementId, modelMap);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        ElementDO element = (ElementDO) modelMap.get("element");
        Key attributeKey = KeyFactory.createKey(element.getKey(), AttributeDO.class.getSimpleName(), attributeId);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);
        modelMap.put("attribute", attribute);
    }

}
