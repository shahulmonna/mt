package com.loycl.mt.emit.impl;

import com.google.common.base.Strings;
import com.loycl.mt.emit.SampleEmitter;
import com.loycl.mt.model.MtRequest;
import com.loycl.mt.model.MtResponse;
import com.loycl.mt.utils.conversion.MapperUtil;
import com.loycl.mt.utils.generator.LongGenerator;
import com.loycl.mt.utils.status.exception.MTException;
import com.loycl.mt.utils.validation.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author: Syed Shahul
 */
@Configuration("sampleEmitter")
public class SampleEmitterImpl implements SampleEmitter {
	private static final Logger LOGGER =
			LoggerFactory.getLogger(SampleEmitterImpl.class);


	private RabbitTemplate amqpTemplate;
	private LongGenerator longGenerator;

	@Override
	public void emmit()  {
		try {

		int i;
			LOGGER.info("Push Start at : {}", new Date().toString());
		for (i = 0; i < 50000; i++) {
			// push mt
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setType("MtRequest");
			messageProperties.setMessageId(longGenerator.generate().toString());

			String mtRequestStr = MapperUtil.convertToString(new MtRequest
					(messageProperties.getMessageId().substring(2),
							"Grattis!... use coupon code : #CODE# ".replace
							("#CODE#",messageProperties.getMessageId().substring(8,
									4)).concat(" ref:"+i),
							messageProperties.getMessageId()));
			Message message = new Message(mtRequestStr.getBytes(), messageProperties);

			amqpTemplate.convertAndSend(message);
		}
			LOGGER.info("Push End at : {}", new Date().toString());
		}catch (Exception e){
			LOGGER.info("error : {}",e.getLocalizedMessage());
			e.fillInStackTrace();
		}
	}

	@Autowired
	public void setAmqpTemplate(RabbitTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	@Autowired
	public void setLongGenerator(LongGenerator longGenerator) {
		this.longGenerator = longGenerator;
	}
}
