/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.MusicGenre;
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
public class MusicGenreBD extends BD {

    private final static Logger logger = Logger.getLogger(MusicGenreBD.class);
    private static MusicGenreBD instance;

    private MusicGenreBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static MusicGenreBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new MusicGenreBD(
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

    public List<MusicGenre> getAllMusiGenre() throws Exception {
        List<MusicGenre> mgs = new ArrayList<>();
        String query = "SELECT code, details\n"
                + "	FROM public.\"Music_Genre\"";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    MusicGenre mg = new MusicGenre();
                    mg.setCode(rsSer.getString("code"));
                    mg.setDetails(rsSer.getString("details"));
                    mgs.add(mg);
                }
            }
        }
        return mgs;
    }

    public MusicGenre getMusiGenreCode(String code) throws Exception {
        MusicGenre mg = null;
        String query = "SELECT details\n"
                + "	FROM public.\"Music_Genre\" where code = ?";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setString(1, code);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    mg = new MusicGenre();
                    mg.setDetails(rsSer.getString("details"));
                    mg.setCode(code);
                }
            }
        }
        return mg;
    }

    public void deleteMusicGenre(String code) throws Exception {
        String query = "DELETE FROM public.\"Music_Genre\" WHERE code = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setString(1, code);
            stmSer.executeUpdate();
        }
    }

    public void saveMusicGenre(MusicGenre MG, String lastCode) throws SQLException, Exception {

        boolean nueva = lastCode == null;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"Music_Genre\"(\n"
                        + "	code, details)\n"
                        + "	VALUES (?, ?);";
                stm = con.prepareStatement(query);
                stm.setString(1, MG.getCode());
                stm.setString(2, MG.getDetails());
            } else {
                query = "UPDATE public.\"Music_Genre\"\n"
                        + "	SET details=?\n"
                        + "	WHERE code=?";
                stm = con.prepareStatement(query);
                stm.setString(1, MG.getDetails());
                stm.setString(2, MG.getCode());
            }
            stm.executeUpdate();
            stm.close();
        }
    }
}
