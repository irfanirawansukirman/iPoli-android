package io.ipoli.android.player.persistence;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.Gson;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ipoli.android.Constants;
import io.ipoli.android.app.App;
import io.ipoli.android.app.persistence.BaseFirebasePersistenceService;
import io.ipoli.android.app.utils.StringUtils;
import io.ipoli.android.player.Player;
import io.ipoli.android.quest.persistence.OnDataChangedListener;
import io.ipoli.android.quest.persistence.OnOperationCompletedListener;

/**
 * Created by Venelin Valkov <venelin@curiousily.com>
 * on 7/27/16.
 */
public class FirebasePlayerPersistenceService extends BaseFirebasePersistenceService<Player> implements PlayerPersistenceService {

    public FirebasePlayerPersistenceService(Bus eventBus, Gson gson) {
        super(eventBus, gson);
    }

    @Override
    public void save(Player obj) {
        save(obj, null);
    }

    @Override
    public void save(Player player, OnOperationCompletedListener listener) {
        DatabaseReference collectionRef = getCollectionReference();
        boolean isNew = StringUtils.isEmpty(player.getId());
        if (isNew) {
            DatabaseReference objRef = collectionRef.push();
            player.setId(objRef.getKey());
            objRef.setValue(player, (databaseError, databaseReference) -> {
                if (listener != null) {
                    listener.onComplete();
                }
            });
        } else {
            player.markUpdated();
            DatabaseReference objRef = collectionRef.child(player.getId());
            Map<String, Object> playerData = new HashMap<>();
            playerData.put("id", player.getId());
            playerData.put("coins", player.getCoins());
            playerData.put("experience", player.getExperience());
            playerData.put("level", player.getLevel());
            playerData.put("avatar", player.getAvatar());
            playerData.put("updatedAt", player.getUpdatedAt());
            playerData.put("createdAt", player.getCreatedAt());
            objRef.updateChildren(playerData, (databaseError, databaseReference) -> {
                if (listener != null) {
                    listener.onComplete();
                }
            });
        }
    }

    @Override
    public void find(OnDataChangedListener<Player> listener) {
        DatabaseReference playerRef = getCollectionReference().child(App.getPlayerId());
        listenForSingleModelChange(playerRef, listener);
    }

    @Override
    public void listen(OnDataChangedListener<Player> listener) {
        DatabaseReference playerRef = getCollectionReference().child(App.getPlayerId());
        listenForModelChange(playerRef, listener);
    }

    @Override
    protected Class<Player> getModelClass() {
        return Player.class;
    }

    @Override
    protected String getCollectionName() {
        return "players";
    }

    @Override
    protected DatabaseReference getCollectionReference() {
        return database.getReference(Constants.API_VERSION).child(getCollectionName());
    }

    @Override
    protected GenericTypeIndicator<Map<String, Player>> getGenericMapIndicator() {
        return new GenericTypeIndicator<Map<String, Player>>() {

        };
    }

    @Override
    protected GenericTypeIndicator<List<Player>> getGenericListIndicator() {
        return new GenericTypeIndicator<List<Player>>() {
        };
    }
}