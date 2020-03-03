package edu.pucmm.data;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "assistant")
public class Assistant extends PanacheEntity {

  // 0000000001|Stephanie|Willis|3138838020|eswillis@gmail.com|Profesor de Canadá/EEUU/Europa|Pago presencial en efectivo el día 19 de marzo

  // a. ID
	// b. Nombres
	// c. Apellidos
	// d. DocuimentoID
	// e. EmailParticipante
	// f. DescripcionCategoriaParticipante
  // g. DescripciónFormaPago

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

}
