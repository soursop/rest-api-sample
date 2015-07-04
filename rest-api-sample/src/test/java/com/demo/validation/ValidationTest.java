package com.demo.validation;

import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class ValidationTest {

    @Test
    public void testJsr303BeanValidator() throws ServletException, IOException {
//        ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();
//        servlet.setRelativeLocations(getClass(), "jsr-303-validation.xml");
//        servlet.init(new MockServletConfig("dispatcherServlet"));
// 
//        MockHttpServletRequest req = new MockHttpServletRequest("POST", "/user");
//        req.addParameter("id", "rednics"); // not null, min 4, max 10
//        req.addParameter("name", "신관영"); // not null, min 10
//        req.addParameter("password", "1qaz1234"); // not null, min 6, max 20
//        req.addParameter("age", "39"); // min 1, max 200
// 
//        MockHttpServletResponse res = new MockHttpServletResponse();
//        servlet.service(req, res);
// 
//        assertThat(servlet.getModelAndView().getViewName(), is("successView"));
    }

}
