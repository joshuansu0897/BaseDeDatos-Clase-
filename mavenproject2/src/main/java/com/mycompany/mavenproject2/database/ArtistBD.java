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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author joshuansu
 */
public class ArtistBD extends BD {

    private final static Logger logger = Logger.getLogger(ArtistBD.class);
    private static ArtistBD instance;

    private ArtistBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static ArtistBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new ArtistBD(
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

    public List<Artist> getArtist() throws Exception {
        List<Artist> l = new ArrayList<>();
        String query = "SELECT name, gender, height, weight, details, id FROM public.\"Artist\"";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    Artist a = new Artist();
                    a.setName(rsSer.getString("name"));
                    a.setGender(rsSer.getString("gender").charAt(0));
                    a.setHeight(rsSer.getString("height"));
                    a.setWeight(rsSer.getString("weight"));
                    a.setDetails(rsSer.getString("details"));
                    a.setId(rsSer.getLong("id"));
                    l.add(a);
                }
            }
        }
        return l;
    }

    public Artist getArtistId(long id) throws Exception {
        Artist a = null;
        String query = "SELECT name, gender, height, weight, details FROM public.\"Artist\" where id = ?";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    a = new Artist();
                    a.setName(rsSer.getString("name"));
                    a.setGender(rsSer.getString("gender").charAt(0));
                    a.setHeight(rsSer.getString("height"));
                    a.setWeight(rsSer.getString("weight"));
                    a.setDetails(rsSer.getString("details"));
                    a.setId(id);
                }
            }
        }
        return a;
    }

    public void deleteArtist(long id) throws Exception {
        List<Long> CDids = ArtOnTraOnCD.getInstance().getCDArtist(id);
        CDBD.getInstance().deleteCDs(CDids);
        String query = "DELETE FROM public.\"Artist\" WHERE id = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            stmSer.executeUpdate();
        }
    }

    public List<String[]> getMostViews() throws Exception {
        List<String[]> l = new ArrayList<>();
        String query = "select distinct cd.title, cd.\"totalPlayingTimes\", art.name\n"
                + "from public.\"CD\" cd\n"
                + "inner join public.\"Artist_on_Track_or_CD\" ar\n"
                + "	on cd.id = ar.\"CDid\"\n"
                + "join public.\"Artist\" art \n"
                + "	on art.id = ar.artistid order by cd.\"totalPlayingTimes\" desc";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    String[] s = new String[3];
                    s[0] = rsSer.getString("name");
                    s[1] = rsSer.getString("title");
                    s[2] = rsSer.getString("totalPlayingTimes");
                    l.add(s);
                }
            }
        }
        return l;
    }

    public void saveArtist(Artist a) throws Exception {
        boolean nueva = a.getId() == -1;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"Artist\"(\n"
                        + "	name, gender, height, weight, details)\n"
                        + "	VALUES (?, ?, ?, ?, ?)";
                stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, a.getName());
                stm.setString(2, String.valueOf(a.getGender()));
                stm.setString(3, a.getHeight());
                stm.setString(4, a.getWeight());
                stm.setString(5, a.getDetails());
            } else {
                query = "UPDATE public.\"Artist\"\n"
                        + "	SET name=?, gender=?, height=?, weight=?, details=?\n"
                        + "	WHERE id=?";
                stm = con.prepareStatement(query);
                stm.setString(1, a.getName());
                stm.setString(2, String.valueOf(a.getGender()));
                stm.setString(3, a.getHeight());
                stm.setString(4, a.getWeight());
                stm.setString(5, a.getDetails());
                stm.setLong(6, a.getId());
            }
            stm.executeUpdate();
            stm.close();
        }
    }
}
