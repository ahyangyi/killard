package com.killard.board.web.filter;

import com.killard.board.jdo.PersistenceHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class PersistenceManagerFilter implements Filter {

    private final Logger log = Logger.getLogger("Killard");

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            PersistenceHelper.open();
            PersistenceHelper.getPersistenceManager().currentTransaction().begin();
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException e) {
            PersistenceHelper.rollback();
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } catch (ServletException e) {
            PersistenceHelper.rollback();
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } catch (Throwable e) {
            PersistenceHelper.rollback();
            log.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        } finally {
            PersistenceHelper.close();
        }
    }

    public void destroy() {
    }
}
