package com.capstone.jejuRefactoring.common.logging;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class ApiQueryCounter {

    private int count;

    public void increaseCount() {
        count++;
    }
}
