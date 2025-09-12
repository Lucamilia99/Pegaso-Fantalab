package com.pegaso.PEGASO.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse2 {


    @JsonProperty("data")
    private List<CourseDtoResponse2> data;
    @JsonProperty("code")
    private int code;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data=" + data +
                ", code=" + code +
                '}';
    }
}
