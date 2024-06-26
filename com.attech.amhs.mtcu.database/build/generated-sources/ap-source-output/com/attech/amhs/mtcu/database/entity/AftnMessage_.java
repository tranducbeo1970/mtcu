package com.attech.amhs.mtcu.database.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AftnMessage.class)
public abstract class AftnMessage_ {

	public static volatile SingularAttribute<AftnMessage, MessageKey> messageKey;
	public static volatile SingularAttribute<AftnMessage, Integer> sequenceNumber;
	public static volatile SingularAttribute<AftnMessage, String> svc;
	public static volatile SingularAttribute<AftnMessage, String> flag;
	public static volatile SingularAttribute<AftnMessage, String> origin;
	public static volatile SingularAttribute<AftnMessage, String> channel;
	public static volatile SingularAttribute<AftnMessage, String> source;
	public static volatile SingularAttribute<AftnMessage, Integer> priority;
	public static volatile SingularAttribute<AftnMessage, String> filingTime;
	public static volatile SingularAttribute<AftnMessage, Integer> rptTime;
	public static volatile SingularAttribute<AftnMessage, String> div;
	public static volatile SingularAttribute<AftnMessage, String> cct;
	public static volatile SingularAttribute<AftnMessage, String> rpt;
	public static volatile SingularAttribute<AftnMessage, Date> time;
	public static volatile SingularAttribute<AftnMessage, String> text;
	public static volatile SingularAttribute<AftnMessage, String> cid;

}

