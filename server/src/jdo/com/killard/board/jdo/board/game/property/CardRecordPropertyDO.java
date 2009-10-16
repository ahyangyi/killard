package com.killard.board.jdo.board.game.property;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.board.game.CardRecordDO;
import com.killard.board.jdo.board.property.MetaCardPropertyDO;
import com.killard.board.jdo.PropertyDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
public class CardRecordPropertyDO extends RecordPropertyDO {

    public CardRecordPropertyDO(Key ownerKey, String name, String data) {
        super(ownerKey, name, data);
    }

    public CardRecordPropertyDO(Key ownerKey, PropertyDO origin) {
        super(ownerKey, origin);
    }
}
