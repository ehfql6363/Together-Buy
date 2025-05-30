package com.ssafy.TogetherBuyMain.global.util;

import com.ssafy.TogetherBuyMain.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyMain.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.MemberLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressUtil {

    private String cityOrProvince; // 시/도
    private String cityOrCounty;   // 시/군/구
    private String townOrDistrict; // 읍/면/동 (Optional)
    private String village;        // 리 (Optional)
    private String roadName;       // 도로명
    private String roadNumber;     // 도로번호
    private String detailAddress;  // 상세 주소 (Optional)

    public static MemberLocation stringToEntity(String address, Member member) {
        // 정규식 패턴 정의
        String regex = "^(?<CityOrProvince>[가-힣]+(?:도|특별시|광역시|자치시|직할시|특별자치도)?)\\s" + // 시/도
                "(?<CityOrCounty>[가-힣]+(?:시|군|구))\\s" +                        // 시/군/구
                "(?<TownOrDistrict>[가-힣]+(?:읍|면|동))?\\s?" +                   // 읍/면/동 (Optional)
                "(?<Village>[가-힣]+리)?\\s?" +                                  // 리 (Optional)
                "(?<RoadName>[가-힣0-9]+(?:로|길))\\s" +                            // 도로명
                "(?<RoadNumber>[0-9]+(?:-[0-9]+)?)\\s?" +                       // 도로번호
                "(?<DetailAddress>.+)?";                                        // 상세주소 (Optional)

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        if (!matcher.matches()) {
            throw new BusinessLogicException(ExceptionCode.ADDRESS_INVALID_FORMAT);
        }

        // 추출된 매칭 값들로 MemberLocation 생성
        MemberLocation location = MemberLocation.builder()
                .member(member).build();

        location.updateLocationFromAddress(matcher);

        return location;
    }
    public static Matcher getParsingAddressArray(String address) {
        // 정규식 패턴 정의
        String regex = "^(?<CityOrProvince>[가-힣]+(?:도|특별시|광역시|자치시|직할시|특별자치도)?)\\s" + // 시/도
                "(?<CityOrCounty>[가-힣]+(?:시|군|구))\\s" +                        // 시/군/구
                "(?<TownOrDistrict>[가-힣]+(?:읍|면|동))?\\s?" +                   // 읍/면/동 (Optional)
                "(?<Village>[가-힣]+리)?\\s?" +                                  // 리 (Optional)
                "(?<RoadName>[가-힣0-9]+(?:로|길))\\s" +                            // 도로명
                "(?<RoadNumber>[0-9]+(?:-[0-9]+)?)\\s?" +                       // 도로번호
                "(?<DetailAddress>.+)?";                                        // 상세주소 (Optional)

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        if (!matcher.matches()) {
            throw new BusinessLogicException(ExceptionCode.ADDRESS_INVALID_FORMAT);
        }

        return matcher;
    }
}