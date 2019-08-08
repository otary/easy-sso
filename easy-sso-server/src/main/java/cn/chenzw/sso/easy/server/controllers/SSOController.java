package cn.chenzw.sso.easy.server.controllers;

import cn.chenzw.sso.easy.server.exception.SSOException;
import cn.chenzw.sso.easy.server.support.AbstractSSOTemplate;
import cn.chenzw.sso.easy.server.support.SSOTemplateFactory;
import cn.chenzw.sso.easy.server.utils.SSOUtils;
import cn.chenzw.toolkit.http.HttpHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/itm-sso")
public class SSOController {

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";



    @RequestMapping
    public void entrance(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        HttpHolder.init(req, resp);

        try {
            AbstractSSOTemplate template = SSOTemplateFactory.autoGetTemplate();
            template.dispach();
        } catch (SSOException e) {
            resp.getWriter().print(SSOUtils.buildHtmlMsg("单点异常", e.getMessage()));
        } catch (Exception e) {
            resp.getWriter().print(SSOUtils.buildHtmlMsg("单点异常[ex]", e.getMessage()));
        }
    }


}
