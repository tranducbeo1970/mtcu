package com.attech.amhs.mtcu.database.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GatewayOut.class)
public abstract class GatewayOut_ {

	public static volatile SingularAttribute<GatewayOut, String> address;
	public static volatile SingularAttribute<GatewayOut, String> amhsid;
	public static volatile SingularAttribute<GatewayOut, Long> msgid;
	public static volatile SingularAttribute<GatewayOut, String> text;
	public static volatile SingularAttribute<GatewayOut, String> originator;
	public static volatile SingularAttribute<GatewayOut, Integer> priority;
	public static volatile SingularAttribute<GatewayOut, String> filingTime;
	public static volatile SingularAttribute<GatewayOut, String> optionalHeading;
	public static volatile SingularAttribute<GatewayOut, Date> insertedTime;

}

