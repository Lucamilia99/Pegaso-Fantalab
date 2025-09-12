package com.pegaso.PEGASO.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDtoResponse {

     @JsonProperty("id")
     private Long id;
     @JsonProperty("description")
     private String description;
     @JsonProperty("lessonId")
     private int lessonId;
     @JsonProperty("itemId")
     private String itemId;
     @JsonProperty("lp_item_id")
     private int lpItemId;
     @JsonProperty("lp_id")
     private int lpId;
     @JsonProperty("previous_item_id")
     private int itemPrecedente;
     @JsonProperty("next_item_id")
     private int itemSuccessivo;
     @JsonProperty("title")
     private String title;
     @JsonProperty("percentage")
     private int percentage;
     @JsonProperty("start_time")
     private int startTime;
     @JsonProperty("total_time")
     private int totalTime;
     @JsonProperty("contentType")
     private String contentType;
     @JsonProperty("duration_s")
     private int duration_s;
     @JsonProperty("videoUrl")
     private String videoUrl;
     @JsonProperty("mp3Url")
     private String mp3Url;
     @JsonProperty("paragNumber")
     private String paragNumber;
     @JsonProperty("max_time_allowed")
     private String max_time_allowed;
     @JsonProperty("testEmpty")
     private String testEmpty;
     @JsonProperty("testImported")
     private String testImported;
     @JsonProperty("type")
     private String type;
     @JsonProperty("platform_id")
     private String platform_id;
     @JsonProperty("teacherName")
     private String teacherName;
     @JsonProperty("teacherSurname")
     private String teacherSurname;
     @JsonProperty("secTeachName")
     private String secTeachName;
     @JsonProperty("secTeachSurname")
     private String secTeachSurname;
     @JsonProperty("lessonNumber")
     private String lessonNumber;
     @JsonProperty("paragTotNumber")
     private String paragTotNumber;
     @JsonProperty("bookUrl")
     private String bookUrl;
     @JsonProperty("peg_BookUrl")
     private String peg_BookUrl;
     @Override
     public String toString() {
          return "CourseDtoResponse{" +
                  "id=" + id +
                  ", description='" + description + '\'' +
                  ", lessonId=" + lessonId +
                  ", itemId='" + itemId + '\'' +
                  ", lpItemId=" + lpItemId +
                  ", lpId=" + lpId +
                  ", itemPrecedente=" + itemPrecedente +
                  ", itemSuccessivo=" + itemSuccessivo +
                  ", title='" + title + '\'' +
                  ", percentage=" + percentage +
                  ", startTime=" + startTime +
                  ", totalTime=" + totalTime +
                  ", contentType='" + contentType + '\'' +
                  ", duration_s='" + duration_s + '\'' +
                  ", videoUrl='" + videoUrl + '\'' +
                  ", mp3Url='" + mp3Url + '\'' +
                  ", paragNumber='" + paragNumber + '\'' +
                  ", max_time_allowed='" + max_time_allowed + '\'' +
                  ", testEmpty='" + testEmpty + '\'' +
                  ", testImported='" + testImported + '\'' +
                  ", type='" + type + '\'' +
                  ", platform_id='" + platform_id + '\'' +
                  ", teacherName='" + teacherName + '\'' +
                  ", teacherSurname='" + teacherSurname + '\'' +
                  ", secTeachName='" + secTeachName + '\'' +
                  ", secTeachSurname='" + secTeachSurname + '\'' +
                  ", lessonNumber='" + lessonNumber + '\'' +
                  ", paragTotNumber='" + paragTotNumber + '\'' +
                  ", bookUrl='" + bookUrl + '\'' +
                  ", peg_BookUrl='" + peg_BookUrl + '\'' +
                  '}';
     }
}
