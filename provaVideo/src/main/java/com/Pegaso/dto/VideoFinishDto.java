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
public class VideoFinishDto {

    @JsonProperty("fail")
    private Integer fail;
    @JsonProperty("percent")
    private Integer percent;
    @JsonProperty("difSecondVideo")
    private Integer difSecondVideo;
    @JsonProperty("difSecondVideo")
    private Integer difTime;
    @JsonProperty("difSecondVideo")
    private Integer time;
}
