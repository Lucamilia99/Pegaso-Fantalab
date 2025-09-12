package com.Pegaso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VideoClassInizializeDto {

    @JsonProperty("result")
    private String result;
    @JsonProperty("control_key")
    private String control_key;
    @JsonProperty("total_time")
    private int total_time;
    @JsonProperty("captcha_to_do")
    private String captcha_to_do;
    @JsonProperty("fail")
    private Integer fail;
    @JsonProperty("percent")
    private Integer percent;

}
