package com.codemaven.manager.db.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Events;
import com.codemaven.generated.tables.pojos.Sessions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class EventDao
{
	private final DSLContext dsl;

	public List<Events> fetchEventsAfterDate(final LocalDateTime after)
	{
		return dsl.selectFrom(Tables.EVENTS)
				.where(Tables.EVENTS.EVENT_DATE.ge(after))
				.fetchInto(Events.class);
	}

	public List<Events> fetchEventsBetweenDates(final LocalDateTime before, final LocalDateTime after)
	{
		return dsl.selectFrom(Tables.EVENTS)
				.where(Tables.EVENTS.EVENT_DATE.ge(before))
				.and(Tables.EVENTS.EVENT_DATE.lt(after))
				.fetchInto(Events.class);
	}

	public Events fetchEventById(final int eventId)
	{
		return dsl.selectFrom(Tables.EVENTS)
				.where(Tables.EVENTS.ID.eq(eventId))
				.fetchOneInto(Events.class);
	}
	
	public List<Sessions> fetchSessionsByEventId(final int eventId)
	{
		return dsl.selectFrom(Tables.SESSIONS)
				.where(Tables.SESSIONS.EVENT_ID.eq(eventId))
				.fetchInto(Sessions.class);
	}
}