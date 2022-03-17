/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import commons.Answer;
import commons.Player;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * @param name the name with which the player wants to play singleplayer
     * @return true if the server accepts
     */
    public boolean startSingle(String name) {
        return askConfirmation("api/play/single", name);
    }

    /**
     * @param name the name with which the player wants to join the waiting room
     * @return true if the server accepts
     */
    public boolean enterWaitingRoom(String name) {
        return askConfirmation("api/play/join", name);
    }

    /**
     * @param path where to send the request
     * @return true if request is ok
     */
    public boolean askConfirmation(String path, String name){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(path) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(name, APPLICATION_JSON), Boolean.class);
    }

    /**
     * @return the list of waiting players
     */
    public List<Player> getWaitingPlayers() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/waitingroom") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Player>>() {});
    }

    /**
     * @param player that is going to leave
     */
    public void leaveWaitingroom(Player player) {
         ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/waitingroom/leave") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON), Boolean.class);
    }

    /**
     * @param players is the list of the visible players for the client
     * @return the updated list of the players when something has changed
     */
    public List<Player> pollWaitingroom(List<Player> players) {
        return ClientBuilder.newClient(new ClientConfig()) //F
                .target(SERVER).path("api/play/waitingroom/poll") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(players, APPLICATION_JSON), List.class);
    }

    public int grantPoints(Answer answer) {
      return ClientBuilder.newClient(new ClientConfig())
              .target(SERVER).path("api/currentplayerscore/grantpoints")
              .request(APPLICATION_JSON)
              .accept(APPLICATION_JSON)
              .post(Entity.entity(answer, APPLICATION_JSON), Integer.class);
    }
}