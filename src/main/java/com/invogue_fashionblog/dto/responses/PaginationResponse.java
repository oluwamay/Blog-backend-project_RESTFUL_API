package com.invogue_fashionblog.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse {

    private List<PostResponse> pageContent;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean islastPage;

}
