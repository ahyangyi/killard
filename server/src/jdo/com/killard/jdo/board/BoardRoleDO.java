package com.killard.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.jdo.AttributeHandler;
import com.killard.jdo.FunctionHelper;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.board.descriptor.BoardRoleDescriptorDO;
import com.killard.jdo.board.descriptor.BoardAttributeDescriptorDO;
import com.killard.jdo.board.player.PlayerRecordDO;
import com.killard.jdo.card.RoleDO;
import com.killard.jdo.card.descriptor.AttributeDescriptorDO;
import com.killard.jdo.card.descriptor.RoleDescriptorDO;
import com.killard.environment.ActionValidator;
import com.killard.environment.BeforeAction;
import com.killard.environment.AfterAction;
import com.killard.environment.event.ActionListener;
import com.killard.card.Action;
import com.killard.card.Player;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

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
public class BoardRoleDO extends DescriptableDO<BoardRoleDescriptorDO> implements ActionListener {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key packageKey;

    @Persistent
    private String name;

    @Persistent(serialized = "true")
    private List<AttributeHandler> validators;

    @Persistent(serialized = "true")
    private List<AttributeHandler> before;

    @Persistent(serialized = "true")
    private List<AttributeHandler> after;

    @Persistent
    private SortedSet<BoardRoleDescriptorDO> descriptors;

    @NotPersistent
    private BoardManagerDO boardManager;

    public BoardRoleDO(BoardPackageDO pack, RoleDO role) {
        this.packageKey = pack.getKey();
        this.name = role.getName();
        validators = new ArrayList<AttributeHandler>(Arrays.asList(role.getValidators()));
        before = new ArrayList<AttributeHandler>(Arrays.asList(role.getBefore()));
        after = new ArrayList<AttributeHandler>(Arrays.asList(role.getAfter()));

        this.descriptors = new TreeSet<BoardRoleDescriptorDO>();
        for (RoleDescriptorDO descriptor : role.getAllDescriptors()) {
            this.descriptors.add(new BoardRoleDescriptorDO(this, descriptor));
        }
    }

    public void restore(BoardManagerDO boardManager) {
        this.boardManager = boardManager;
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

    protected SortedSet<BoardRoleDescriptorDO> getDescriptors() {
        return descriptors;
    }

    @ActionValidator(actionClass = Action.class, selfTargeted = false)
    public List<Action> validateAction(Player owner, Action action) {
        List<Action> result = FunctionHelper.handler(boardManager, owner, action, validators);
        getLog().fine("validate " + action.getClass().getSimpleName() + " : " + result);
        return result;
    }

    @BeforeAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> beforeAction(Player owner, Action action) {
        return FunctionHelper.handler(boardManager, owner, action, before);
    }

    @AfterAction(actionClass = Action.class, selfTargeted = false)
    public List<Action> afterAction(Player owner, Action action) {
        return FunctionHelper.handler(boardManager, owner, action, after);
    }

    public Logger getLog() {
        return Logger.getLogger(getClass().getName());
    }
}
