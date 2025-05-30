package com.ssafy.TogetherBuyMain.security.oauth.repository;

import com.ssafy.TogetherBuyMain.security.oauth.entity.OAuth2Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2AttributeRepository extends JpaRepository<OAuth2Attribute, Long> {
}
