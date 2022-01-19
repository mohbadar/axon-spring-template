package com.unite.axon_spring.iam.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Data
public class BaseEvent<T> {

    public final T id;

    public BaseEvent(T id) {
        Assert.notNull(id, "Id cannot be null");
        this.id = id;
    }
}