package com.attech.amhs.mtcu.database.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GatewayIn.class)
public abstract class GatewayIn_ {

	public static volatile SingularAttribute<GatewayIn, String> address;
	public static volatile SingularAttribute<GatewayIn, String> cpa;
	public static volatile SingularAttribute<GatewayIn, Long> msgid;
	public static volatile SingularAttribute<GatewayIn, Date> time;
	public static volatile SingularAttribute<GatewayIn, String> text;
	public static volatile SingularAttribute<GatewayIn, String> source;
	public static volatile SingularAttribute<GatewayIn, Integer> priority;

}

