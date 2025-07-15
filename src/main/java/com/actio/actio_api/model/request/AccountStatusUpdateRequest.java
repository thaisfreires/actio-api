package com.actio.actio_api.model.request;

import lombok.Data;

@Data
public class AccountStatusUpdateRequest {
    private Long accountId;
    private String newStatus;
}
