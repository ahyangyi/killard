package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.RoleDescriptorDO;
import com.killard.board.jdo.board.property.RolePropertyDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class RoleDO extends DescriptableDO<RoleDO, RolePropertyDO, RoleDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private boolean visible;

    @Persistent
    private Text definition;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    @Persistent
    private SortedSet<RolePropertyDO> properties;

    @Persistent
    private SortedSet<RoleDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    protected RoleDO(PackageDO pack, String name,
                     String definition,
                     List<AttributeHandler> validators,
                     List<AttributeHandler> before,
                     List<AttributeHandler> after) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(pack.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();

        this.name = name;
        this.visible = true;

        this.definition = new Text(definition);

        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);

        this.properties = new TreeSet<RolePropertyDO>();
        this.descriptors = new TreeSet<RoleDescriptorDO>();
    }

    protected RoleDO(PackageDO pack, RoleDO source) {
        this(pack, source.getName(), source.getDefinition(), source.getValidators(), source.getBefore(), source.getAfter());
        for (RolePropertyDO property : source.getProperties()) properties.add(new RolePropertyDO(this, property));
//        for (RoleDescriptorDO descriptor : source.descriptors)
//            descriptors.add(new RoleDescriptorDO(this, descriptor));
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new RolePropertyDO(this, name, data));
    }

    public RolePropertyDO[] getProperties() {
        return properties.toArray(new RolePropertyDO[properties.size()]);
    }

    public boolean isVisible() {
        return visible;
    }

    public RoleDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new RoleDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new RoleDescriptorDO(this, locale, name, description));
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

    public List<AttributeHandler> getValidators() {
        return Collections.unmodifiableList(validators);
    }

    public List<AttributeHandler> getBefore() {
        return Collections.unmodifiableList(before);
    }

    public List<AttributeHandler> getAfter() {
        return Collections.unmodifiableList(after);
    }

    public String getDefinition() {
        return definition.getValue();
    }

    public void setDefinition(String definition) {
        this.definition = new Text(definition);
    }
}
