package com.ssafy.TogetherBuyBilling.security.oauth.repository;

import com.ssafy.TogetherBuyBilling.security.oauth.entity.OAuth2Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2AttributeRepository extends JpaRepository<OAuth2Attribute, Long> {
}
