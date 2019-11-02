package cn.chenzw.sso.easy.server.controllers;

import cn.chenzw.sso.easy.core.constants.SSOConstants;
import cn.chenzw.sso.easy.core.exception.SSOException;
import cn.chenzw.sso.easy.core.utils.SSOUtils;
import cn.chenzw.sso.easy.server.support.SSOTemplateFactory;
import cn.chenzw.toolkit.http.HttpHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping(SSOConstants.DEFAULT_SSO_HANDLE_MAPPING)
public class SSOController {

    private static final Logger logger = LoggerFactory.getLogger(SSOController.class);

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    @RequestMapping
    public void entrance(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        HttpHolder.init(req, resp);

        try {
            SSOTemplateFactory.autoGetTemplate().dispach();
        } catch (SSOException e) {
            logger.error("单点异常！", e);
            resp.getWriter().print(SSOUtils.buildHtmlMsg("单点异常", e.getMessage()));
        } catch (Exception e) {
            logger.error("单点异常ex！", e);
            resp.getWriter().print(SSOUtils.buildHtmlMsg("单点异常[ex]", e.getMessage()));
        }
    }


}
