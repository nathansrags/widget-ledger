package com.widget.ledger.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.widget.ledger.web.common.util.Constants;

@Controller
public class WelcomeController {
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		return new ModelAndView("welcome");
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = Constants.NavigationConstants.SLASH_LOGIN, method = RequestMethod.GET)
	public String loginPage(ModelMap model) {
		return Constants.NavigationConstants.LOGIN;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,
			HttpServletResponse response) {
		final Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginFailure(HttpServletRequest request,
			HttpServletResponse response) {
		final Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?error";
	}
}
