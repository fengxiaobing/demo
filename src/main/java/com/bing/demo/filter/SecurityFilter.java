package com.bing.demo.filter;

import com.bing.demo.utils.Security;
import com.google.gson.JsonObject;
import org.hibernate.internal.util.StringHelper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author liheng 2018/06/20
 * 权限过滤器，现阶段沿用之前的设置把用户信息放入session，后期考虑放入redis
 */
@Order(1)//@Order注解表示执行过滤顺序，值越小，越先执行
/**
 * 过滤器注解
 */
@WebFilter(filterName = "securityFilter", urlPatterns = "/*")
//@ConfigurationProperties(prefix="exclude")
public class SecurityFilter implements Filter {




    private String[] urls;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        try{
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            String requestUrl = httpRequest.getRequestURI();
            if(requestUrl.contains("swagger")
                    || requestUrl.contains("/commitUser")
                    || requestUrl.contains("/configuration/ui")
                    ||requestUrl.contains("/configuration/security")
                    ||requestUrl.contains("/v2/api-docs")
                    ||requestUrl.contains("/app/request")
                    ){
                filterChain.doFilter(servletRequest, servletResponse);
            }

            String token = httpRequest.getParameter("username");
            String body = getBody(httpRequest);
//              String token = httpRequest.getHeader("token");
            if(StringHelper.isEmpty(token)){
//                String body = getBody(httpRequest);
//                JsonObject requestContent = SJZXUtils.dealRequestReturnJson(body);
//                token = requestContent.get("token").getAsString();
//                servletRequest =getRequest(servletRequest,body);
            }

            //认证服务无法调用
//            if(null == tokenService){
//                httpResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//                return;
//            }
            //验证token是否合法
//            if(StringHelper.isEmpty(token) || !tokenService.validateToken(token)){
//                httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
//                return;
//            }

            //如果是web请求，则存入session中使用
            //if(WebRequestHelper.isWebClient(httpRequest)){
                HttpSession session = httpRequest.getSession();
                Object object = session.getAttribute(Security.SESSION_USER_KEY);
                if(null == object) {
//                        //如果自动注解service为null则采用下面方式
//                        if(null == userService){
//                            userService = (UserService) SpringContextUtils.getBean("userServiceImpl");
//                        }
//                        if(null == roleService){
//                            roleService = (RoleService) SpringContextUtils.getBean("RoleServleceImpl");
//                        }

//                        Map<String,Object> map = Jwt.validToken(token);
//                        Map data = (Map) JSONUtil.convertToStrict(map.get("data"),Map.class);
//                        String userId = StringHelper.objToString(data.get("uid"));
//                        User user = suserService.findById(userId);
//                        List<Role> roleCollection = sroleService.getRoleByUserId(userId, true);
//                        user.setRoleCollection(roleCollection);

//                        session.setAttribute(Security.SESSION_USER_KEY, user);
                }
            //}

            filterChain.doFilter(servletRequest, servletResponse);
        }catch(Exception e){
//            logger.error(e.getMessage());
        }
    }
    private String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        }finally {
            if(null != bufferedReader){
                bufferedReader.close();
            }
        }
        body = stringBuilder.toString();
        return body;
    }

    /**
     * 将post解析过后的request进行封装改写
     * @param request
     * @param body
     * @return
     */
    private ServletRequest getRequest(ServletRequest request ,String body){
        String enctype = request.getContentType();
        if (StringHelper.isNotEmpty(enctype) && enctype.contains("application/json")){
            return new PostServletRequest((HttpServletRequest) request,body);
        }
        return request;
    }

    @Override
    public void destroy() {

    }

    public static void  main(String[] args ){
        String ab = "1314";
        System.out.println(ab.contains("341"));
    }
}
