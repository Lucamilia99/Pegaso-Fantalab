package com.Pegaso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDtoResponse2 {

     @JsonProperty("id")
     private Long id;
     @JsonProperty("lp_type")
     private Long lp_type;
     @JsonProperty("name")
     private String name;
     @JsonProperty("display_order")
     private String display_order;
     @JsonProperty("folder_id")
     private String folder_id;
     @JsonProperty("lp_id")
     private String lp_id;
     @JsonProperty("title")
     private String title;
     @JsonProperty("percentage")
     private String percentage;

     @Override
     public String toString() {
          return "CourseDtoResponse2{" +
                  "id=" + id +
                  ", lp_type=" + lp_type +
                  ", name='" + name + '\'' +
                  ", display_order='" + display_order + '\'' +
                  ", folder_id='" + folder_id + '\'' +
                  ", lp_id='" + lp_id + '\'' +
                  ", title='" + title + '\'' +
                  ", percentage='" + percentage + '\'' +
                  '}';
     }
}
