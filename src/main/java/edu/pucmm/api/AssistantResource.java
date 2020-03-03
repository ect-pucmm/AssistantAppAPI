package edu.pucmm.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import edu.pucmm.data.Assistant;
import edu.pucmm.service.AssistantService;
import edu.pucmm.utils.RestResponse;

@Path("/assistants")
@Produces("application/json")
@Consumes("application/json")
public class AssistantResource {

    @Inject
    AssistantService assistantService;

    @POST
    public Response saveAssistant(Assistant assistant) {
        Assistant assistantSave = assistantService.save(assistant);
        return Response.status(201).entity(RestResponse.toResponse(assistantSave, "Guardado correctamente", false, 201)).build();
    }
}