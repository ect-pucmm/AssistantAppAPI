package edu.pucmm.data;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "assistant")
public class Assistant extends PanacheEntity {

  @Column(name = "id_user")
  public String idUser;

  @Column(name = "nombre")
  public String nombre;

  @Column(name = "apellido")
  public String apellido;

  @Column(name = "document_id")
  public String documentId;

  public String email;

  @Column(name = "description_category_competitor")
  public String descriptionCategoryCompetitor;

  @Column(name = "payment_method")
  public String paymentMethod;
  
  @Column(name = "assist", columnDefinition = "boolean default false")
  public boolean assist;

}
