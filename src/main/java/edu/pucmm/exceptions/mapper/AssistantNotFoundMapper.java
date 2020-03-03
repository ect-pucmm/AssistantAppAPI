package edu.pucmm.exceptions.mapper;

import edu.pucmm.exceptions.AssistantNotFound;
import edu.pucmm.utils.RestResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AssistantNotFoundMapper implements ExceptionMapper<AssistantNotFound> {
    @Override
    public Response toResponse(AssistantNotFound exception) {
        return Response.status(404)
            .entity(RestResponse.toResponse(null, exception.getMessage(), true, 404))
            .build();
    }
}
