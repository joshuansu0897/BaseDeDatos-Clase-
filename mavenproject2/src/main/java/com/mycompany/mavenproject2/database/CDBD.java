/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.CD;
import com.mycompany.mavenproject2.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author joshuansu
 */
public class CDBD extends BD {

    private final static Logger logger = Logger.getLogger(TrackBD.class);
    private static CDBD instance;

    private CDBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static CDBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new CDBD(
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

    public void deleteCD(long id) throws Exception {
        String query = "DELETE FROM public.\"CD\" WHERE id = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            stmSer.executeUpdate();
        }
    }

    public void deleteCDs(List<Long> ids) throws Exception {
        List<Long> tracksID = new ArrayList<>();
        for (long id : ids) {
            tracksID.addAll(ArtOnTraOnCD.getInstance().getTrackCD(id));
        }
        TrackBD.getInstance().deleteTracks(tracksID);
        String query = "DELETE FROM public.\"CD\" WHERE id = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            for (long id : ids) {
                stmSer.setLong(1, id);
                stmSer.addBatch();
            }
            stmSer.executeBatch();
        }
    }

    public CD getCDid(Long id) throws SQLException {
        CD cd = null;
        String query = "SELECT \"musicGenreCode\", \n"
                + "\"outletCode\", \n"
                + "\"CDSetid\", \n"
                + "title, \n"
                + "cost, \n"
                + "\"dateReleased\", \n"
                + "\"numberOfTracks\", \n"
                + "\"totalPlayingTimes\", \n"
                + "\"variousArtists\", \n"
                + "\"recordCompanyCode\"\n"
                + "	FROM public.\"CD\" WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    cd = new CD();
                    cd.setMusicGenreCode(rs.getString("musicGenreCode"));
                    cd.setOutletCode(rs.getString("outletCode"));
                    cd.setCDSetid(rs.getLong("CDSetid"));
                    cd.setTitle(rs.getString("title"));
                    cd.setCost(rs.getString("cost"));
                    cd.setDateReleased(LocalDate.parse(rs.getString("dateReleased")));
                    cd.setNumberOfTracks(rs.getInt("numberOfTracks"));
                    cd.setTotalPlayingTimes(rs.getInt("totalPlayingTimes"));
                    cd.setVariousArtists(rs.getBoolean("variousArtists"));
                    cd.setRecordCompanyCode(rs.getString("recordCompanyCode"));
                    cd.setId(id);
                }
            }
        }
        return cd;
    }

    public List<CD> getAllCDs() throws SQLException {
        List<CD> l = new ArrayList<>();
        String query = "SELECT id,\n"
                + "\"musicGenreCode\", \n"
                + "\"outletCode\", \n"
                + "\"CDSetid\", \n"
                + "title, \n"
                + "cost, \n"
                + "\"dateReleased\", \n"
                + "\"numberOfTracks\", \n"
                + "\"totalPlayingTimes\", \n"
                + "\"variousArtists\", \n"
                + "\"recordCompanyCode\"\n"
                + "	FROM public.\"CD\"";
        try (Connection con = getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    CD cd = new CD();
                    cd.setId(rs.getLong("id"));
                    cd.setMusicGenreCode(rs.getString("musicGenreCode"));
                    cd.setOutletCode(rs.getString("outletCode"));
                    cd.setCDSetid(rs.getLong("CDSetid"));
                    cd.setTitle(rs.getString("title"));
                    cd.setCost(rs.getString("cost"));
                    cd.setDateReleased(LocalDate.parse(rs.getString("dateReleased")));
                    cd.setNumberOfTracks(rs.getInt("numberOfTracks"));
                    cd.setTotalPlayingTimes(rs.getInt("totalPlayingTimes"));
                    cd.setVariousArtists(rs.getBoolean("variousArtists"));
                    cd.setRecordCompanyCode(rs.getString("recordCompanyCode"));
                    l.add(cd);
                }
            }
        }
        return l;
    }

    public void saveCD(CD cd) throws SQLException {
        boolean nueva = cd.getId() == -1;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"CD\"(\n"
                        + "    \"musicGenreCode\",\n"
                        + "    \"outletCode\",\n"
                        + "    \"CDSetid\",\n"
                        + "    title,\n"
                        + "    cost,\n"
                        + "    \"dateReleased\",\n"
                        + "    \"numberOfTracks\",\n"
                        + "    \"totalPlayingTimes\",\n"
                        + "    \"variousArtists\",\n"
                        + "    \"recordCompanyCode\"\n"
                        + ")\n"
                        + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, cd.getMusicGenreCode());
                stm.setString(2, cd.getOutletCode());
                stm.setLong(3, cd.getCDSetid());
                stm.setString(4, cd.getTitle());
                stm.setString(5, cd.getCost());
                stm.setString(6, Util.LocalDateToString(cd.getDateReleased()));
                stm.setInt(7, cd.getNumberOfTracks());
                stm.setInt(8, cd.getTotalPlayingTimes());
                stm.setBoolean(9, cd.isVariousArtists());
                stm.setString(10, cd.getRecordCompanyCode());
            } else {
                query = "UPDATE public.\"CD\"\n"
                        + "	SET \"musicGenreCode\"=?,\n"
                        + "    \"outletCode\"=?,\n"
                        + "    \"CDSetid\"=?,\n"
                        + "    title=?,\n"
                        + "    cost=?,\n"
                        + "    \"dateReleased\"=?::date,\n"
                        + "    \"numberOfTracks\"=?,\n"
                        + "    \"totalPlayingTimes\"=?,\n"
                        + "    \"variousArtists\"=?,\n"
                        + "    \"recordCompanyCode\"=?\n"
                        + "	WHERE id=?;";
                stm = con.prepareStatement(query);
                stm.setString(1, cd.getMusicGenreCode());
                stm.setString(2, cd.getOutletCode());
                stm.setLong(3, cd.getCDSetid());
                stm.setString(4, cd.getTitle());
                stm.setString(5, cd.getCost());
                stm.setObject(6, cd.getDateReleased());
                stm.setInt(7, cd.getNumberOfTracks());
                stm.setInt(8, cd.getTotalPlayingTimes());
                stm.setBoolean(9, cd.isVariousArtists());
                stm.setString(10, cd.getRecordCompanyCode());
                stm.setLong(11, cd.getId());
            }
            stm.executeUpdate();
            stm.close();
        }
    }
}
