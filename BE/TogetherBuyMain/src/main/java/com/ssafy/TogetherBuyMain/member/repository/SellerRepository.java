package com.ssafy.TogetherBuyMain.member.repository;

import com.ssafy.TogetherBuyMain.member.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
//    Optional<Seller> findByBusinessEmail(String email);
//    Optional<Seller> findByMember_Email(String email);
}
