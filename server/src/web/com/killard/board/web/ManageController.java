package com.killard.board.web;

import com.google.appengine.api.datastore.Key;
import com.killard.board.card.ElementSchool;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.ElementSchoolDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageStatus;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.jdo.board.RoleGroupDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.parser.ScriptEngine;
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
public class ManageController extends BasicController {

    private static Key defaultPackageKey;

    private static String baseDirectory;

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

    static {
//        Queue queue = QueueFactory.getQueue("rss-fetch");
//        queue.add(TaskOptions.Builder.url("/cron/sync.xml"));
    }

    @RequestMapping(value = "/manage/clear.*", method = RequestMethod.GET)
    public void clearAllPackages(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Extent<PackageBundleDO> extent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(bundle);
            PersistenceHelper.doTransaction();
        }
        extent.closeAll();

        Extent<BoardDO> boardExtent = pm.getExtent(BoardDO.class);
        for (BoardDO board : boardExtent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(board);
            PersistenceHelper.doTransaction();
        }
        boardExtent.closeAll();
        redirect("/packages", request, response);
    }

    @RequestMapping(value = "/manage/reset.*", method = RequestMethod.GET)
    public void reset(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Extent<PackageBundleDO> extent = pm.getExtent(PackageBundleDO.class);
        for (PackageBundleDO bundle : extent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(bundle);
            PersistenceHelper.doTransaction();
        }
        extent.closeAll();

        Extent<BoardDO> boardExtent = pm.getExtent(BoardDO.class);
        for (BoardDO board : boardExtent) {
            PersistenceHelper.getPersistenceManager().deletePersistent(board);
            PersistenceHelper.doTransaction();
        }
        boardExtent.closeAll();

        PackageBundleDO bundle = new PackageBundleDO("Orions");
        pm.makePersistent(bundle);

        baseDirectory = "WEB-INF/orions/";
        defaultPackageKey = bundle.getKey();

        PackageDO draft = bundle.draft();
        draft.newDescriptor(BoardContext.getLocale(), "Orions", "Orions");

        File ruleFile = new File(baseDirectory + "Rule.json");
        builder.buildRule(draft, getString(ruleFile), engine.parse(ruleFile));

        List<AttributeHandler> handlers = new ArrayList<AttributeHandler>();
        RoleDO role = draft.newRole("test", "", handlers, handlers, handlers);
        RoleGroupDO group = draft.newRoleGroup();
        pm.makePersistent(group);
        PersistenceHelper.doTransaction();
        group.addRole(role);
        group.addRole(role);
        System.out.println("create group " + group.getKey() + " " + group.getRoleKeys().length);
        pm.makePersistent(group);
        PersistenceHelper.doTransaction();
        System.out.println("init group " + group.getKey() + " " + group.getRoleKeys().length);

        pm.makePersistent(bundle);

        redirect("/manage/load", request, response);
    }

    @RequestMapping(value = "/manage/load.*", method = RequestMethod.GET)
    public void loadCards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (defaultPackageKey == null) redirect("/manage/reset", request, response);

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, defaultPackageKey);
        PackageDO draft = bundle.getDraft();

        Set<String> elementSchools = new HashSet<String>();
        for (ElementSchool elementSchool : draft.getElementSchools()) {
            elementSchools.add(elementSchool.getName());
        }

        File dir = new File(baseDirectory);
        for (File sub : dir.listFiles()) {
            if (sub.isDirectory() && !elementSchools.contains(sub.getName())) {
                ElementSchoolDO elementSchool = draft.newElementSchool(sub.getName());
                elementSchool.newDescriptor(BoardContext.getLocale(), sub.getName(), sub.getName());

                for (File file : sub.listFiles()) {
                    if (file.getName().endsWith(".js")) {
                        String name = file.getName().substring(0, file.getName().length() - 3);
                        MetaCardDO card = elementSchool.newCard(name);
                        builder.buildCard(elementSchool, card, engine.parse(file));
                        if (card.getDescriptor(BoardContext.getLocale()) == null) {
                            card.newDescriptor(BoardContext.getLocale(), name, "");
                        }
                        File imageFile = new File(name + ".jpg");
                        if (imageFile.exists()) {
                        }
                    }
                }
                pm.makePersistent(elementSchool);
                redirect("/manage/load", request, response);
                return;
            }
        }
        redirect("/manage/publish", request, response);
    }

    @RequestMapping(value = "/manage/publish.*", method = RequestMethod.GET)
    public void publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (defaultPackageKey == null) redirect("/manage/reset", request, response);
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, defaultPackageKey);

        bundle.setStatus(PackageStatus.PUBLIC);
        bundle.release();
        pm.makePersistent(bundle);
        
        redirect("/packages", request, response);
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
