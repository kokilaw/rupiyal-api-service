package io.kokilaw.rupiyal.common.request;

import io.kokilaw.rupiyal.common.model.ProcessorType;
import lombok.Data;

/**
 * Created by kokilaw on 2023-06-13
 */
@Data
public class TaskRequest {
    private ProcessorType processorType;
}
