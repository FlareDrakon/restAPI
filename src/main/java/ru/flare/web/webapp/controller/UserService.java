package ru.flare.web.webapp.controller;

import ru.flare.web.dao.Dao;
import ru.flare.web.dto.Transaction;
import ru.flare.web.dto.User;
import ru.flare.web.webapp.RequestException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;

/**
 * User: Renato
 */
@Path("{user}/{id}")
public class UserService {

	private Dao<User> dao;

	@Inject
	public UserService(Dao<User> dao ) {
		this.dao = dao;
	}

	@POST
	@Path( "/transaction" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction transfer( @PathParam( "id" ) String userId,  Transaction query) {
		System.out.println( "Requested to get ID = " + userId );
		//need transaction here but it's only for example so ignore it
		User actor = dao.getById( userId );
		User receiver = dao.getById( query.toUserId );
		if ( actor == null ) {
			throw new RequestException("user not found");
		}

		if ( receiver == null ) {
			throw new RequestException("receiver not found");
		}
		try {
			if(actor.getBalance() < query.count) {
				throw new RequestException("no money");
			}
			actor.setBalance(actor.getBalance() - query.count);
			receiver.setBalance(Math.addExact(receiver.getBalance(), query.count));
		}
		catch (ArithmeticException e) {
			throw new RequestException("balance overflow - cancel");
		}
		dao.save(receiver, actor);
		return query;
	}
}
