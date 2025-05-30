package com.ssafy.TogetherBuyMain.community.dto.page;

import java.util.List;

public record PaginatedResponseDTO<T>(
        List<T> content,
        int currentPage,
        int totalPages,
        long totalElements,
        int pageSize
) {
}
