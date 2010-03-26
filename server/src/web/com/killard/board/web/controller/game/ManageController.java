package com.killard.board.web.controller.game;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.killard.board.card.Element;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageStatus;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.RoleGroupDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.parser.ScriptEngine;
import com.killard.board.web.cache.CacheInstance;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@RequestMapping("/manage")
public class ManageController extends BasicController {

    private static Key defaultPackageKey;

    private static String baseDirectory;

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    static {
//        Queue queue = QueueFactory.getQueue("rss-fetch");
//        queue.add(TaskOptions.Builder.url("/cron/sync.xml"));
    }

    @RequestMapping(value = "/clear/board", method = RequestMethod.GET)
    public void clearBoards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<BoardDO> extent = pm.getExtent(BoardDO.class);
        for (BoardDO bundle : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(bundle);
            PersistenceHelper.commit();
        }
        extent.closeAll();
        CacheInstance.getInstance().getBoardCache().clear();

        redirect("/game", request, response);
    }

    @RequestMapping(value = "/clear/element", method = RequestMethod.GET)
    public void clearElements(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<ElementDO> elementExtent = pm.getExtent(ElementDO.class);
        for (ElementDO record : elementExtent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(record);
            PersistenceHelper.commit();
        }
        elementExtent.closeAll();

        redirect("/game", request, response);
    }

    @RequestMapping(value = "/clear/card", method = RequestMethod.GET)
    public void clearCards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<MetaCardDO> cardExtent = pm.getExtent(MetaCardDO.class);
        for (MetaCardDO record : cardExtent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(record);
            PersistenceHelper.commit();
        }
        cardExtent.closeAll();

        redirect("/game", request, response);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public void clearPackages(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<PackageBundleDO> bundleExtent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : bundleExtent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(bundle);
            PersistenceHelper.commit();
        }
        bundleExtent.closeAll();
        CacheInstance.getInstance().getBoardCache().clear();

        redirect("/game", request, response);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public void reset(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<PackageBundleDO> extent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(bundle);
            PersistenceHelper.commit();
        }
        extent.closeAll();
        CacheInstance.getInstance().getBoardCache().clear();

        PackageBundleDO bundle = new PackageBundleDO("animals");
        pm.makePersistent(bundle);

        baseDirectory = "WEB-INF/orions/";

        PackageDO draft = bundle.draft();
        defaultPackageKey = draft.getKey();
        draft.newDescriptor(BoardContext.getLocale(), "Animals In Danger", "This game talks about animals.");

        File ruleFile = new File(baseDirectory + "Rule.json");
        builder.buildRule(draft, engine.parse(ruleFile));

        List<AttributeHandler> handlers = new ArrayList<AttributeHandler>();
        RoleDO role = draft.newRole("test", handlers, handlers, handlers);
        PersistenceHelper.commit();
        role.newDescriptor(BoardContext.getLocale(), "test", "Test Role");
        RoleGroupDO group = draft.newRoleGroup();
        PersistenceHelper.commit();
        group = pm.getObjectById(RoleGroupDO.class, group.getKey());
        group.addRole(role);
        group.addRole(role);
        redirect("/game/manage/load", request, response);
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public void loadCards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (defaultPackageKey == null) redirect("/game/manage/reset", request, response);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        PackageDO draft = pm.getObjectById(PackageDO.class, defaultPackageKey);

        Set<String> elements = new HashSet<String>();
        for (Element element : draft.getElements()) {
            elements.add(element.getName());
        }

        File dir = new File(baseDirectory);
        for (File sub : dir.listFiles()) {
            if (sub.isDirectory() && !elements.contains(sub.getName())) {
                ElementDO element = draft.newElement(sub.getName());
                element.newDescriptor(BoardContext.getLocale(), sub.getName(), sub.getName());
                pm.makePersistent(element);
                PersistenceHelper.commit();

                for (File file : sub.listFiles()) {
                    if (file.getName().endsWith(".json")) {
                        String name = file.getName().substring(0, file.getName().length() - 5);
                        MetaCardDO card = element.newCard(name);
                        pm.makePersistent(card);
                        PersistenceHelper.commit();
                        builder.buildCard(element, card, engine.parse(file));
                        if (card.getDescriptor(BoardContext.getLocale()) == null) {
                            card.newDescriptor(BoardContext.getLocale(), name, "");
                        }
                        File imageFile = new File(baseDirectory + sub.getName() + "/" + name + ".png");
                        if (imageFile.exists()) {
                            card.setImageFormat(Image.Format.PNG);
                            card.setImageData(getBytes(imageFile));
                        }
                        PersistenceHelper.commit();
                    }
                }
                redirect("/game/manage/load", request, response);
                return;
            }
        }
        redirect("/game/manage/publish", request, response);
    }

    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public void publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (defaultPackageKey == null) redirect("/manage/reset", request, response);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, defaultPackageKey.getParent());

        bundle.setStatus(PackageStatus.PUBLIC);
        bundle.release();
        
        redirect("/game", request, response);
    }

    protected String getString(File file) throws IOException {
        StringBuilder buf = new StringBuilder();
        FileReader reader = new FileReader(file);
        try {
            int c;
            while ((c = reader.read()) > -1) {
                buf.append((char) c);
            }
        } finally {
            reader.close();
        }
        return buf.toString();
    }

    protected byte[] getBytes(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            in.close();
        }
        return out.toByteArray();
    }
}
