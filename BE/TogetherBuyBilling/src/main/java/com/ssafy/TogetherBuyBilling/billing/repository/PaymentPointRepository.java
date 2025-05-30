package com.ssafy.TogetherBuyBilling.billing.repository;

import com.ssafy.TogetherBuyBilling.billing.entity.PaymentPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentPointRepository extends JpaRepository<PaymentPoint, Long> {
}
