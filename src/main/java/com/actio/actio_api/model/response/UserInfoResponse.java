package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {
    private String name;
    private String email;
    private String userRole;
    private Long accountId;
}
