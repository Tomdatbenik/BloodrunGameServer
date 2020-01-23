package bloodrunserver.controllers;

import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import bloodrunserver.models.dto.GameInfoDto;
import bloodrunserver.models.dto.LobbyDto;
import bloodrunserver.server.Server;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameLobbyController {

    GameLogic gameLogic = new GameLogic();

    @PostMapping("/createlobby")
    public GameInfoDto AddLobby(@RequestBody String LobbyJson)
    {
        Gson gson = new Gson();

        LobbyDto LobbyDTO = gson.fromJson(LobbyJson,LobbyDto.class);

        List<Player> playerList = new ArrayList<>();

        if(LobbyDTO != null)
        {
            if(LobbyDTO.getUserDtoOne() != null)
            {
                if(LobbyDTO.getUserDtoOne().getId() != 0)
                {
                    playerList.add(new Player(LobbyDTO.getUserDtoOne().getUsername()));
                }
            }
            if(LobbyDTO.getUserDtoTwo() != null)
            {
                if(LobbyDTO.getUserDtoTwo().getId() != 0)
                {
                    playerList.add(new Player(LobbyDTO.getUserDtoTwo().getUsername()));
                }
            }
            if(LobbyDTO.getUserDtoThree() != null)
            {
                if(LobbyDTO.getUserDtoThree().getId() != 0)
                {
                    playerList.add(new Player(LobbyDTO.getUserDtoThree().getUsername()));
                }
            }
            if(LobbyDTO.getUserDtoFour() != null)
            {
                if(LobbyDTO.getUserDtoFour().getId() != 0)
                {
                    playerList.add(new Player(LobbyDTO.getUserDtoFour().getUsername()));
                }
            }
            Lobby lobby = new Lobby(playerList);


            synchronized (gameLogic)
            {
                gameLogic.createGame(lobby);
            }

            GameInfoDto gameInfoDto = new GameInfoDto();

            return gameInfoDto;
        }


        return null;
    }


}
