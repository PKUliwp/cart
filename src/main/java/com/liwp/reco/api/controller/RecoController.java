package com.liwp.reco.api.controller;

import com.liwp.reco.api.query.entity.Entity;
import com.liwp.reco.api.query.reco.Recommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by liwp on 2017/6/30.
 */
@Slf4j
@RestController
public class RecoController {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private Recommendation reco;

    @CrossOrigin
    @PostMapping("/api/index.jsp")
    public String home(@RequestParam("query") String query,
                       @RequestParam("content") String cont) {

        String api = "";
        String element[] = new String[5];
        String prex[] = new String[5];

        if(query == null || query.length() < 3) {
            query = "index";
        }

        Collection<Entity> results = reco.recommend(query);
        if(results.size() < 5) {
            results = reco.recommend("index");
        }
        System.out.println(results.size());
        Iterator<Entity> it = results.iterator();

        String frame = "";


        String title[] = new String[5];
        String qBody[] = new String[5];
        String aBody[] = new String[5];
        for(int i=0;i<5;i++) {
            Entity entity = it.next();
            if(i == 0) {
                frame = "<div style=\"overflow:hidden;float:right;height:100%;width:35%;\">" +
                        "<iframe id=\"show\" name=\"show\" seamless scrolling=\"yes\"" +
                        "style=\"margin:0;padding:0;float:left;height:104%;width:104%;border:none;background-color:white\" " +
                        "src=\"" +
                        entity.urlPath() +
                        "\"></iframe></div>";
            }
            title[i] = entity.refSoTitle();
            qBody[i] = entity.refSoQuestionBody();
            aBody[i] = entity.refSoAnswerBody();


            element[i] = "<div style=\"float:left;position:relative;width:15%;\">" + entity.name() +
                    "</div><div style=\"width:18%;float:left\">" + entity.suffix() + "</div>" +
                    "<select style=\"float:left;width:4%;\" type=\"submit\" form=\"f" + i +"\" " +
                    "name=\"sel\"" +
                    "onChange=\"javascript:document.getElementById('f" + i + "').submit();\">" +
                    "<option value=\"" + entity.urlPath() + "\">Javadoc</option>" +
                    "<option value=\"qa\">Q&A</option>" +
                    "<option value=\"http://162.105.88.151:3000/Lucene\">Graph</option>" +
                    "</select>" +
                    "<br />";

            prex[i] = "<div style=\"float:left;width:28%;\" >" + entity.prefix() + "</div>";
        }


        for(int i=0;i<5;i++) {

            element[i] = prex[i] + element[i];
            api += element[i];
        }
        String html = "\n";

        html = html.replaceAll( "&", "&amp;");
        html = html.replaceAll( "\"", "&quot;");  //"
        html = html.replaceAll( "\t", "&nbsp;&nbsp;");
        html = html.replaceAll( " ", "&nbsp;");
        html = html.replaceAll("<", "&lt;");
        html = html.replaceAll( ">", "&gt;");
        html = html.replaceAll("\n","<br />");

        String so = "";
        for(int i=0;i<5;i++) {
            so += "<form id=\"f" + i +"\" method=\"post\" target=\"show\" action=\"http://162.105.88.121:9090/api/qa_show\">";
            so += "<input type=\"text\" style=\"display:none\" name=\"value\" value=\"" +
                    (title[i] +"<hr/>"+ qBody[i] +"<hr/>"+ aBody[i]).replaceAll("\"", "&quot;") +
                    "\" />";

            so += "</form>";
        }



        html = frame +  so + api + html ;

        return html;
    }


}
