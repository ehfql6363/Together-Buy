package com.ssafy.TogetherBuyMain.shop.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {
    PRODUCT_NOT_FOUND("P001", "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND.value()),
    SEARCH_NO_RESULTS("P002", "검색 결과가 없습니다.", HttpStatus.NOT_FOUND.value()),
    INVALID_PARAMETER("P003", "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST.value()),
    MEMBER_NOT_FOUND("P004", "멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND.value()),
    CATEGORY_NOT_FOUND("P005", "카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND.value()),
    CATEGORY_IS_EMPTY("P006", "카테고리가 비어 있습니다.", HttpStatus.NOT_FOUND.value()),
    SUBCATEGORY_NOT_FOUND("P007", "서브카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND.value()),
    INVALID_CATEGORY_SUBCATEGORY_COMBINATION("P007", "유효하지 않은 카테고리/서브카테고리 조합입니다.", HttpStatus.BAD_REQUEST.value());

    private final String code;
    private final String message;
    private final int statusCode;
}