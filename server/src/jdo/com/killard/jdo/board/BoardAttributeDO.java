package com.killard.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.killard.card.Action;
import com.killard.card.Attribute;
import com.killard.card.CardInstance;
import com.killard.environment.ActionValidator;
import com.killard.environment.AfterAction;
import com.killard.environment.BeforeAction;
import com.killard.jdo.AttributeHandler;
import com.killard.jdo.FunctionHelper;
import com.killard.jdo.card.AttributeDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BoardAttributeDO extends BoardDescriptableDO implements Attribute {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardElementSchoolDO elementSchool;

    @Persistent
    private String id;

    @Persistent
    private Boolean hidden;

    @Persistent
    private Boolean useful;

    @Persistent
    private Boolean harmful;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> before = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> after = new ArrayList<AttributeHandler>();

    @Persistent
    private String name;

    @Persistent
    private Text description;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    public BoardAttributeDO(BoardElementSchoolDO elementSchool, AttributeDO attribute) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(elementSchool.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), attribute.getId());
        this.key = keyBuilder.getKey();

        this.elementSchool = elementSchool;
        this.id = attribute.getId();
        this.hidden = attribute.isHidden();
        this.useful = attribute.isUseful();
        this.harmful = attribute.isHarmful();

        validators.addAll(Arrays.asList(attribute.getValidators()));
        before.addAll(Arrays.asList(attribute.getBefore()));
        after.addAll(Arrays.asList(attribute.getAfter()));

        if (attribute.getDescriptor() != null) {
        this.name = attribute.getDescriptor().getName();
        this.description = new Text(attribute.getDescriptor().getDescription());
        this.image = new Blob(attribute.getDescriptor().getImageData());
        }
    }

    public Key getKey() {
        return key;
    }

    public BoardElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isUseful() {
        return useful;
    }

    public boolean isHarmful() {
        return harmful;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description.getValue();
    }

    public byte[] getImageData() {
        return image.getBytes();
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(CardInstance card, Action action) {
        return FunctionHelper.handler(card, action, validators);
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(CardInstance card, Action action) {
        return FunctionHelper.handler(card, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(CardInstance card, Action action) {
        return FunctionHelper.handler(card, action, after);
    }

}
