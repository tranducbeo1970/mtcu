package com.attech.amhs.mtcu.database.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AddressConversionLog.class)
public abstract class AddressConversionLog_ {

	public static volatile SingularAttribute<AddressConversionLog, MessageConversionLog> messageConversionLog;
	public static volatile SingularAttribute<AddressConversionLog, String> address;
	public static volatile SingularAttribute<AddressConversionLog, Integer> reportRequest;
	public static volatile SingularAttribute<AddressConversionLog, Long> id;
	public static volatile SingularAttribute<AddressConversionLog, Integer> notifyRequest;
	public static volatile SingularAttribute<AddressConversionLog, Integer> mtaReportRequest;

}

