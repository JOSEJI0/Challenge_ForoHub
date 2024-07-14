package com.example.alura.ForoHub.controll;

import com.example.alura.ForoHub.exceptions.TopicException;
import com.example.alura.ForoHub.exceptions.UsuarioException;
import com.example.alura.ForoHub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import paghttp.request.TopicRequest;

@RestController
@RequestMapping("/api/topics")
public class TopicControll {

    @Autowired
    private TopicService topicService;

    @PreAuthorize("hasAuthority('Create_Topic')")
    @PostMapping
    public ResponseEntity<?> crearTopic(@RequestBody @Valid TopicRequest topicRequest) throws UsuarioException {
        return ResponseEntity.ok(topicService.createTopic(topicRequest));
    }

    @PreAuthorize("hasAuthority('Update_Topic')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopic(@PathVariable String id, @RequestBody @Valid TopicRequest topicRequest) throws TopicException {
        return ResponseEntity.ok(topicService.updateTopic(id, topicRequest));
    }

    @PreAuthorize("hasAuthority('Delete_Topic')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable String id) throws TopicException {
        topicService.deleteTopic(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('Read_Topic')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTopic(@PathVariable String id) throws TopicException {
        return ResponseEntity.ok(topicService.getTopic(id));
    }

    @PreAuthorize("hasAuthority('Read_Topic')")
    @GetMapping
    public ResponseEntity<?> getTopics() {return ResponseEntity.ok(topicService.getTopics());}

    @PreAuthorize("hasAuthority('All_Topic')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTopics() {return  ResponseEntity.ok(topicService.getAllTopics());}
}