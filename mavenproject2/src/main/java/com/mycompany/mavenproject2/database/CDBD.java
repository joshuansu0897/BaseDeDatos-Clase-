/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
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

    public void deleteCD(int id) throws Exception {
        String query = "DELETE FROM public.\"CD\" WHERE id = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setInt(1, id);
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
}
