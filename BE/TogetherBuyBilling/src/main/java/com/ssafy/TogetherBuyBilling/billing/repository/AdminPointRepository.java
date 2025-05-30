package com.ssafy.TogetherBuyBilling.billing.repository;

import com.ssafy.TogetherBuyBilling.billing.entity.AdminPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminPointRepository extends JpaRepository<AdminPoint, Long> {
    @Query("SELECT a FROM AdminPoint a WHERE a.pointId = 1")
    AdminPoint findAdminPoint();
}
