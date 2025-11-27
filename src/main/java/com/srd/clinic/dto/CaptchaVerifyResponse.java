package com.srd.clinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaptchaVerifyResponse {
    private boolean success;
    private double score;
    @JsonProperty("challenge_ts")
    private String challengeTs;
    private String hostname;
    private List<String> errorCodes;
}
