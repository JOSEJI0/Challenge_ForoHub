package com.example.alura.ForoHub.service;

import com.example.alura.ForoHub.exceptions.TopicException;
import com.example.alura.ForoHub.exceptions.UsuarioException;
import com.example.alura.ForoHub.model.Topic;
import com.example.alura.ForoHub.repository.TopicRepository;
import com.example.alura.ForoHub.util.TopicUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paghttp.request.TopicRequest;
import paghttp.response.TopicResponse;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public TopicResponse createTopic(TopicRequest topicRequest) throws UsuarioException {
        Topic topic = TopicUtil.toTopic(topicRequest);
        topicRepository.save(topic);
        return TopicUtil.toTopicResponse(topic);
    }

    public TopicResponse updateTopic(String id, TopicRequest topicRequest) throws TopicException {
        Topic topic = topicRepository.findById(id).orElseThrow(
                () -> new TopicException("Topic no encontrado")
        );
        topic.setTitle(topicRequest.getTitle());
        topic.setBody(topicRequest.getBody());
        topicRepository.save(topic);
        return TopicUtil.toTopicResponse(topic);
    }

    @Transactional
    public void deleteTopic(String id) throws TopicException {
        topicRepository.findById(id).orElseThrow(
                () -> new TopicException("Topic no encontrado")
        ).setEnable(false);
    }

    public TopicResponse getTopic(String id) throws TopicException {
        return TopicUtil.toTopicResponse(topicRepository.findById(id).orElseThrow(
                () -> new TopicException("Topic not found")
        ));
    }

    public List<TopicResponse> getMyTopics(String id) {
        return topicRepository.findByAuthorIdAndEnable(id, true).stream()
                .map(TopicUtil::toTopicResponse)
                .toList();
    }

    public List<TopicResponse> getTopics() {
        return topicRepository.findByEnable(true).stream()
                .map(TopicUtil::toTopicResponse)
                .toList();
    }

    public List<TopicResponse> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(TopicUtil::toTopicResponse)
                .toList();
    }
}
