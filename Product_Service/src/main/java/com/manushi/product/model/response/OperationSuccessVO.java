package com.manushi.product.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OperationSuccessVO {
    @JsonProperty("result")
    private String result;

    public OperationSuccessVO() {
        this.result = "success";
    }
}
