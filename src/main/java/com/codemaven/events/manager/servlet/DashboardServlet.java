package com.codemaven.events.manager.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.model.CarouselDisplayItem;
import com.codemaven.events.servlet.ServletBase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/manager")
@Slf4j
public class DashboardServlet extends ServletBase
{
	private ServiceFactory serviceFactory;
	
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		List<CarouselDisplayItem> carousel = new ArrayList<>();
		CarouselDisplayItem displayItem = new CarouselDisplayItem();
		displayItem.setAltText("CM Banner");
		displayItem.setUrl("cm-banner.png");
		carousel.add(displayItem);
		carousel.add(displayItem);
		carousel.add(displayItem);
		req.setAttribute("carousel", carousel);
		req.setAttribute("title", "Dashboard");
		displayPage(req, resp, "dashboard.jsp");
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.DASHBOARD;
	}
}
