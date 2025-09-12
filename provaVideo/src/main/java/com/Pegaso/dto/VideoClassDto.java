package com.Pegaso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoClassDto {

    @JsonProperty("course_code")
    private String courseCode;
    @JsonProperty("lp_item_id")
    private int lpItem;
    @JsonProperty("video_duration")
    private int videoDuration;
    @JsonProperty("video_current")
    private int videoCurrent;

}
