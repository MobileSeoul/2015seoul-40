
package com.seoul.hanokmania.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO JSON 파싱을 위한 준비
 */
@JsonIgnoreProperties(ignoreUnknown = true)     // 매칭 되는 필드가 없을 때 무시
public class Weather {
    @JsonProperty("dt_txt")     // JSON 속성과 매칭
    public String time;

    @JsonProperty("temp")       // 속성과 변수명이 같을 경우 생략 가능
    public String temp;

    @JsonProperty("description")
    public String description;

    @Override
    public String toString() {
        return "Weather [time: " + time + ", temp: " + temp + ", desc: " + description + "]";
    }
}
