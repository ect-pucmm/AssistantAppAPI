package edu.pucmm.exceptions.mapper;

import edu.pucmm.exceptions.AssistantExists;
import edu.pucmm.exceptions.AssistantNotFound;
import edu.pucmm.utils.RestResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AssistantExistsMapper implements ExceptionMapper<AssistantExists> {
    @Override
    public Response toResponse(AssistantExists exception) {
        return Response.status(400)
            .entity(RestResponse.toResponse(null, exception.getMessage(), true, 404))
            .build();
    }
}
