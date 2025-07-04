package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationResponse {

    private Long id;
    private String email;


}
