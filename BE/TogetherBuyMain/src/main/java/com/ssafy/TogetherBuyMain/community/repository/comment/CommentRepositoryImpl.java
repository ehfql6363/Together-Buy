package com.ssafy.TogetherBuyMain.community.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import com.ssafy.TogetherBuyMain.community.entity.comment.QComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<Comment> findCommentsByBoardId(Long boardId, Pageable pageable) {
        QComment comment = QComment.comment;
        // 댓글 리스트 조회
        List<Comment> comments = queryFactory
                .selectFrom(comment)
                .leftJoin(comment.member).fetchJoin() // N+1 문제 방지를 위한 fetch join
                .leftJoin(comment.freeBoard).fetchJoin()
                .where(comment.freeBoard.id.eq(boardId))
                .orderBy(comment.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 댓글 개수 조회

        long total = Optional.ofNullable(
                queryFactory
                        .select(comment.count())
                        .from(comment)
                        .where(comment.freeBoard.id.eq(boardId))
                        .fetchOne()
        ).orElse(0L);

        return PageableExecutionUtils.getPage(comments, pageable, () -> total);
    }
}
