/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.configuracion;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author joshuansu
 */
public class Configuracion {

    private final static Logger logger = Logger.getLogger(Configuracion.class);

    public static final File ubicacion = new File("Configuracion" + File.separator + "config.json");

    private static Configuracion instance = null;

    private final HashMap<String, String> configuraciones = new HashMap<>();

    public static Configuracion getInstance() throws Exception {
        if (instance == null) {
            instance = new Configuracion(ubicacion);
        }
        return instance;
    }

    private Configuracion(File json) {
        try {
            if (json != null && json.exists()) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(json));
                JSONObject jsonObject = (JSONObject) obj;

                for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    configuraciones.put(key, jsonObject.get(key).toString());
                }
            }
        } catch (IOException | ParseException e) {
            logger.error("No se pudo leer el Json para la configuracion.", e);
        }
    }

    public String getConfiguracion(String key) {
        try {
            return configuraciones.get(key);
        } catch (Exception e) {
            return "";
        }
    }

    public int getSizeConf() {
        return configuraciones.size();
    }
}
