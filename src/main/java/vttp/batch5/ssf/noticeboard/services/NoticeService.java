package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.Constant.Util;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticerepo;

    // TODO: Task 3
    // You can change the signature of this method by adding any number of parameters
    // and return any type
    public ResponseEntity<?> postToNoticeServer(Notice notice) {
        try {
            JsonArrayBuilder categoryBuilder = Json.createArrayBuilder();
            for (String category : notice.getCategories()) {
                categoryBuilder.add(category);
            }
            JsonArray categoriesArray = categoryBuilder.build();
            JsonObject payload = Json.createObjectBuilder()
                    .add("title", notice.getTitle())
                    .add("poster", notice.getPoster())
                    .add("postDate", notice.getPostDate().getTime())
                    .add("categories", categoriesArray)
                    .add("text", notice.getText())
                    .build();

            RequestEntity<String> req = RequestEntity
                    .post(Util.URL + "/notice")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(payload.toString());

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.exchange(req, String.class);

            if (!response.getStatusCode().isError()) {
                JsonReader reader = Json.createReader(new StringReader(response.getBody()));
                JsonObject record = reader.readObject();
                if (record.containsKey("id")) {
                    noticerepo.insertNotices(
                            record.getString("id"),
                            record.toString()
                    );
                }
                return response;
            }
            return response;

        } catch (Exception ex) {
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("errormessage:",  ex.getMessage())
                    .add("timestamp", System.currentTimeMillis())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    public Boolean isHealthy() {
        return noticerepo.isHealthy();
    }
}
