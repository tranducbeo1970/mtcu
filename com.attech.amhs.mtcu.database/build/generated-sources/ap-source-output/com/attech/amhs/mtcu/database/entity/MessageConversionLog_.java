package com.attech.amhs.mtcu.database.entity;

import com.attech.amhs.mtcu.database.enums.ConvertResult;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.attech.amhs.mtcu.database.enums.Priority;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageConversionLog.class)
public abstract class MessageConversionLog_ {

	public static volatile SingularAttribute<MessageConversionLog, String> date;
	public static volatile SingularAttribute<MessageConversionLog, String> subject;
	public static volatile SingularAttribute<MessageConversionLog, String> origin;
	public static volatile SetAttribute<MessageConversionLog, AddressConversionLog> addressLogs;
	public static volatile SingularAttribute<MessageConversionLog, String> contentId;
	public static volatile SingularAttribute<MessageConversionLog, String> messageId;
	public static volatile SingularAttribute<MessageConversionLog, String> remark;
	public static volatile SingularAttribute<MessageConversionLog, MessageType> type;
	public static volatile SingularAttribute<MessageConversionLog, Priority> priority;
	public static volatile SingularAttribute<MessageConversionLog, String> filingTime;
	public static volatile SetAttribute<MessageConversionLog, MessageConversionLog> childs;
	public static volatile SingularAttribute<MessageConversionLog, String> subjectId;
	public static volatile SingularAttribute<MessageConversionLog, String> content;
	public static volatile SingularAttribute<MessageConversionLog, String> ohi;
	public static volatile SingularAttribute<MessageConversionLog, Date> convertedTime;
	public static volatile SingularAttribute<MessageConversionLog, String> subjectIpm;
	public static volatile SingularAttribute<MessageConversionLog, Long> id;
	public static volatile SingularAttribute<MessageConversionLog, MessageCategory> category;
	public static volatile SingularAttribute<MessageConversionLog, String> ipmId;
	public static volatile SingularAttribute<MessageConversionLog, ConvertResult> status;
	public static volatile SingularAttribute<MessageConversionLog, MessageConversionLog> parents;

}

