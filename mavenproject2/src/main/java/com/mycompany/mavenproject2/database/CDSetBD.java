/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.CDSet;
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
public class CDSetBD extends BD {

    private final static Logger logger = Logger.getLogger(CDSetBD.class);
    private static CDSetBD instance;

    private CDSetBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static CDSetBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new CDSetBD(
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

    public List<CDSet> getAllCDSet() throws SQLException {
        List<CDSet> l = new ArrayList<>();
        String query = "SELECT \"totalNumber\", \"CDName\", \"companyCode\", id\n"
                + "	FROM public.\"CD_Set\";";
        try (Connection con = getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    CDSet cd = new CDSet();
                    cd.setTotalNumber(rs.getInt("totalNumber"));
                    cd.setName(rs.getString("CDName"));
                    cd.setCompanyCode(rs.getString("companyCode"));
                    cd.setId(rs.getLong("id"));
                    l.add(cd);
                }
            }
        }
        return l;
    }

    public CDSet getCDSetId(long id) throws SQLException {
        CDSet cd = null;
        String query = "SELECT \"totalNumber\", \"CDName\", \"companyCode\"\n"
                + "	FROM public.\"CD_Set\" where id = ?";
        try (Connection con = getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    cd = new CDSet();
                    cd.setTotalNumber(rs.getInt("totalNumber"));
                    cd.setName(rs.getString("CDName"));
                    cd.setCompanyCode(rs.getString("companyCode"));
                    cd.setId(id);
                }
            }
        }
        return cd;
    }

    public void saveCDSet(CDSet MG) throws SQLException {
        boolean nueva = MG.getId() == -1;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"CD_Set\"(\n"
                        + "	\"totalNumber\", \"CDName\", \"companyCode\")\n"
                        + "	VALUES (?, ?, ?);";
                stm = con.prepareStatement(query);
                stm.setInt(1, MG.getTotalNumber());
                stm.setString(2, MG.getName());
                stm.setString(3, MG.getCompanyCode());
            } else {
                query = "UPDATE public.\"CD_Set\"\n"
                        + "	SET \"totalNumber\"=?, \"CDName\"=?, \"companyCode\"=?\n"
                        + "	WHERE id=?;";
                stm = con.prepareStatement(query);
                stm.setInt(1, MG.getTotalNumber());
                stm.setString(2, MG.getName());
                stm.setString(3, MG.getCompanyCode());
                stm.setLong(4, MG.getId());
            }
            stm.executeUpdate();
            stm.close();
        }
    }

    public void deleteCDSet(long id) throws Exception {
        String query = "DELETE FROM public.\"CD_Set\" WHERE id = ?;";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setLong(1, id);
            stmSer.executeUpdate();
        }
    }

}
