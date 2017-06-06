package com.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{
	/*登录认证，xml中已注册*/
	private String noURI;
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		Object user = req.getSession().getAttribute("login_user");
		String uri = req.getRequestURI();
		if(noURI != null && !"".equals(noURI)){
			String[] noURIArray = noURI.split(";");
			for (String string : noURIArray) {
				if(uri.endsWith(string)){
					chain.doFilter(request, response);
					return;
				}
			}
		}
		if(user !=null){
				chain.doFilter(request, response);
		}else{
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("login");
		}
	}

	@Override/*初始化传参，获取不需要拦截的资源*/
	public void init(FilterConfig fConfig) throws ServletException {
		noURI = fConfig.getInitParameter("noURI");
	}

}
