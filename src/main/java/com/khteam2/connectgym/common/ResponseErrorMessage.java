package com.khteam2.connectgym.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorMessage {

    private Boolean isSuccess;
    private String errorMsg;
    private String url;
}
