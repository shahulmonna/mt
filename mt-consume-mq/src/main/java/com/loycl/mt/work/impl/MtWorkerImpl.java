package com.loycl.mt.work.impl;

import com.loycl.mt.model.MtRequest;
import com.loycl.mt.service.MtManager;
import com.loycl.mt.work.conversion.AmqpUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author: Syed Shahul
 */
@Configuration("mtWorkerImpl")
public class MtWorkerImpl implements ChannelAwareMessageListener {
	private static final Logger LOGGER =
			LoggerFactory.getLogger(MtWorkerImpl.class);

	private MtManager mtManager;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
		/*if(LOGGER.isInfoEnabled()){
		   LOGGER.info("onMessage : {}", new String(message.getBody()));
		}*/
if(message.getMessageProperties().getType() != null) {
	switch (message.getMessageProperties().getType()) {
		case "MtRequest":
			mtManager.processMT((MtRequest) AmqpUtil.convertMessage(message));
	}
}else {
	LOGGER.info("msg :{}", new String(message.getBody()));
}
		}catch (Exception e){
			e.fillInStackTrace();
			LOGGER.error("{}",e.getMessage());
		}
	//	channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
	}

	@Autowired
	public void setMtManager(MtManager mtManager) {
		this.mtManager = mtManager;
	}
}
