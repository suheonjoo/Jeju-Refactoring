package com.capstone.jejuRefactoring.common.logging;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope //요청마다의 빈 생성및소멸하는 스코프 설정
@Getter
public class ApiQueryInfo {

    private long runningTime;
    private int count;

    public void increaseCount() {
        count++;
    }

    public void increaseRunningTime(long addTime) {
        runningTime += addTime;
    }
}
