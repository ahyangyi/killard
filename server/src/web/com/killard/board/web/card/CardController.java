package com.killard.board.web.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.descriptor.MetaCardDescriptorDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.context.BoardContext;
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

    @RequestMapping(value = "/card.*", method = RequestMethod.GET)
    public String edit(@RequestParam("packageId") Long packageId,
                       @RequestParam("elementSchoolId") Long elementSchoolId,
                       @RequestParam("cardId") Long cardId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key packageKey = KeyFactory.createKey(PackageDO.class.getSimpleName(), packageId);
        Key elementSchoolkey =
                KeyFactory.createKey(packageKey, ElementSchoolDO.class.getSimpleName(), elementSchoolId);
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardId);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        modelMap.put("board", card);
        return "board/board";
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

        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardId);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        card.setDefinition(definition);

//        Map map = engine.parse(definition);
//        builder.buildCard(elementSchool, board, map);
//        pm.makePersistent(board);

        if (file.getBytes().length > 0) {
            MetaCardDescriptorDO descriptor = card.getDescriptor();
            descriptor.setImageData(file.getBytes());
            pm.makePersistent(descriptor);
        }

        pm.makePersistent(card);
        PersistenceHelper.doTransaction();
        modelMap.put("board", card);
        return "board/board";
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
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardId);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);

        MetaCardDescriptorDO descriptor = card.getDescriptor();
        descriptor.setImageData(file.getBytes());
        pm.makePersistent(descriptor);

        pm.makePersistent(card);
        redirect("/game/game", request, response);
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

        MetaCardDO card = new MetaCardDO(cardName, elementSchool, "");
        pm.makePersistent(card);

        MetaCardDescriptorDO descriptor = new MetaCardDescriptorDO(card, BoardContext.getLocale());
        descriptor.setName(cardName);
        card.addDescriptor(descriptor);
        pm.makePersistent(card);

        PersistenceHelper.doTransaction();
        modelMap.put("board", card);
        return "board/board";
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
        Key cardKey = KeyFactory.createKey(elementSchoolkey, MetaCardDO.class.getSimpleName(), cardId);
        MetaCardDO card = pm.getObjectById(MetaCardDO.class, cardKey);
        pm.deletePersistent(card);
        PersistenceHelper.doTransaction();
        modelMap.put("package", pm.getObjectById(PackageDO.class, packageKey));
        return "board/package";
    }
}
