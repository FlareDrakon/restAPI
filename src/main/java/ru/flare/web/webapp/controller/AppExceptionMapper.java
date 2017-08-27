package ru.flare.web.webapp.controller;

import com.google.inject.Singleton;
import ru.flare.web.webapp.RequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class AppExceptionMapper implements ExceptionMapper<RequestException> {
    @Override
    public Response toResponse(RequestException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage()).build();
    }
}
