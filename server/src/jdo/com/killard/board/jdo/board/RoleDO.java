package com.killard.board.jdo.board;

import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.RoleDescriptorDO;
import com.killard.board.jdo.board.property.RolePropertyDO;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
import java.util.Collections;
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
public class RoleDO extends DescriptableDO<RoleDO, RolePropertyDO, RoleDescriptorDO> {

    @Persistent
    private boolean visible;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private List<RolePropertyDO> properties;

    @Persistent
    @Element(dependent = "true")
    private transient List<RoleDescriptorDO> descriptors;

    protected RoleDO(PackageDO pack, String name,
                     List<AttributeHandler> validators,
                     List<AttributeHandler> before,
                     List<AttributeHandler> after) {
        super(pack, name);

        this.visible = true;

        this.validators = new ArrayList<AttributeHandler>(validators);
        this.before = new ArrayList<AttributeHandler>(before);
        this.after = new ArrayList<AttributeHandler>(after);

        this.properties = new ArrayList<RolePropertyDO>();
        this.descriptors = new ArrayList<RoleDescriptorDO>();
    }

    protected RoleDO(PackageDO pack, RoleDO source) {
        this(pack, source.getName(), source.getValidators(), source.getBefore(), source.getAfter());
        for (RolePropertyDO property : source.getProperties()) properties.add(new RolePropertyDO(this, property));
//        for (RoleDescriptorDO descriptor : source.descriptors)
//            descriptors.add(new RoleDescriptorDO(this, descriptor));
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

    public List<AttributeHandler> getValidators() {
        return Collections.unmodifiableList(validators);
    }

    public List<AttributeHandler> getBefore() {
        return Collections.unmodifiableList(before);
    }

    public List<AttributeHandler> getAfter() {
        return Collections.unmodifiableList(after);
    }
}
