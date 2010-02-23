package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.killard.board.card.Action;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.Skill;
import com.killard.board.card.SkillTarget;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.SkillDescriptorDO;
import com.killard.board.jdo.board.property.SkillPropertyDO;
import com.killard.board.parser.Context;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.Function;
import com.killard.board.parser.GlobalContext;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class SkillDO extends DescriptableDO<SkillDO, SkillPropertyDO, SkillDescriptorDO> implements Skill<SkillDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private Text definition;

    @Persistent
    private List<String> targets;

    @Persistent
    private int cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent
    @Element(dependent = "true")
    private Set<SkillPropertyDO> properties;

    @Persistent
    @Element(dependent = "true")
    private transient Set<SkillDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private transient Blob image;

    protected SkillDO(MetaCardDO card, String name, String definition, List<String> targets, int cost, Function function) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(card.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();

        this.name = name;

        this.definition = new Text(definition);

        this.targets = new ArrayList<String>(targets);
        this.targets.add(SkillTarget.self.name());

        this.cost = cost;
        this.function = function;

        this.properties = new HashSet<SkillPropertyDO>();
        this.descriptors = new HashSet<SkillDescriptorDO>();
    }

    protected SkillDO(MetaCardDO card, SkillDO source) {
        this(card, source.name, source.getDefinition(), source.targets, source.cost, source.function);
        this.cost = source.cost;
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new SkillPropertyDO(this, name, data));
    }

    public String getDefinition() {
        return definition.getValue();
    }

    public SkillPropertyDO[] getProperties() {
        return properties.toArray(new SkillPropertyDO[properties.size()]);
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillTarget[] getTargets() {
        SkillTarget[] result = new SkillTarget[targets.size()];
        for (int i = 0; i < result.length; i++) result[i] = SkillTarget.valueOf(targets.get(i));
        return result;
    }

    public int getCost() {
        return cost;
    }

    public Function getFunction() {
        return function;
    }

    public List<Action> execute(Board board, Card owner, Object... targets) {
        Context ctx = new GlobalContext(owner);
        ctx.addVariable("targets", targets);
        ctx.addVariable("board", board);
        try {
            function.execute(ctx);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ctx.getActions();
    }

    public SkillDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new SkillDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new SkillDescriptorDO(this, locale, name, description));
    }

    public boolean isRenderable() {
        return image != null;
    }

    public byte[] getImageData() {
        return image.getBytes();
    }

    public void setImageData(byte[] data) {
        image = new Blob(data);
    }
}
