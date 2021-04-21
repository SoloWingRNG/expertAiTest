package com.ai.expert.test.entity;

import ai.expert.nlapi.v1.model.DataModel;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDoc implements GenericEntity{

    private DataModel data;
    private boolean success;




}
