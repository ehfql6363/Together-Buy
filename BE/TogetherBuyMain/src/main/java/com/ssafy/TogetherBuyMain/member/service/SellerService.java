package com.ssafy.TogetherBuyMain.member.service;

import com.ssafy.TogetherBuyMain.global.AWS.S3Util;
import com.ssafy.TogetherBuyMain.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyMain.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyMain.global.util.MemberUtil;
import com.ssafy.TogetherBuyMain.member.dto.request.RegisterSellerRequestDTO;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.Seller;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.member.repository.SellerRepository;
import com.ssafy.TogetherBuyMain.security.email.dto.response.EmailVerificationResponseDTO;
import com.ssafy.TogetherBuyMain.security.email.emailService.EmailService;
import com.ssafy.TogetherBuyMain.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.shop.dto.order.*;
import com.ssafy.TogetherBuyMain.shop.dto.product.*;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;
import com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage;
import com.ssafy.TogetherBuyMain.shop.entity.order.DeliveryStatus;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.entity.product.SubCategory;
import com.ssafy.TogetherBuyMain.shop.repository.category.CategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.category.SubCategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.order.OrderRepository;
import com.ssafy.TogetherBuyMain.shop.repository.product.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {

    private final JwtUtil jwtUtil;
    private final MemberUtil memberUtil;
    private final S3Util s3Util;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final OrderRepository orderRepository;
    private final SellerRepository sellerRepository;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final EmailService emailService;

    @Transactional
    public void registerSeller(
            String authorization,
            RegisterSellerRequestDTO request,
            HttpServletResponse response) {
        Member member = memberUtil.getMemberByAuthorization(authorization);
        Seller newSeller = Seller.builder()
                .businessName(request.businessName())
                .bossName(request.bossName())
                .businessTel(request.businessTel())
                .businessEmail(request.businessEmail())
                .businessAddress(request.businessAddress())
                .businessNumber(request.businessNumber())
                .member(member)
                .build();

        sellerRepository.save(newSeller);

        member = memberRepository.save(member);

        Cookie cookie = new Cookie("Authorization", jwtUtil.generateAccessToken(member));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 300);
        response.addCookie(cookie);
    }

    @Transactional
    public RegisterProductResponseDTO registerProduct(
            String authorization,
            RegisterProductRequestDTO request,
            List<MultipartFile> productImages,
            MultipartFile detailImage
    ) {
        Seller seller = getSeller(authorization);

        Product newProduct = getNewProduct(request, seller);
        log.info("newProduct: {}", newProduct.getImages().toString());
        createProductImages(productImages, newProduct);
        createProductDetailImage(detailImage, newProduct);

        return new RegisterProductResponseDTO(newProduct.getProductId());
    }

    @Transactional
    public List<RetrieveSellersProductsResponseDTO> getSellersProducts(String authorization) {
        Seller seller = getSeller(authorization);
        System.out.println(seller.getSellerId());
        List<Product> products = productRepository.findAllBySeller(seller);

        return products.stream()
                .map(RetrieveSellersProductsResponseDTO::of)
                .toList();
    }

    @Transactional
    public RetrieveOrdersByProductResponseDTO getOrdersByProduct(
            String authorization,
            Long productId) {
        Product product = productRepository.getReferenceById(productId);
        List<Order> orders = product.getOrders();

        List<OrderListDTO> orderListDTOs = getOrderListDTOs(orders);
        if(orderListDTOs.isEmpty()) return new RetrieveOrdersByProductResponseDTO(orderListDTOs, null, 0L);

        Long total = 0L;
        for (Order order : orders) {
            total += order.getOrderPrice();
        }

        List<OrderParticipantResponseDTO> participants = getParticipants(orders.getFirst());
        RecipientDTO recipient = getRecipient(orders.getFirst().getForm().getRecipient());
        OrderDetailResponseDTO orderDetail = OrderDetailResponseDTO.of(orders.getFirst(), recipient, participants);

        return new RetrieveOrdersByProductResponseDTO(orderListDTOs, orderDetail, total);
    }

    @Transactional
    public OrderDetailResponseDTO getOrderDetail(
            Long orderId
    ) {
        Order order = orderRepository.getReferenceById(orderId);
        List<OrderParticipantResponseDTO> participants = getParticipants(order);
        RecipientDTO recipient = getRecipient(order.getForm().getRecipient());

        return OrderDetailResponseDTO.of(order, recipient, participants);
    }

    @Transactional
    public OrderStatusListResponseDTO getOrderStatusList(
            String status
    ) {
        List<OrderStatusDTO> orderStatuses = getOrdersByStatus(status);
        return new OrderStatusListResponseDTO(orderStatuses);
    }

    @Transactional
    public void changeDeliveryStatus(
            Long orderId,
            String wayBillNumber
    ) {
        Order order = orderRepository.getReferenceById(orderId);
        order.updateWayBillNumber(wayBillNumber);
    }

    @Transactional
    public ProductModifyResponseDTO getProductDetail(
            String authorization,
            Long productId
    ) {
        Seller seller = getSeller(authorization);
        Product product = productRepository.getReferenceById(productId);

        if(!product.getSeller().equals(seller)) {
            throw new IllegalArgumentException("상품 등록한 판매자만 수정할 수 있습니다.");
        }

        return ProductModifyResponseDTO.of(product);
    }

    @Transactional
    public void updateProduct(
            String authorization,
            Long productId,
            ProductUpdateRequestDTO request,
            List<MultipartFile> productImages,
            MultipartFile detailImage
    ) {
        Seller seller = getSeller(authorization);
        Product product = productRepository.getReferenceById(productId);

        if(!product.getSeller().equals(seller)) {
            throw new IllegalArgumentException("상품 등록한 판매자만 수정할 수 있습니다.");
        }

        deleteImagesFromS3(product, product.getImages());
        updateProductImages(productImages, product);

        String productDetailImage = createProductImage(detailImage, product).getUrl();
        updateProduct(product, request, productDetailImage);
    }

    private Product getNewProduct(RegisterProductRequestDTO request, Seller seller) {
        SubCategory subCategory = subCategoryRepository.getReferenceById(request.subCategoryId());
        Product product = Product.builder()
                .productName(request.productName())
                .price(request.price())
                .discountAmount(request.discountAmount())
                .subCategory(subCategory)
                .discountUnit(request.disCountUnit())
                .salePrice(request.salePrice())
                .unitAmount(request.unitAmount())
                .unitName(request.unitName())
                .pricePerUnit(request.pricePerUnit())
                .total(request.total())
                .seller(seller)
                .groupBuyingBoards(new ArrayList<>())
                .orders(new ArrayList<>())
                .wishingMembers(new HashSet<>())
                .images(new ArrayList<>())
                .build();
        return productRepository.save(product);
    }

    private void createProductImages(List<MultipartFile> productImages, Product product) {
        for (MultipartFile productImage : productImages) {
            ProductImage newProductImage = createProductImage(productImage, product);
            product.getImages().add(newProductImage);
        }
    }

    private ProductImage createProductImage(MultipartFile productImage, Product product) {
        String url = s3Util.uploadFile(productImage, product.getProductId());
        return ProductImage.builder()
                .product(product)
                .fileName(productImage.getOriginalFilename())
                .url(url)
                .contentType(productImage.getContentType())
                .size(productImage.getSize())
                .build();
    }

    private void createProductDetailImage(MultipartFile detailImage, Product product) {
        String url = s3Util.uploadFile(detailImage, product.getProductId());
        product.updateDetailImage(url);
    }

    private List<OrderListDTO> getOrderListDTOs(List<Order> orders) {
        return orders.stream().map(OrderListDTO::of).toList();
    }

    private List<OrderParticipantResponseDTO> getParticipants(Order order) {
        Form form = order.getForm();
        return form.getApplies().stream()
                .map(OrderParticipantResponseDTO::of)
                .toList();
    }

    private RecipientDTO getRecipient(Member member) {
        if(member == null) return null;
        return RecipientDTO.of(member);
    }

    private List<OrderStatusDTO> getOrdersByStatus(String status) {
        return "ALL".equalsIgnoreCase(status)
                ? orderRepository.findAll().stream().map(OrderStatusDTO::of).toList()
                : orderRepository.findAllByDeliveryStatus(DeliveryStatus.valueOf(status.toUpperCase()))
                .stream().map(OrderStatusDTO::of).toList();
    }

    private void updateProduct(Product product, ProductUpdateRequestDTO request, String productDetailImage) {
        product.updateProductInfo(
                request.productName(),
                request.price(),
                request.discountAmount(),
                request.discountUnit(),
                request.salePrice(),
                request.unitAmount(),
                request.unitName(),
                request.pricePerUnit(),
                request.total()
        );

        product.updateDetailImage(productDetailImage);
    }

    private void updateProductImages(List<MultipartFile> productImages, Product product) {
        productImages.forEach(productImage -> {
            ProductImage newProductImage = createProductImage(productImage, product);
            product.getImages().add(newProductImage);
        });
    }

    private void deleteImagesFromS3(Product product, List<ProductImage> productImages) {
        for (ProductImage productImage : productImages) {
            s3Util.deleteFile(productImage.getUrl());
        }
        product.getImages().clear();
    }

    private Seller getSeller(String accessToken) {
        Member member = memberUtil.getMemberByAuthorization(accessToken);
        return member.getSeller();
    }


    @Transactional
    public void deleteProduct(String authorization, Long productId) {

        String accessToken = jwtService.extractToken(authorization);

        // 토큰에서 memberId를 뽑고,
        Long memberId = jwtUtil.getMemberIdFromToken(accessToken);

        // 해당 memberId의 seller에 연결된 id값으로 busninessname이 존재할경우 삭제가능
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));


        Seller seller = member.getSeller();
        // seller의 businessName 존재 여부로 판매자 권한 확인
        if (seller == null || seller.getBusinessName() == null) {
            throw new BusinessLogicException(ExceptionCode.NOT_A_SELLER);
        }

        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        // 상품의 판매자와 현재 판매자가 일치하는지 확인
        if (!product.getSeller().equals(seller)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_PRODUCT_ACCESS);
        }

        try {
            // S3에서 상품 이미지들 삭제
            deleteImagesFromS3(product, product.getImages());

            // 상품 삭제
            productRepository.delete(product);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_DELETE_FAILED);
        }
    }

    @Transactional
    public void sendCode(String authorization) {
        String email = jwtUtil.getEmailFromToken(jwtUtil.extractToken(authorization));
        String code = createCode();
        emailService.sendEmail(email, code);

        // Member 엔티티에서 이메일로 해당 Member를 찾음
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Seller not found with email: " + email));

        member.setVerificationCode(code);
        memberRepository.save(member);
    }

    private String createCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    @Transactional
    public EmailVerificationResponseDTO verifyCode(String authorization, String codeToVerify) {
        String email = jwtUtil.getEmailFromToken(jwtUtil.extractToken(authorization));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Seller not found with email: " + email));

        boolean isVerified = member.verifyCode(codeToVerify);

        return new EmailVerificationResponseDTO(isVerified);
    }
}
