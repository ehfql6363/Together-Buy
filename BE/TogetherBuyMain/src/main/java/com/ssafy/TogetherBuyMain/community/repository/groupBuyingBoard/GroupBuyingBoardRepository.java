package com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupBuyingBoardRepository extends JpaRepository<GroupBuyingBoard, Long>, GroupBuyingBoardRepositoryCustom {

    Page<GroupBuyingBoard> findByProduct_SubCategory_Category_CategoryIdAndProduct_SubCategory_Id(
            Long categoryId, Long subCategoryId, Pageable pageable
    );

    Page<GroupBuyingBoard> findByProduct_SubCategory_Category_CategoryId(
            Long categoryId, Pageable pageable
    );

    @Query(value = """
            SELECT 
                g.group_buying_board_id AS groupBuyingBoardId,
                g.title AS groupBuyingBoardTitle,
                f.current_amount AS currentAmount,
                p.total AS totalAmount,
                CONCAT_WS(' ', m.sido, m.sigungu, m.eupmyeondong, m.ri, m.load_name, m.load_number) AS meetingLocation,
                f.start_time AS meetingStartTime,
                f.end_time AS meetingEndTime,
                (SELECT pi.url 
                 FROM product_image pi 
                 WHERE pi.product_id = p.product_id 
                 LIMIT 1) AS productMainImage,
                GROUP_CONCAT(fd.day_of_week) AS dayOfWeek
            FROM 
                group_buying_board g
            JOIN 
                form f ON g.group_buying_board_id = f.group_buying_board_id
            JOIN 
                product p ON g.product_id = p.product_id
            JOIN 
                meeting_location m ON f.meeting_location_id = m.meeting_location_id
            LEFT JOIN 
                form_days fd ON f.form_id = fd.form_id
            WHERE f.current_amount * 100 / p.total >= 80
            GROUP BY 
                g.group_buying_board_id,
                g.title,
                f.current_amount,
                p.total,
                meetingLocation,
                f.start_time,
                f.end_time,
                productMainImage
            """,
            nativeQuery = true)
    List<Object[]> findExpiringGroupBuyingBoards();
}
