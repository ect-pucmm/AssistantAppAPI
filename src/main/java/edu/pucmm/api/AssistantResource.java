package edu.pucmm.api;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import edu.pucmm.data.Assistant;
import edu.pucmm.exceptions.AssistantNotFound;
import edu.pucmm.service.AssistantService;
import edu.pucmm.utils.PaginationData;
import edu.pucmm.utils.RestResponse;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Path("/assistants")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AssistantResource {
    
    public static final Logger LOG = LoggerFactory.getLogger(AssistantResource.class.getName());

    @Inject
    AssistantService assistantService;

    @GET
    @Path("/participants")
    public Response getParticipants(@QueryParam String name, @QueryParam Integer page, @QueryParam Integer limit) {
        page = page != null ? (page > 0 ? page - 1 : 0) : 0;
        limit = limit == null ? 10 : limit;
        
        LOG.debug("Page: {}", page);
        LOG.debug("Limit: {}", limit);
        
        PaginationData paginationData = assistantService.getParticipantsByNombreOrApellido(page, limit, name);

        return Response.ok(RestResponse.toResponse(paginationData, "Listado de Participantes", false, 200)).build();
    }
    
    @GET
    @Path("/assistants")
    public Response getAssistants(@QueryParam String name, @QueryParam Integer page, @QueryParam Integer limit) {
        page = page != null ? (page > 0 ? page - 1 : 0) : 0;
        limit = limit == null ? 10 : limit;

        LOG.debug("Page: {}", page);
        LOG.debug("Limit: {}", limit);

        PaginationData paginationData = assistantService.getAssistantsByNombreOrApellido(page, limit, name);

        return Response.ok(RestResponse.toResponse(paginationData, "Listado de Participantes", false, 200)).build();
    }

    @POST
    public Response saveAssistant(Assistant assistant) {
        Assistant assistantSave = assistantService.save(assistant);
        
        return Response.status(201).entity(RestResponse.toResponse(assistantSave, "Guardado correctamente", false, 201)).build();
    }
    
    @PUT
    public Response assistAssistant(Assistant assistant) {
//        throw new AssistantNotFound("Error en el sistema");

        assistant = assistantService.assistAssisant(assistant);
        
        return Response.status(201).entity(RestResponse.toResponse(assistant, "Editado correctamente", false, 201)).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    @Path("/report")
    public Response exportAllEmployee() {
        StreamingOutput stream = os -> {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writeHeader(writer, "ID", "NOMBRE", "APELLIDO", "DOCUMENTO_ID", "EMAIL", "DESCRIPCION_CATEGORIA_PARTICIPANTE", "DESCRIPCION_FORMA_PAGO", "ASISTIO");
            for (Assistant assistant : assistantService.getAssistants()) {
                writer.write(assistant.idUser != null ? assistant.idUser : "");
                writer.write(",");
                writer.write(assistant.nombre != null ? assistant.nombre : "");
                writer.write(",");
                writer.write(assistant.apellido != null ? assistant.apellido : "");
                writer.write(",");
                writer.write(assistant.documentId != null ? assistant.documentId : "");
                writer.write(",");
                writer.write(assistant.email != null ? assistant.email : "");
                writer.write(",");
                writer.write(assistant.descriptionCategoryCompetitor != null ? assistant.descriptionCategoryCompetitor : "");
                writer.write(",");
                writer.write(assistant.paymentMethod != null ? assistant.paymentMethod : "");
                writer.write(",");
                writer.write(assistant.assist ? "SI" : "NO");
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        };

        ResponseBuilder response = Response.ok(stream);
        response.header("Content-Disposition", "attachment; filename=\"report.csv\"");
        return response.build();
    }


    public void writeHeader(Writer writer, String... columns) throws IOException {
        for (String aux : columns) {
            writer.write(aux);
            writer.write(",");
        }
        writer.write("\n");
    }
}