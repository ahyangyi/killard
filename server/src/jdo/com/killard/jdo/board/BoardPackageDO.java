package com.killard.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.jdo.card.ElementSchoolDO;
import com.killard.jdo.card.PackageDO;
import com.killard.jdo.card.descriptor.PackageDescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.board.descriptor.BoardPackageDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
public class BoardPackageDO extends DescriptableDO<BoardPackageDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key packageKey;

    @Persistent
    private String name;

    @Persistent
    private BoardRuleDO rule;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<BoardElementSchoolDO> elementSchools;

    @Persistent
    private SortedSet<BoardPackageDescriptorDO> descriptors;

    public BoardPackageDO(Key boardManagerKey, PackageDO pack) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManagerKey);
        keyBuilder.addChild(getClass().getSimpleName(), pack.getKey().getId());
        this.key = keyBuilder.getKey();
        this.packageKey = pack.getKey();
        this.rule = new BoardRuleDO(this, pack.getRule());
        this.elementSchools = new TreeSet<BoardElementSchoolDO>();
        for (ElementSchoolDO elementSchool : pack.getElementSchools()) {
            elementSchools.add(new BoardElementSchoolDO(this, elementSchool));
        }
        this.name = pack.getName();

        this.descriptors = new TreeSet<BoardPackageDescriptorDO>();
        for (PackageDescriptorDO descriptor : pack.getAllDescriptors()) {
            this.descriptors.add(new BoardPackageDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public String getName() {
        return name;
    }

    public BoardRuleDO getRule() {
        return rule;
    }

    public BoardElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new BoardElementSchoolDO[elementSchools.size()]);
    }

    protected SortedSet<BoardPackageDescriptorDO> getDescriptors() {
        return descriptors;
    }
}
