package edu.pucmm.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import edu.pucmm.data.Assistant;
import io.quarkus.runtime.StartupEvent;

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

    if (findAssis == null) {
        throw new RuntimeException("Ya el participante se enuentra registrado.");
    }

    assistant.persistAndFlush();
    return assistant;
  }

  @Transactional
  public void register() {
      Assistant assistant = new Assistant();
      assistant.idUser = "00000001";
      assistant.nombre = "Juan Diego";
      assistant.apellido = "Lopez";
      assistant.descriptionCategoryCompetitor = "Estudiante";
      assistant.paymentMethod = "Efectivo";
      assistant.persistAndFlush();
  }
}