package com.codemaven.events.manager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.service.TracksService;
import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.model.CarouselDisplayItem;
import com.codemaven.events.servlet.ServletBase;
import com.codemaven.events.util.StringUtil;
import com.codemaven.generated.tables.pojos.Locations;
import com.codemaven.generated.tables.pojos.Tracks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/manager/tracks")
@Slf4j
public class TracksServlet extends ServletBase
{
	private static final String JSP_PATH = "tracks";
	private ServiceFactory serviceFactory;
	
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			doCmd(req, resp);
		}
		catch (Exception e)
		{
			log.error("Error processing cmd: " + e.getMessage(), e);
			displayError(req, resp, "Uh-Oh, something went wrong!");
		}
	}
	
	private void doCmd(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String cmd = getCmd(req);
		if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "list"))
		{
			doList(req, resp);
		}
		else
		{
			log.debug("Tried accessing tracks with cmd: " + cmd);
			displayError(req, resp, "The command used to access this page is invalid");
		}
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		TracksService service = serviceFactory.getInstance(ServiceType.TRACK, TracksService.class);

		Map<Tracks, Locations> igtc = service.fetchTracksLocationsBySet("IGTC");
		log.info(igtc.size() + " IGTC tracks loaded");
		req.setAttribute("igtc", igtc);
		
		Map<Tracks, Locations> gtwc2019 = service.fetchTracksLocationsBySet("GTWC 2019");
		log.info(gtwc2019.size() + " GTWC 2019 tracks loaded");
		req.setAttribute("gtwc2019", gtwc2019);
		
		Map<Tracks, Locations> gtwc2018 = service.fetchTracksLocationsBySet("GTWC 2018");
		log.info(gtwc2018.size() + " GTWC 2018 tracks loaded");
		req.setAttribute("gtwc2018", gtwc2018);
		
		List<CarouselDisplayItem> carousel = new ArrayList<>();
		CarouselDisplayItem displayItem = new CarouselDisplayItem();
		displayItem.setAltText("Audi R8 Tail");
		displayItem.setUrl("audi-tail.png");
		carousel.add(displayItem);
		CarouselDisplayItem displayItem2 = new CarouselDisplayItem();
		displayItem2.setAltText("Nissan GTR Tail");
		displayItem2.setUrl("gtr-tail-pits.png");
		carousel.add(displayItem2);
		req.setAttribute("carousel", carousel);
		
		req.setAttribute("title", "Tracks");
		displayPage(req, resp, JSP_PATH+"/list.jsp");
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.TRACKS;
	}
}
