package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.parser.ScriptEngine;
import com.killard.board.web.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
public class CardController extends BasicController {

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

    @RequestMapping(value = "/package/*/*/*/view.*", method = RequestMethod.GET)
    public String viewCard(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];
        String cardName = ids[3];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);

        modelMap.put("card", card);
        return "package/card";
    }

    @RequestMapping(value = "/package/*/*/*/edit.*", method = RequestMethod.POST)
    public String updateCard(@RequestParam("definition") String definition,
                             @RequestParam("image") MultipartFile file,
                             ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];
        String cardName = ids[3];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();

        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        card.setDefinition(definition);
        card.setImageData(file.getBytes());
        pm.makePersistent(card);

        modelMap.put("card", card);
        return "package/card";
    }

    @RequestMapping(value = "/package/*/*/*/delete.*", method = RequestMethod.POST)
    public String deleteCard(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];
        String cardName = ids[3];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        pm.deletePersistent(card);

        modelMap.put("elementschool", pm.getObjectById(ElementSchoolDO.class, elementSchoolkey));
        return "package/elementschool";
    }

    @RequestMapping(value = "/package/*/*/*/quickedit.*", method = RequestMethod.POST)
    public void quickedit(@RequestParam("image") MultipartFile file,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getRequestURI().split("/");
        Long packageBundleId = Long.parseLong(ids[1]);
        String elementSchoolName = ids[2];
        String cardName = ids[3];

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), packageBundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey();

        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolName);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardName);

        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        card.setImageData(file.getBytes());
        pm.makePersistent(card);

        redirect("/board", request, response);
    }
}
