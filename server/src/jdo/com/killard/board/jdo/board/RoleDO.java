package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
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
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Collections;

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
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private String name;

    @Persistent
    private Boolean visible;

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

    public RoleDO(PackageDO pack, String name, boolean visible,
                  List<AttributeHandler> validators,
                  List<AttributeHandler> before,
                  List<AttributeHandler> after) {
        this.packageKey = pack.getKey();
        this.name = name;
        this.visible = visible;
        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);
        this.properties = new TreeSet<RolePropertyDO>();
        this.descriptors = new TreeSet<RoleDescriptorDO>();
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public RolePropertyDO[] getProperties() {
        return properties.toArray(new RolePropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new RolePropertyDO(this, name, data));
    }

    protected boolean removeProperty(RolePropertyDO property) {
        return properties.remove(property);
    }

    public boolean isVisible() {
        return visible;
    }

    public RoleDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new RoleDescriptorDO[descriptors.size()]);
    }

    public boolean addDescriptor(RoleDescriptorDO descriptor) {
        return descriptors.add(descriptor);
    }

    public boolean removeDescriptor(RoleDescriptorDO descriptor) {
        return descriptors.remove(descriptor);
    }

    public Key getPackageKey() {
        return packageKey;
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

    public RoleDO clone(PackageDO pack) {
        return new RoleDO(pack, getName(), isVisible(), validators, before, after);
    }
}
