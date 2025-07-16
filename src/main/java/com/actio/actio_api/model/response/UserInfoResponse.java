package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {
    private String fullName;
    private String email;
    private String role;
}
