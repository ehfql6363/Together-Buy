package com.ssafy.TogetherBuyMain.shop.entity.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QForm is a Querydsl query type for Form
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QForm extends EntityPathBase<Form> {

    private static final long serialVersionUID = 315184932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QForm form = new QForm("form");

    public final ListPath<Apply, QApply> applies = this.<Apply, QApply>createList("applies", Apply.class, QApply.class, PathInits.DIRECT2);

    public final NumberPath<Long> currentAmount = createNumber("currentAmount", Long.class);

    public final NumberPath<Long> endTime = createNumber("endTime", Long.class);

    public final NumberPath<Long> formId = createNumber("formId", Long.class);

    public final com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard groupBuyingBoard;

    public final QMeetingLocation meetingLocation;

    public final com.ssafy.TogetherBuyMain.shop.entity.order.QOrder order;

    public final com.ssafy.TogetherBuyMain.member.entity.QMember recipient;

    public final SetPath<DayOfWeek, EnumPath<DayOfWeek>> selectedDays = this.<DayOfWeek, EnumPath<DayOfWeek>>createSet("selectedDays", DayOfWeek.class, EnumPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> startTime = createNumber("startTime", Long.class);

    public final NumberPath<Long> totalAmount = createNumber("totalAmount", Long.class);

    public QForm(String variable) {
        this(Form.class, forVariable(variable), INITS);
    }

    public QForm(Path<? extends Form> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QForm(PathMetadata metadata, PathInits inits) {
        this(Form.class, metadata, inits);
    }

    public QForm(Class<? extends Form> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupBuyingBoard = inits.isInitialized("groupBuyingBoard") ? new com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard(forProperty("groupBuyingBoard"), inits.get("groupBuyingBoard")) : null;
        this.meetingLocation = inits.isInitialized("meetingLocation") ? new QMeetingLocation(forProperty("meetingLocation"), inits.get("meetingLocation")) : null;
        this.order = inits.isInitialized("order") ? new com.ssafy.TogetherBuyMain.shop.entity.order.QOrder(forProperty("order"), inits.get("order")) : null;
        this.recipient = inits.isInitialized("recipient") ? new com.ssafy.TogetherBuyMain.member.entity.QMember(forProperty("recipient"), inits.get("recipient")) : null;
    }

}

