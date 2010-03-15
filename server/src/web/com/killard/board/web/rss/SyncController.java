package com.killard.board.web.rss;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class SyncController extends BasicController {

    @RequestMapping(value = "/cron/sync.xml", method = {RequestMethod.GET, RequestMethod.POST})
    public void sync(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("sync called");
        Queue queue = QueueFactory.getQueue("rss-fetch");
        queue.add(TaskOptions.Builder.url("/cron/sync.xml"));
    }

}
