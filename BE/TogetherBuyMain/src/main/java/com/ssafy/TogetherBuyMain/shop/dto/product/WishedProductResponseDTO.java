package com.ssafy.TogetherBuyMain.shop.dto.product;

import java.util.List;

public record WishedProductResponseDTO(
        List<WishedProductDTO> wishedProducts
) {}
