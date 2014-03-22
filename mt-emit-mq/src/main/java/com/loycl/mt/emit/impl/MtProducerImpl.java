package com.loycl.mt.emit.impl;

import com.loycl.mt.emit.MtProducer;
import com.loycl.mt.model.MtRequest;
import com.loycl.mt.model.MtResponse;
import com.loycl.mt.utils.generator.LongGenerator;
import com.loycl.mt.utils.status.exception.MTException;
import com.loycl.mt.utils.validation.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Syed Shahul
 */
@Configuration("mtProducer")
public class MtProducerImpl implements MtProducer{
	private static final Logger LOGGER =
		LoggerFactory.getLogger(MtProducerImpl.class);

	private RabbitTemplate amqpTemplate;
	private LongGenerator longGenerator;

	@Override public MtResponse pushMT(MtRequest mtRequest) throws MTException {
		MtResponse mtResponse =new MtResponse(CollectionUtil.isNotEmpty(mtRequest
			                                                                .getReferenceNo()) ? mtRequest
			.getReferenceNo() :longGenerator.generate().toString());

		if(LOGGER.isInfoEnabled()){
			LOGGER.info("mtResponse : {}",mtResponse);
		}
		// push mt
		amqpTemplate.convertAndSend(mtRequest);
		return mtResponse;
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
