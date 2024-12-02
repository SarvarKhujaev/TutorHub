package org.tutorhub.interfaces.kafka;

import org.tutorhub.inspectors.AnnotationInspector;
import org.tutorhub.inspectors.AvroSchemaInspector;
import org.apache.avro.generic.GenericRecord;

@SuppressWarnings(
        value = "хранит все методы нужные для объектов которые отправляются через Кафку"
)
public interface KafkaEntitiesCommonMethods {
    @lombok.NonNull
    String getSuccessMessage();

    @lombok.NonNull
    default String generateMessage() {
        return "Kafka got request for topic: " + AnnotationInspector.getKafkaTopicName( this );
    }

    @lombok.NonNull
    default GenericRecord getEntityRecord() {
        return AvroSchemaInspector.generateGenericRecord( this );
    }
}
