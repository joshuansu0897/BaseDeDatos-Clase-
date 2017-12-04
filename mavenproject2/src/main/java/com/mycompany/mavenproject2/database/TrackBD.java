/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.Track;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author joshuansu
 */
public class TrackBD extends BD {

    private final static Logger logger = Logger.getLogger(TrackBD.class);
    private static TrackBD instance;

    private TrackBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static TrackBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new TrackBD(
                        c.getConfiguracion("servidor"),
                        c.getConfiguracion("tipoBaseDeDatos"),
                        c.getConfiguracion("port"),
                        c.getConfiguracion("nombreBaseDeDatos"),
                        c.getConfiguracion("usuario"),
                        c.getConfiguracion("password")
                );
            } catch (Exception e) {
                logger.error("Error al conectar con base de datos.", e);
            }
        }
        return instance;
    }

    public List<Track> getTracks() throws SQLException {
        List<Track> l = new ArrayList<>();
        String query = "SELECT id, title, length, comment\n"
                + "	FROM public.\"CD_Track\";";
        try (Connection cn = getConnection(); PreparedStatement stm = cn.prepareStatement(query)) {
            try (ResultSet rsSer = stm.executeQuery()) {
                while (rsSer.next()) {
                    Track t = new Track();
                    t.setId(rsSer.getLong("id"));
                    t.setTitle(rsSer.getString("title"));
                    t.setLength(rsSer.getString("length"));
                    t.setComment(rsSer.getString("comment"));
                    l.add(t);
                }
            }
        }
        return l;
    }

    public Track getTrackId(long id) throws SQLException {
        Track t = null;
        String query = "SELECT title, length, comment\n"
                + "	FROM public.\"CD_Track\" where id = ?";
        try (Connection cn = getConnection(); PreparedStatement stm = cn.prepareStatement(query)) {
            stm.setLong(1, id);
            try (ResultSet rsSer = stm.executeQuery()) {
                while (rsSer.next()) {
                    t = new Track();
                    t.setId(id);
                    t.setTitle(rsSer.getString("title"));
                    t.setLength(rsSer.getString("length"));
                    t.setComment(rsSer.getString("comment"));
                }
            }
        }
        return t;
    }

    public void saveTrack(Track t) throws Exception {
        boolean nueva = t.getId() == -1;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"CD_Track\"(\n"
                        + "	title, length, comment)\n"
                        + "	VALUES (?, ?, ?);";
                stm = con.prepareStatement(query);
                stm.setString(1, t.getTitle());
                stm.setString(2, t.getLength());
                stm.setString(3, t.getComment());
            } else {
                query = "UPDATE public.\"CD_Track\"\n"
                        + "	SET title=?, length=?, comment=?\n"
                        + "	WHERE id=?;";
                stm = con.prepareStatement(query);
                stm.setString(1, t.getTitle());
                stm.setString(2, t.getLength());
                stm.setString(3, t.getComment());
                stm.setLong(4, t.getId());
            }
            stm.executeUpdate();
            stm.close();
        }
    }

    public void deleteTrack(long id) throws Exception {
        String query = "DELETE FROM public.\"CD_Track\" WHERE id = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            stmSer.executeUpdate();
        }
    }

    public void deleteTracks(List<Long> ids) throws Exception {
        ArtOnTraOnCD.getInstance().deleteTracks(ids);
        String query = "DELETE FROM public.\"CD_Track\" WHERE id = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            for (long id : ids) {
                stmSer.setLong(1, id);
                stmSer.addBatch();
            }
            stmSer.executeBatch();
        }
    }
}
