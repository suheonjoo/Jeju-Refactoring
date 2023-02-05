package com.capstone.jejuRefactoring.common.support;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;

public class RepositorySupport {

    private RepositorySupport() {
    }

    public static BooleanExpression toContainsExpression(final StringPath stringPath, final String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return stringPath.contains(keyword);
    }

    public static <T> BooleanExpression toEqExpression(final SimpleExpression<T> simpleExpression, final T compared) {
        if (compared == null) {
            return null;
        }
        return simpleExpression.eq(compared);
    }

    public static <T> Slice<T> toSlice(List<T> contents, Pageable pageable) {
        boolean hasNext = isContentSizeGreaterThanPageSize(contents, pageable);
        //hasNext 가 true 면 subListLastContent, false 면 contents
        return new SliceImpl<>(hasNext ? subListLastContent(contents, pageable) : contents, pageable, hasNext);
    }

    private static <T> boolean isContentSizeGreaterThanPageSize(List<T> content, Pageable pageable) {
        return content.size() > pageable.getPageSize();
    }

    private static <T> List<T> subListLastContent(List<T> content, Pageable pageable) {
        return content.subList(0, pageable.getPageSize());
    }
}
