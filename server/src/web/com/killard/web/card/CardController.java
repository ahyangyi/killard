package com.killard.web.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.jdo.JdoCardBuilder;
import com.killard.jdo.PersistenceHelper;
import com.killard.jdo.card.CardDO;
import com.killard.jdo.card.CardDescriptorDO;
import com.killard.jdo.card.ElementSchoolDO;
import com.killard.jdo.card.PackageDO;
import com.killard.jdo.context.BoardContext;
import com.killard.parser.ScriptEngine;
import com.killard.web.BasicController;
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

    @RequestMapping(value = "/card.*", method = RequestMethod.GET)
    public String edit(@RequestParam("packageId") Long packageId,
                       @RequestParam("elementSchoolId") Long elementSchoolId,
                       @RequestParam("cardId") Long cardId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, CardDO.class.getSimpleName(), cardId);
        CardDO card = pm.getObjectById(CardDO.class, cardKey);
        modelMap.put("card", card);
        return "card/card";
    }

    @RequestMapping(value = "/card.*", method = RequestMethod.POST)
    public String edit(@RequestParam("packageId") Long packageId,
                       @RequestParam("elementSchoolId") Long elementSchoolId,
                       @RequestParam("cardId") Long cardId,
                       @RequestParam("definition") String definition,
                       @RequestParam("image") MultipartFile file,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        Key cardKey = KeyFactory.createKey(elementSchoolkey, CardDO.class.getSimpleName(), cardId);
        CardDO card = pm.getObjectById(CardDO.class, cardKey);
        card.setDefinition(definition);

//        Map map = engine.parse(definition);
//        builder.buildCard(elementSchool, card, map);
//        pm.makePersistent(card);

        if (file.getBytes().length > 0) {
            CardDescriptorDO descriptor = card.getDescriptor();
            descriptor.setImageData(file.getBytes());
            pm.makePersistent(descriptor);
        }

        pm.makePersistent(card);
        PersistenceHelper.doTransaction();
        modelMap.put("card", card);
        return "card/card";
    }

    @RequestMapping(value = "/card/instantedit.*", method = RequestMethod.POST)
    public void edit(@RequestParam("packageId") Long packageId,
                     @RequestParam("elementSchoolId") Long elementSchoolId,
                     @RequestParam("cardId") Long cardId,
                     @RequestParam("image") MultipartFile file,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, CardDO.class.getSimpleName(), cardId);
        CardDO card = pm.getObjectById(CardDO.class, cardKey);

        CardDescriptorDO descriptor = card.getDescriptor();
        descriptor.setImageData(file.getBytes());
        pm.makePersistent(descriptor);

        pm.makePersistent(card);
        redirect("/game/board", request, response);
    }

    @RequestMapping(value = "/card/add.*", method = RequestMethod.POST)
    public String add(@RequestParam("packageId") Long packageId,
                      @RequestParam("elementSchoolId") Long elementSchoolId,
                      @RequestParam("cardName") String cardName,
                      ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        ElementSchoolDO elementSchool = pm.getObjectById(ElementSchoolDO.class, elementSchoolkey);

        CardDO card = new CardDO(cardName, elementSchool, "");
        pm.makePersistent(card);

        CardDescriptorDO descriptor = new CardDescriptorDO(card, BoardContext.getLocale());
        descriptor.setName(cardName);
        card.addDescriptor(descriptor);
        pm.makePersistent(card);

        PersistenceHelper.doTransaction();
        modelMap.put("card", card);
        return "card/card";
    }

    @RequestMapping(value = "/card/delete.*", method = RequestMethod.POST)
    public String delete(@RequestParam("packageId") Long packageId,
                         @RequestParam("elementSchoolId") Long elementSchoolId,
                         @RequestParam("cardId") Long cardId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, CardDO.class.getSimpleName(), cardId);
        CardDO card = pm.getObjectById(CardDO.class, cardKey);
        pm.deletePersistent(card);
        PersistenceHelper.doTransaction();
        modelMap.put("package", pm.getObjectById(PackageDO.class, packageKey));
        return "card/package";
    }
}
