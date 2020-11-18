package capstone.amidst.data;

import capstone.amidst.models.Game;
import capstone.amidst.models.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayerTestDouble implements PlayerRepository {

    private ArrayList<Player> playerArrayList = new ArrayList<>();

    public PlayerTestDouble(){
        playerArrayList.add(new Player(1,"testPlayerAlpha",false, false,1));
        playerArrayList.add(new Player(2,"Computer2",false, false,2));
        playerArrayList.add(new Player(3,"Computer3",false, true,2));
        playerArrayList.add(new Player(4,"Computer4",true, false,2));
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(playerArrayList);
    }

    @Override
    public Player findById(int playerId) {
        for(Player p: playerArrayList){
            if(p.getPlayerId() == playerId){
                return p;
            }
        }
        return null;
    }

    @Override
    public Player findByUserName(String username) {
        for(Player p: playerArrayList){
            if(p.getPlayerName() == username){
                return p;
            }
        }
        return null;
    }

    @Override
    public Player findByAppUserId(int appUserId) {
        return null;
    }

    @Override
    public List<Player> findByIsImposter(boolean Imposter) {
       return null;
    }

    @Override
    public List<Player> findByIsDead(boolean Dead) {
        return null;
    }

    @Override
    public Player add(Player player) {
        return null;
    }

    @Override
    public Player addComputerPlayer(Player player) {
        return null;
    }

    @Override
    public boolean update(Player player) {
        return true;
    }

    @Override
    public Boolean updateIsDead(Player player) {
        player.setDead(true);
        return true;
    }

    @Override
    public Boolean updateIsImposter(Player player) {
        return true;
    }

    @Override
    public Boolean updateResetPlayer(Player player) {
        return true;
    }

    @Override
    public boolean deleteById(int playerId) {
        return false;
    }
}
