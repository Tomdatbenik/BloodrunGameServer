package bloodrunserver.models.dto;

import bloodrunserver.models.Player;

import java.util.List;

public class GameFinishedDto {

    private UserDto winner;

    private LobbyDto lobby;

    public GameFinishedDto(List<Player> players, Player winner) {

        lobby = new LobbyDto();

        if(players.size() >= 1) {
            if(players.get(0).getUsername() != null)
            {
                lobby.setUserDtoOne(new UserDto(0,players.get(0).getUsername()));
            }
        }


        if(players.size() >= 2)
        {
            if(players.get(1).getUsername() != null)
            {
                lobby.setUserDtoTwo(new UserDto(0,players.get(1).getUsername()));
            }
        }

        if(players.size() >= 3)
        {
            if(players.get(2).getUsername() != null)
            {
                lobby.setUserDtoTwo(new UserDto(0,players.get(2).getUsername()));
            }
        }

        if(players.size() >= 4)
        {
            if(players.get(3).getUsername() != null)
            {
                lobby.setUserDtoTwo(new UserDto(0,players.get(3).getUsername()));
            }
        }

        this.winner = new UserDto(0,winner.getUsername());
    }
}
