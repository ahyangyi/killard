package com.killard.board.web.filter;

import com.killard.board.jdo.PersistenceHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

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

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            PersistenceHelper.openSession();
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException e) {
            PersistenceHelper.rollback();
            throw e;
        } catch (ServletException e) {
            PersistenceHelper.rollback();
            throw e;
        } catch (Throwable e) {
            PersistenceHelper.rollback();
            e.printStackTrace();
        } finally {
            PersistenceHelper.closeSession();
        }
    }

    public void destroy() {
    }
}
