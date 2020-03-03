package edu.pucmm.api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import edu.pucmm.data.Assistant;
import edu.pucmm.service.AssistantService;
import edu.pucmm.utils.RestResponse;

import java.io.*;
import java.util.List;

import static com.sun.org.apache.xml.internal.serialize.LineSeparator.Web;

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

    @GET
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    @Path("/allEmployeeReport")
    public Response exportAllEmployee() {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os)); 
                writeHeader(writer, "ID", "NOMBRE", "APELLIDO", "DOCUMENTO_ID", "EMAIL", "DESCRIPCION_CATEGORIA_PARTICIPANTE", "DESCRIPCION_FORMA_PAGO");
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
                    writer.write("\n");
                }
                writer.flush();
                writer.close();
            }
        };

        ResponseBuilder response = Response.ok(stream);
        response.header("Content-Disposition", "attachment; filename=\"testFile_file.csv\"");
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