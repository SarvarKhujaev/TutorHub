package org.tutorhub.annotations.kafka;

import org.tutorhub.constans.kafka.KafkaTopics;
import java.lang.annotation.*;

@Target( value = ElementType.TYPE )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
@SuppressWarnings(
        value = """
                отвечает за параметры классов, которые используются для сериализации
                и отправки в Кафку
                """
)
public @interface KafkaEntityAnnotation {
    KafkaTopics topicName();
}
