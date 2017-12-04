/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.database;

import com.mycompany.mavenproject2.configuracion.Configuracion;
import com.mycompany.mavenproject2.model.RecordCompany;
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
public class RecordCompanyBD extends BD {

    private final static Logger logger = Logger.getLogger(RecordCompanyBD.class);
    private static RecordCompanyBD instance;

    private RecordCompanyBD(String host, String inst, String port, String db, String usuario, String password) throws Exception {
        super(host, inst, port, db, usuario, password);
    }

    public static RecordCompanyBD getInstance() {
        if (instance == null) {
            try {
                Configuracion c = Configuracion.getInstance();
                instance = new RecordCompanyBD(
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

    public List<RecordCompany> getAllRecordCompany() throws Exception {
        List<RecordCompany> mgs = new ArrayList<>();
        String query = "SELECT code, name, details\n"
                + "	FROM public.\"Record_Company\";";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    RecordCompany mg = new RecordCompany();
                    mg.setCode(rsSer.getString("code"));
                    mg.setName(rsSer.getString("name"));
                    mg.setDetails(rsSer.getString("details"));
                    mgs.add(mg);
                }
            }
        }
        return mgs;
    }

    public RecordCompany getRecordCompanyCode(String code) throws Exception {
        RecordCompany mg = null;
        String query = "SELECT name, details\n"
                + "	FROM public.\"Record_Company\" where code = ?";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setString(1, code);
            try (ResultSet rsSer = stmSer.executeQuery()) {
                while (rsSer.next()) {
                    mg = new RecordCompany();
                    mg.setCode(code);
                    mg.setName(rsSer.getString("name"));
                    mg.setDetails(rsSer.getString("details"));
                }
            }
        }
        return mg;
    }

    public void deleteRecordCompany(String code) throws Exception {
        String query = "DELETE FROM public.\"Record_Company\" WHERE code = ? ";
        try (Connection conSer = getConnection(); PreparedStatement stmSer = conSer.prepareStatement(query)) {
            stmSer.setString(1, code);
            stmSer.executeUpdate();
        }
    }

    public void saveRecordCompany(RecordCompany MG, String lastCode) throws SQLException, Exception {

        boolean nueva = lastCode == null;

        String query;
        try (Connection con = getConnection()) {
            PreparedStatement stm;
            if (nueva) {
                query = "INSERT INTO public.\"Record_Company\"(\n"
                        + "	code, name, details)\n"
                        + "	VALUES (?, ?, ?);";
                stm = con.prepareStatement(query);
                stm.setString(1, MG.getCode());
                stm.setString(2, MG.getName());
                stm.setString(3, MG.getDetails());
            } else {
                query = "UPDATE public.\"Record_Company\"\n"
                        + "	SET name=?, details=?\n"
                        + "	WHERE code=?";
                stm = con.prepareStatement(query);
                stm.setString(1, MG.getName());
                stm.setString(2, MG.getDetails());
                stm.setString(3, MG.getCode());
            }
            stm.executeUpdate();
            stm.close();
        }
    }
}
