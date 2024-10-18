package com.mischenkov.sudoku.listener;

import com.mischenkov.sudoku.service.LocalSudokuService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import static com.mischenkov.sudoku.listener.ContextVariable.SUDOKU_SERVICE;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(SUDOKU_SERVICE, new LocalSudokuService());
        initLog4J(sce.getServletContext());
    }

    private void initLog4J(ServletContext servletContext) {
        PropertyConfigurator.configure(
                servletContext.getRealPath("WEB-INF/log4j.properties")
        );
        LOG.info("Log4j init");
    }
}
