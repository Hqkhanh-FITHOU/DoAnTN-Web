package com.graduate.hou.service;

import java.util.List;

import com.google.firebase.internal.NonNull;
import com.google.firebase.messaging.SendResponse;

public interface BatchResponse {
    @NonNull
    List<SendResponse> getResponses();

    int getSuccessCount();

    int getFailureCount();
}
