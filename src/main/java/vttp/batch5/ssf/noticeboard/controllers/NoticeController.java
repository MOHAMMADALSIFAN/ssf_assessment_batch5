package vttp.batch5.ssf.noticeboard.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.Constant.Util;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers
@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;


    @GetMapping("")
    public String showForm(Model model) {
        Notice notice = new Notice();
        model.addAttribute("notice", notice);
        return "notice";
    }

    @PostMapping(path = "/notice")
    public String postNotice(@Valid @ModelAttribute Notice notice,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "notice";
        }
        try {
            ResponseEntity<?> response = noticeService.postToNoticeServer(notice);
            if (!response.getStatusCode().isError()) {
                JsonReader reader = Json.createReader(new StringReader((String) response.getBody()));
                JsonObject jsonResponse = reader.readObject();
                model.addAttribute("id", jsonResponse.getString("id"));
                return "view2";
            } else {
                model.addAttribute("error", "Failed to post notice");
                model.addAttribute("url", Util.URL);
                return "view3";
            }
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("url", Util.URL);
            return "view3";
        }
    }



}
