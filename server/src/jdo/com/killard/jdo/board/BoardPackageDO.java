package com.killard.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.killard.jdo.card.ElementSchoolDO;
import com.killard.jdo.card.PackageDO;

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
public class BoardPackageDO extends BoardDescriptableDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key packageKey;

    @Persistent
    private String id;

    @Persistent
    private BoardRuleDO rule;

    @Persistent(mappedBy = "pack", defaultFetchGroup = "false")
    private SortedSet<BoardElementSchoolDO> elementSchools;

    @Persistent
    private String name;

    @Persistent
    private Text description;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    public BoardPackageDO(PackageDO pack) {
        this.packageKey = pack.getKey();
        this.id = pack.getId();
        this.rule = new BoardRuleDO(this, pack.getRule());
        this.elementSchools = new TreeSet<BoardElementSchoolDO>();
        for (ElementSchoolDO elementSchool : pack.getElementSchools()) {
            elementSchools.add(new BoardElementSchoolDO(this, elementSchool));
        }
        this.name = pack.getDescriptor().getName();
//        this.description = new Text(pack.getDescriptor().getDescription());
//        this.image = new Blob(pack.getDescriptor().getImageData());
    }

    public Key getKey() {
        return key;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public String getId() {
        return id;
    }

    public BoardRuleDO getRule() {
        return rule;
    }

    public BoardElementSchoolDO[] getElementSchools() {
        return elementSchools.toArray(new BoardElementSchoolDO[elementSchools.size()]);
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
}
