package bloodrunserver.controllers;

import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
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
                playerList.add(new Player(LobbyDTO.getUserDtoOne().getUsername()));
            }
            if(LobbyDTO.getUserDtoTwo() != null)
            {
                playerList.add(new Player(LobbyDTO.getUserDtoTwo().getUsername()));
            }
            if(LobbyDTO.getUserDtoThree() != null)
            {
                playerList.add(new Player(LobbyDTO.getUserDtoThree().getUsername()));;
            }
            if(LobbyDTO.getUserDtoFour() != null)
            {
                playerList.add(new Player(LobbyDTO.getUserDtoFour().getUsername()));
            }
            Lobby lobby = new Lobby(playerList);

            Message message = new Message("WebSocket", lobby.toJson(), MessageType.CREATE_LOBBY);

            Server.getExecutor().submit(new MessageExecutor(message));

            GameInfoDto gameInfoDto = new GameInfoDto();

            return gameInfoDto;
        }


        return null;


    }
}
