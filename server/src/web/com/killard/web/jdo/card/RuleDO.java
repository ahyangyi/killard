package com.killard.web.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.killard.web.jdo.AttributeHandler;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
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
public class RuleDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private Text definition;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> before = new ArrayList<AttributeHandler>();

    @Persistent(serialized = "true")
    private List<AttributeHandler> after = new ArrayList<AttributeHandler>();

    public RuleDO(PackageDO pack,
                  List<AttributeHandler> validators,
                  List<AttributeHandler> before,
                  List<AttributeHandler> after) {
        this.packageKey = pack.getKey();
        if (validators != null) this.validators.addAll(validators);
        if (before != null) this.before.addAll(before);
        if (after != null) this.after.addAll(after);
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public AttributeHandler[] getValidators() {
        return validators.toArray(new AttributeHandler[validators.size()]);
    }

    public AttributeHandler[] getBefore() {
        return before.toArray(new AttributeHandler[before.size()]);
    }

    public AttributeHandler[] getAfter() {
        return after.toArray(new AttributeHandler[after.size()]);
    }

    public String getDefinition() {
        return definition.getValue();
    }

    public void setDefinition(String definition) {
        this.definition = new Text(definition);
    }

    public RuleDO clone(PackageDO pack) {
        return new RuleDO(pack, validators, before, after);
    }
}
