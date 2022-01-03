package net.foggies.moderngens.impl.storage;

import foggielib.database.Database;
import foggielib.location.SimpleLocation;
import foggielib.serializer.SerializeUtils;
import net.foggies.moderngens.impl.obj.GenPlayer;
import net.foggies.moderngens.impl.obj.Generator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PlayerDatabase extends Database {

    public PlayerDatabase(String host, String port, String database, String userName, String password) {
        super(host, port, database, userName, password);
        this.createTable();
    }

    @Override
    public void createTable() {
        executeQuery("CREATE TABLE IF NOT EXISTS Generators(" +
                "OWNER VARCHAR(37), GENERATORS TEXT, PRIMARY KEY (OWNER)" +
                ")");
    }

    public Optional<GenPlayer> get(UUID uuid){
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT * FROM Generators WHERE OWNER=?"
             )) {

            ps.setString(1, uuid.toString());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next())
                return Optional.of(new GenPlayer(
                        uuid,
                        (HashMap<SimpleLocation, Generator>) SerializeUtils.fromString(resultSet.getString("GENERATORS"))
                ));

        } catch (SQLException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void put(GenPlayer genPlayer) throws IOException {
        executeQuery("INSERT IGNORE INTO Generators VALUES(?,?)",
                genPlayer.getUuid().toString(), SerializeUtils.toString(genPlayer.getGenMap()));
    }

    public void save(GenPlayer genPlayer) throws IOException {
        executeQuery("UPDATE Generators SET GENERATORS=? WHERE OWNER=?",
                SerializeUtils.toString(genPlayer.getGenMap()), genPlayer.getUuid().toString());
    }

}
