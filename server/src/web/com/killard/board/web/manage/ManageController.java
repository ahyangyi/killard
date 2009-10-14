package com.killard.board.web.manage;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.users.UserServiceFactory;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RuleDO;
import com.killard.board.jdo.board.descriptor.ElementSchoolDescriptorDO;
import com.killard.board.jdo.board.descriptor.MetaCardDescriptorDO;
import com.killard.board.jdo.board.descriptor.PackageDescriptorDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.jdo.game.BoardDO;
import com.killard.board.parser.ScriptEngine;
import com.killard.board.web.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
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
public class ManageController extends BasicController {

    private static Key defaultPackageKey;

    private static String baseDirectory;

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

    static {
        Queue queue = QueueFactory.getQueue("rss-fetch");
        queue.add(TaskOptions.Builder.url("/cron/sync.xml"));
    }

    @RequestMapping(value = "/manage/task.*", method = RequestMethod.GET)
    public void setupTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Queue queue = QueueFactory.getQueue("rss-fetch");
        queue.add(TaskOptions.Builder.url("/cron/sync.xml"));
        redirect("/index", request, response);
    }

    @RequestMapping(value = "/manage/clearboards.*", method = RequestMethod.GET)
    public void clearAllBoards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Extent<BoardDO> extent = PersistenceHelper.getPersistenceManager().getExtent(BoardDO.class);
        for (BoardDO board : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(board);
            PersistenceHelper.doTransaction();
        }
        extent.closeAll();
        redirect("/game/list", request, response);
    }

    @RequestMapping(value = "/manage/clearpackages.*", method = RequestMethod.GET)
    public void clearAllPackages(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Extent<PackageDO> extent = pm.getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(pack);
            PersistenceHelper.doTransaction();
        }
        extent.closeAll();
        redirect("/game/list", request, response);
    }

    @RequestMapping(value = "/manage/reset.*", method = RequestMethod.GET)
    public void reset(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<PackageDO> extent = pm.getExtent(PackageDO.class);
        for (PackageDO pack : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(pack);
            PersistenceHelper.doTransaction();
        }
        extent.closeAll();

        PackageDO pack = new PackageDO("Orions", UserServiceFactory.getUserService().getCurrentUser());
        pack.setPublished(true);
        pack.setOpen(true);
        pm.makePersistent(pack);

        PackageDescriptorDO desc = new PackageDescriptorDO(pack, Locale.ENGLISH);
        desc.setName("Orions");
        desc.setDescription("Orions Cards");
        pm.makePersistent(desc);

        baseDirectory = "WEB-INF/orions/";

        File ruleFile = new File(baseDirectory + "Rule.js");
        RuleDO rule = builder.buildRule(pack, engine.parse(ruleFile));
        rule.setDefinition(getString(ruleFile));
        pm.makePersistent(rule);

        defaultPackageKey = pack.getKey();

        redirect("/manage/load", request, response);
    }

    @RequestMapping(value = "/manage/bang.*", method = RequestMethod.GET)
    public void bang(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        PackageDO pack = new PackageDO("Bang!", UserServiceFactory.getUserService().getCurrentUser());
        pack.setPublished(true);
        pack.setOpen(true);
        pm.makePersistent(pack);

        PackageDescriptorDO desc = new PackageDescriptorDO(pack, Locale.ENGLISH);
        desc.setName("Bang!");
        desc.setDescription("Bang!");
        pm.makePersistent(desc);

        baseDirectory = "WEB-INF/bang/";

        File ruleFile = new File(baseDirectory + "Rule.js");
        RuleDO rule = builder.buildRule(pack, engine.parse(ruleFile));
        rule.setDefinition(getString(ruleFile));
        pm.makePersistent(rule);

        defaultPackageKey = pack.getKey();

        redirect("/manage/load", request, response);
    }

    @RequestMapping(value = "/manage/load.*", method = RequestMethod.GET)
    public void loadCards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (defaultPackageKey == null) redirect("/game/manage/reset", request, response);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        PackageDO pack = pm.getObjectById(PackageDO.class, defaultPackageKey);

        Set<String> elementSchools = new HashSet<String>();
        for (ElementSchoolDO elementSchool : pack.getElementSchools()) {
            elementSchools.add(elementSchool.getDescriptor().getName());
        }

        File dir = new File(baseDirectory);
        for (File sub : dir.listFiles()) {
            if (sub.isDirectory() && !elementSchools.contains(sub.getName())) {
                ElementSchoolDO elementSchool = new ElementSchoolDO(sub.getName(), pack);
                pm.makePersistent(elementSchool);

                ElementSchoolDescriptorDO elementDesc =
                        new ElementSchoolDescriptorDO(elementSchool, BoardContext.getLocale());
                elementDesc.setName(sub.getName());
                elementDesc.setDescription("Element school: " + sub.getName());
                pm.makePersistent(elementDesc);
                PersistenceHelper.doTransaction();

                for (File file : sub.listFiles()) {
                    if (file.getName().endsWith(".js")) {
                        String name = file.getName().substring(0, file.getName().length() - 3);
                        MetaCardDO card = new MetaCardDO(name, elementSchool, getString(file));
                        elementSchool.addCard(card);
                        pm.makePersistent(elementSchool);
                        builder.buildCard(elementSchool, card, engine.parse(file));
                        pm.makePersistent(card);
                        if (card.getDescriptor(BoardContext.getLocale()) == null) {
                            MetaCardDescriptorDO descriptor = new MetaCardDescriptorDO(card, BoardContext.getLocale());
                            descriptor.setName(name);
                            pm.makePersistent(descriptor);
                        }
                        File imageFile = new File(name + ".jpg");
                        if (imageFile.exists()) {
                            card.getDescriptor().setImageData(getBytes(imageFile));
                        }
                    }
                }
                redirect("/manage/load", request, response);
                return;
            }
        }
        redirect("/index", request, response);
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
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        FileReader reader = new FileReader(file);
        try {
            int c;
            while ((c = reader.read()) > -1) {
                buf.write(c);
            }
        } finally {
            reader.close();
        }
        byte[] result = buf.toByteArray();
        buf.close();
        return result;
    }
}
