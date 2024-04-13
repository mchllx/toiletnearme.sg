package vttp.batch4.csf.toiletnearme.exceptions;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import vttp.batch4.csf.toiletnearme.services.KafkaReceiver;

public class KafkaErrorHandler implements CommonErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    @Override
    public boolean handleOne(Exception exception, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        handle(exception, consumer);
        return false;
    }

    @Override
    public void handleOtherException(Exception exception, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(exception, consumer);
    }

    private void handle(Exception exception, Consumer<?, ?> consumer) {
        LOGGER.error("Exception thrown", exception);
       
        // skip msg throwing exception
            if (exception instanceof RecordDeserializationException ex) {
                // similar to return cursor position
                consumer.seek(ex.topicPartition(), ex.offset() + 1L);
                // confirms offset
                // server consumes from this offset position even after restarting
                consumer.commitSync();
        } else {
            LOGGER.error("Exception not handled", exception);
        }
    }
}
