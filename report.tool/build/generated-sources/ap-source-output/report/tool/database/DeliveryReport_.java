package report.tool.database;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeliveryReport.class)
public abstract class DeliveryReport_ {

	public static volatile SingularAttribute<DeliveryReport, String> contentId;
	public static volatile SingularAttribute<DeliveryReport, String> originator;
	public static volatile SingularAttribute<DeliveryReport, Integer> priority;
	public static volatile SingularAttribute<DeliveryReport, String> subjectId;
	public static volatile SingularAttribute<DeliveryReport, String> contentCorrelatorIA5String;
	public static volatile SingularAttribute<DeliveryReport, Integer> nonDeliveryDiagnosticCode;
	public static volatile SingularAttribute<DeliveryReport, String> suplementInfo;
	public static volatile SingularAttribute<DeliveryReport, String> encodeInfomationType;
	public static volatile SingularAttribute<DeliveryReport, String> arrivalTime;
	public static volatile SingularAttribute<DeliveryReport, Integer> nonDeliveryReasonCode;
	public static volatile SingularAttribute<DeliveryReport, String> recipient;
	public static volatile SingularAttribute<DeliveryReport, Integer> id;
	public static volatile SingularAttribute<DeliveryReport, Integer> contentType;

}

