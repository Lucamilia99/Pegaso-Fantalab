package com.pegaso.PEGASO.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pegaso.PEGASO.dto.*;
import com.pegaso.PEGASO.exception.ControllerException;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static com.pegaso.PEGASO.service.RestClient.sendGetRequest;
import static com.pegaso.PEGASO.service.RestClient.sendPostRequest;

public class VideoControllerRestApi {

    private static final String COURSE_CODE = "0312209INGIND35";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int TOTAL_CHAPTER;

    static {
        try {
            TOTAL_CHAPTER = fetchLastChapter();
            System.out.println("TOTAL CHAPTER: " + TOTAL_CHAPTER);
        } catch (IOException | InterruptedException | ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String BASE_URL = "https://lms-api.prod.pegaso.multiversity.click";

    public static void main(String[] args) throws ControllerException, IOException, InterruptedException {
        int numberParagraph = 43;
        processParagraphs(numberParagraph);
    }

    private static void processParagraphs(int numberParagraph) throws ControllerException, IOException, InterruptedException {
        if (numberParagraph > TOTAL_CHAPTER) {
            System.out.println("Processo completato. Nessun altro paragrafo da elaborare.");
            return; // Condizione di uscita chiara
        }
        System.out.println("Number : " + numberParagraph);
        ApiResponse apiResponse = fetchApiParagraphs(numberParagraph);

        if (apiResponse.getData() == null || apiResponse.getData().isEmpty()) {
            processParagraphs(numberParagraph + 1);
        }

        List<CourseDtoResponse> filteredList = apiResponse.getData().stream()
                .filter(item -> item.getPercentage() < 100
                        && !item.getTitle().equalsIgnoreCase("Test di autovalutazione")
                        && item.getLessonId() > 0
                        && item.getDuration_s() > 0) // Filtra già gli item con durata > 0)
                .toList();

        if (!filteredList.isEmpty()) {
            for (CourseDtoResponse item : filteredList) {
                processVideoLesson(item);
            }
        }
        //Continuo con la lezione successiva
        processParagraphs(numberParagraph+1);
    }

    private static void processVideoLesson(CourseDtoResponse lesson) throws IOException, InterruptedException, ControllerException {
        VideoClassInizializeDto videoInfo = fetchLessonDetails(lesson.getLpItemId(), lesson.getDuration_s());

        if (videoInfo.getPercent() == null) {
            videoInfo.setPercent(0);
        }
        while (videoInfo.getPercent() < 100) {
            videoInfo = updateVideoProgress(videoInfo.getTotal_time() + 3, videoInfo.getControl_key());
        }

        System.out.println("La lezione \"" + lesson.getTitle() + "\" ha raggiunto il 100%.");

    }

    private static VideoClassInizializeDto updateVideoProgress(int videoCurrent, String controlKey) throws IOException, InterruptedException, ControllerException {
        String url = BASE_URL + "/student/course/" + COURSE_CODE + "/lesson/view/send";

        ObjectNode bodyJson = OBJECT_MAPPER.createObjectNode()
                .put("course_code", COURSE_CODE)
                .put("video_current", videoCurrent)
                .put("control_key", controlKey);

        HttpResponse<String> response = sendPostRequest(url, bodyJson);

        if (response.statusCode() != 200) {
            throw new ControllerException("Errore aggiornamento video: ", response.statusCode());
        }

        JsonNode dataNode = OBJECT_MAPPER.readTree(response.body()).get("data");

        Integer percent = dataNode.get("percent").asInt();
        Integer fail = dataNode.get("percent").asInt();
        
        if ( percent.equals(50) || percent.equals(100) ) System.out.println("percent " + percent);
        return VideoClassInizializeDto.builder()
                .percent(percent)
                .fail(fail)
                .total_time(videoCurrent)
                .control_key(controlKey)
                .build();
    }

    private static VideoClassInizializeDto fetchLessonDetails(int lpItem, Integer duration) throws IOException, InterruptedException, ControllerException {
        String url = BASE_URL + "/student/course/" + COURSE_CODE + "/lesson/view/inizialize";

        ObjectNode bodyJson = OBJECT_MAPPER.createObjectNode()
                .put("course_code", COURSE_CODE)
                .put("lp_item_id", lpItem)
                .put("video_duration", duration)
                .putNull("video_current");

        HttpResponse<String> response = sendPostRequest(url, bodyJson);

        if (response.statusCode() != 200) {
            throw new ControllerException("Errore inizializzazione lezione: ", response.statusCode());
        }

        JsonNode dataNode = OBJECT_MAPPER.readTree(response.body()).get("data");

        return VideoClassInizializeDto.builder()
                .result(dataNode.get("result").asText())
                .control_key(dataNode.get("control_key").asText())
                .total_time(dataNode.get("total_time").asInt())
                .captcha_to_do(dataNode.get("captcha_to_do").asText())
                .build();
    }

    private static ApiResponse fetchApiParagraphs(int numberParagraph) throws IOException, InterruptedException, ControllerException {
        String url = BASE_URL + "/student/course/" + COURSE_CODE + "/video-lesson/" + numberParagraph + "/paragraphs/" + numberParagraph;

        HttpResponse<String> response = sendGetRequest(url);

        if (response.statusCode() != 200) {
            throw new ControllerException("Errore API paragrafi: ", response.statusCode());
        }
        return OBJECT_MAPPER.readValue(response.body(), ApiResponse.class);
    }

    private static int fetchLastChapter() throws IOException, InterruptedException, ControllerException {
        String url = BASE_URL + "/student/course/" + COURSE_CODE + "/video-lessons/" + 0;

        HttpResponse<String> response = sendGetRequest(url);

        if (response.statusCode() != 200) {
            throw new ControllerException("Errore API paragrafi: ", response.statusCode());
        }
        // Deserializzo l'object
        ApiResponse2 apiResponse2 = OBJECT_MAPPER.readValue(response.body(), ApiResponse2.class);
        // Calcola il massimo ID tra tutti gli oggetti nella lista `data`
        Long maxId = apiResponse2.getData().stream()
                .map(CourseDtoResponse2::getId) // estrai il campo id
                .max(Long::compareTo)           // trova il massimo
                .orElse(null);           // gestisce il caso lista vuota
        assert maxId != null;
        return maxId.intValue();
    }
}
