package com.example.flowersystem.api;

import com.example.flowersystem.dto.MessageDTO;
import com.example.flowersystem.dto.NotificationDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationApi {
    @POST("/v1/notifications/topic/{topicName}")
    public Call<MessageDTO> sendNotification(@Path("topicName") String topicName, @Body NotificationDTO notificationDTO);
}
