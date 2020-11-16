package capstone.amidst.data;

import capstone.amidst.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GamePlayersTestDouble implements GameRepository {

    private ArrayList<Game> gameArrayList = new ArrayList<>();

    public GamePlayersTestDouble(){
        gameArrayList.add(new Game(1,"HELPME",1,1));
        gameArrayList.add(new Game(2,"HELPME",2,1));
        gameArrayList.add(new Game(3,"HELPME",3,1));
        gameArrayList.add(new Game(4,"HELPME",4,1));
    }


    @Override
    public List<Game> findAll() {
        return new ArrayList<>(gameArrayList);
    }

    @Override
    public Game findById(int gameId) {
        for(Game g: gameArrayList){
            if(g.getGameId() == gameId){
                return g;
            }
        }
        return null;
    }

    @Override
    public List<Game> findByGameCode(String gameCode) {
        return gameArrayList;
    }

    @Override
    public Game add(Game game) {
        return null;
    }

    @Override
    public boolean update(Game game) {
        return true;
    }

    @Override
    public boolean deleteById(int gameId) {
        return false;
    }
}
