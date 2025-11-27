package com.srd.clinic.dto;

import lombok.Data;

@Data
public class CaptchaVerifyResponse {

    private boolean success;

    private String challenge_ts;

    private String hostname;

    private String[] errorCodes;

}
