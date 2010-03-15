package com.killard.board.web.rss;

import com.killard.board.web.controller.BasicController;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
public class RSSController extends BasicController {

    @RequestMapping(value = "/rss.xml", method = {RequestMethod.GET, RequestMethod.POST})
    public void rss(@RequestParam(value = "rss", required = false, defaultValue = "rss_2.0") String rss,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/rss+xml");
        output(rss, response);
    }

    protected void output(String type, HttpServletResponse response) throws Exception {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType(type);

        feed.setTitle("Killard Game Packages");
        feed.setLink("http://www.killard.com");
        feed.setDescription("This feed contains new publish of killard record.");

        List<SyndEntry> entries = new LinkedList<SyndEntry>();
        SyndEntry entry;
        SyndContent description;

        entry = new SyndEntryImpl();
        entry.setTitle("Killard");
        entry.setLink("http://www.killard.com");
        entry.setPublishedDate(new Date());
        description = new SyndContentImpl();
        description.setType("text/plain");
        description.setValue("First package of killard.");
        entry.setDescription(description);
        entries.add(entry);

        feed.setEntries(entries);

        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed, response.getWriter());
    }
}
