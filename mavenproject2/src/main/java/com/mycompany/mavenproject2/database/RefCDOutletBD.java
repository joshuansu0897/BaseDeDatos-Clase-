/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.RefCDOutlet;
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
public class RefCDOutletBD extends BD {

    private final static Logger logger = Logger.getLogger(MusicGenreBD.class);
    private static RefCDOutletBD instance;

    private RefCDOutletBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static RefCDOutletBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new RefCDOutletBD(
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

    public List<RefCDOutlet> getAllCDOutlet() throws Exception {
        List<RefCDOutlet> mgs = new ArrayList<>();
        String query = "SELECT code, description\n"
                + "	FROM public.\"Ref_CD_Outlet\"";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    RefCDOutlet mg = new RefCDOutlet();
                    mg.setCode(rsSer.getString("code"));
                    mg.setDescription(rsSer.getString("description"));
                    mgs.add(mg);
                }
            }
        }
        return mgs;
    }

    public RefCDOutlet getCDOutletCode(String code) throws Exception {
        RefCDOutlet mg = null;
        String query = "SELECT description\n"
                + "	FROM public.\"Ref_CD_Outlet\" where code = ?";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setString(1, code);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    mg = new RefCDOutlet();
                    mg.setDescription(rsSer.getString("description"));
                    mg.setCode(code);
                }
            }
        }
        return mg;
    }

    public void deleteCDOutlet(String code) throws Exception {
        String query = "DELETE FROM public.\"Ref_CD_Outlet\" WHERE code = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setString(1, code);
            stmSer.executeUpdate();
        }
    }

    public void saveCDOutlet(RefCDOutlet MG, String lastCode) throws SQLException, Exception {

        boolean nueva = lastCode == null;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"Ref_CD_Outlet\"(\n"
                        + "	description, code)\n"
                        + "	VALUES (?, ?);";
                stm = con.prepareStatement(query);
                stm.setString(1, MG.getDescription());
                stm.setString(2, MG.getCode());
            } else {
                query = "UPDATE public.\"Ref_CD_Outlet\"\n"
                        + "	SET description=?\n"
                        + "	WHERE code=?";
                stm = con.prepareStatement(query);
                stm.setString(1, MG.getDescription());
                stm.setString(2, MG.getCode());
            }
            stm.executeUpdate();
            stm.close();
        }
    }

}
