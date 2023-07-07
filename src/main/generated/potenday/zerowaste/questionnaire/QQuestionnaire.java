package potenday.zerowaste.questionnaire;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionnaire is a Querydsl query type for Questionnaire
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionnaire extends EntityPathBase<Questionnaire> {

    private static final long serialVersionUID = 585584940L;

    public static final QQuestionnaire questionnaire = new QQuestionnaire("questionnaire");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath visible = createBoolean("visible");

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QQuestionnaire(String variable) {
        super(Questionnaire.class, forVariable(variable));
    }

    public QQuestionnaire(Path<? extends Questionnaire> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionnaire(PathMetadata metadata) {
        super(Questionnaire.class, metadata);
    }

}

