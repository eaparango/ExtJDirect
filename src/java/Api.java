/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author desarrollo
 */
public class Api extends HttpServlet {

    /*
     require('config.php');
     header('Content-Type: text/javascript');

     // convert API config to Ext.Direct spec
     $actions = array();
     foreach($API as $aname=>&$a){
     $methods = array();
     foreach($a['methods'] as $mname=>&$m){
     if (isset($m['len'])) {
     $md = array(
     'name'=>$mname,
     'len'=>$m['len']
     );
     } else {
     $md = array(
     'name'=>$mname,
     'params'=>$m['params']
     );
     }
     if(isset($m['formHandler']) && $m['formHandler']){
     $md['formHandler'] = true;
     }
     $methods[] = $md;
     }
     $actions[$aname] = $methods;
     }

     $cfg = array(
     'url'=>'/aprender/server/service/router.php',
     'type'=>'remoting',
     'actions'=>$actions
     );

     echo 'Ext.ns("Ext.app"); Ext.app.REMOTING_API = ';

     echo json_encode($cfg);
     echo ';';
    
     */
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("Content-Type: text/javascript");
        PrintWriter out = response.getWriter();

        // Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("Config.xml");

        try {
            //Se parcea el archivo xml para crear el documento 
            //que se va a tratar.
            Document documento = (Document) builder.build(xmlFile);
            // Se obtiene la raiz del documento. En este caso 'cruisecontrol'
            Element rootNode = documento.getRootElement();

//            // Obtengo el tag "info" como nodo raiz para poder trabajar 
//            // los tags de éste.
//            Element rootNode_Level2 = rootNode.getChild("info");
//            // Obtengo los nodos "property" del tag info y los almaceno en
//            // una lista.
//            List<Element> lista = rootNode_Level2.getChildren("property");
//
//            //Imprimo por consola la lista.
//            for (int i = 0; i < lista.size(); i++) {
//                System.out.println(((Element) lista.get(i)).getAttributeValue("value"));
//            }
            // out.println("<!DOCTYPE html>");
            Map<String, Object> actions = new LinkedHashMap<String, Object>();

            for ( Element action : rootNode.getChildren() ) {

                ArrayList<Map> methods = new ArrayList<Map>();

                for (Element method : action.getChildren()) {

                    Map<String, Object> md = new LinkedHashMap<String, Object>();

                    if (method.getAttribute("len") != null) {

                        md.put("name", method.getName());
                        md.put("len", method.getAttributeValue("len"));

                    } else {

                        md.put("name", method.getName());
                        md.put("params", method.getAttributeValue("params"));

                    }

                    if (method.getAttribute("formHandler") != null && method.getAttribute("formHandler") != null) {

                        md.put("formHandler", true);

                    }

                    methods.add(md);
                }

                actions.put(action.getName(), methods);
            }

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        } finally {
            out.close();
        }

    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
