package com.example.alura.ForoHub.util;

import com.example.alura.ForoHub.exceptions.UsuarioException;
import com.example.alura.ForoHub.model.Topic;
import com.example.alura.ForoHub.model.User;
import com.example.alura.ForoHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import paghttp.request.TopicRequest;
import paghttp.response.TopicResponse;

@Component
public class TopicUtil {
    @Autowired
    private static UserRepository userRepository;

    public static TopicResponse toTopicResponse(Topic topic){
        return TopicResponse.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .body(topic.getBody())
                .authorName(topic.getAuthor().getUsername())
                .build();
    }

    public static Topic toTopic(TopicRequest topicRequest) throws UsuarioException {
        User user = userRepository.findById(topicRequest.getAuthorId())
                .orElseThrow( () -> new UsuarioException("User not found"));
        return Topic.builder()
                .title(topicRequest.getTitle())
                .body(topicRequest.getBody())
                .author(user)
                .build();
    }
}
