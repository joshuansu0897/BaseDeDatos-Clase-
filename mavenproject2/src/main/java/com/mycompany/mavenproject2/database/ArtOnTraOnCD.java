/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.Artist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author joshuansu
 */
public class ArtOnTraOnCD extends BD {

    private final static Logger logger = Logger.getLogger(TrackBD.class);
    private static ArtOnTraOnCD instance;

    private ArtOnTraOnCD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static ArtOnTraOnCD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new ArtOnTraOnCD(
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

    protected List<Long> getTrackArtist(Long id) throws Exception {
        List<Long> l = new ArrayList<>();
        String query = "SELECT trackid FROM public.\"Artist_on_Track_or_CD\" WHERE artistid = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    l.add(rsSer.getLong("trackid"));
                }
            }
        }
        return l;
    }

    protected List<Long> getCDArtist(long id) throws Exception {
        List<Long> l = new ArrayList<>();
        String query = "SELECT \"CDid\" FROM public.\"Artist_on_Track_or_CD\" WHERE artistid = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    l.add(rsSer.getLong("CDid"));
                }
            }
        }
        return l;
    }

    protected List<Long> getTrackCD(long id) throws Exception {
        List<Long> l = new ArrayList<>();
        String query = "SELECT trackid FROM public.\"Artist_on_Track_or_CD\" WHERE \"CDid\" = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    l.add(rsSer.getLong("trackid"));
                }
            }
        }
        return l;
    }
    
    public void deleteTracks(List<Long> ids) throws Exception {
        String query = "DELETE FROM public.\"Artist_on_Track_or_CD\" WHERE trackid = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            for (long id : ids) {
                stmSer.setLong(1, id);
                stmSer.addBatch();
            }
            stmSer.executeBatch();
        }
    }
}
