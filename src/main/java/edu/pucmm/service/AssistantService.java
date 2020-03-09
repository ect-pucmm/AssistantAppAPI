package edu.pucmm.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import edu.pucmm.exceptions.AssistantExists;
import edu.pucmm.exceptions.AssistantNotFound;
import edu.pucmm.utils.Pagination;
import edu.pucmm.utils.PaginationData;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import edu.pucmm.data.Assistant;
import io.quarkus.runtime.StartupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class AssistantService {

    @Inject
    @ConfigProperty(name = "app.init.db", defaultValue = "false")
    boolean initDb;

    @Inject
    EntityManager em;

    void onStart(@Observes StartupEvent ev) {
        initDb();
    }

    public void initDb() {
        if (initDb) {
            register();
        }
    }

    public Assistant findByEmail(String email) {
        return Assistant.find("email", email).firstResult();
    }

    @Transactional
    public Assistant save(Assistant assistant) {

        Assistant findAssis = findByEmail(assistant.email);

        if (findAssis != null) {
            throw new AssistantNotFound("Ya el participante se enuentra registrado.");
        }

        assistant.persistAndFlush();
        return assistant;
    }
    
    @Transactional
    public Assistant assistAssisant(Assistant assistant) {
        
        if (assistant.email == null || assistant.email.isEmpty()) {
            throw new AssistantNotFound("Error en el sistema");
        }
        Assistant findAssis = findByEmail(assistant.email);

        if (findAssis.assist) {
            throw new AssistantExists("El participante está registrado");
        }

        findAssis.assist = true;
        findAssis.persistAndFlush();
        return findAssis;
    }

    public List<Assistant> getAssistants() {
        List<Assistant> assistants = Assistant.listAll();

        if (assistants == null) {
            new ArrayList<>();
        }

        return assistants;
    }

    @Transactional
    public PaginationData getParticipantsByNombreOrApellido(Integer page, Integer limit, String name) {
        Integer offset = page * limit;
        Stream<Assistant> streamAll = Assistant.streamAll();
        List<Assistant> assistants;
        List<Assistant> pagination;
        if (name != null) {
            assistants = streamAll
                .filter(n -> (n.nombre.toLowerCase().contains(name.toLowerCase()) || n.apellido.toLowerCase().contains(name.toLowerCase())) && !n.assist)
                .collect(Collectors.toList());

        } else {
            assistants = streamAll
                .filter(n -> !n.assist)
                .collect(Collectors.toList());
        }
        pagination = assistants.subList(
            Math.min(assistants.size(), offset),
            Math.min(assistants.size(), offset + limit));

        if (assistants == null) {
            new ArrayList<>();
        }

        return PaginationData.paginationResponse(pagination, assistants.size(), pagination.size(), page + 1, limit, offset);
    }

    @Transactional
    public PaginationData getAssistantsByNombreOrApellido(Integer page, Integer limit, String name) {
        Integer offset = page * limit;
        Stream<Assistant> streamAll = Assistant.streamAll();
        List<Assistant> assistants;
        List<Assistant> pagination;
        if (name != null) {
            assistants = streamAll
                .filter(n -> (n.nombre.toLowerCase().contains(name.toLowerCase()) || n.apellido.toLowerCase().contains(name.toLowerCase())) && n.assist)
                .collect(Collectors.toList());

        } else {
            assistants = streamAll
                .filter(n -> n.assist)
                .collect(Collectors.toList());
        }
        pagination = assistants.subList(
            Math.min(assistants.size(), offset),
            Math.min(assistants.size(), offset + limit));

        if (assistants == null) {
            new ArrayList<>();
        }

        return PaginationData.paginationResponse(pagination, assistants.size(), pagination.size(), page + 1, limit, offset);
    }

    @Transactional
    public void register() {
        Assistant assistant = new Assistant();
        assistant.idUser = "00000001";
        assistant.nombre = "Juan Diego";
        assistant.apellido = "Lopez";
        assistant.email = "juandiegolopezve@gmail.com";
        assistant.documentId = "00000001";
        assistant.descriptionCategoryCompetitor = "Estudiante";
        assistant.paymentMethod = "Efectivo";
        assistant.persistAndFlush();
    }
}
